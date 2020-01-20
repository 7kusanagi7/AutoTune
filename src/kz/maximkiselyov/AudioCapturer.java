package kz.maximkiselyov;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;

public class AudioCapturer {

    private ByteArrayOutputStream audioData;
    private TargetDataLine dataLine;
    private AudioFormat audioFormat;

    private boolean isCapture = false;

    private AudioCapturer() {
        try {
            audioFormat = AudioFormatter.getDefaultAudioFormat();

            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            dataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static class SingletonHelper {
        private static final AudioCapturer INSTANCE = new AudioCapturer();
    }

    public static AudioCapturer getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public void audioCapture(){
        isCapture = true;

        try {
            dataLine.open(audioFormat);
            dataLine.start();

            Thread captureThread = new CaptureThread();
            captureThread.start();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void captureStop(){
        isCapture = false;
    }

    public byte[] getAudioData(){
        return audioData.toByteArray();
    }

    private class CaptureThread extends Thread{

        private byte[] buffer = new byte[10000];

        @Override
        public void run(){
            audioData = new ByteArrayOutputStream();

            try {
                while (isCapture){
                    int cnt = dataLine.read(buffer, 0, buffer.length);

                    if(cnt > 0){
                        audioData.write(buffer, 0, cnt);
                    }
                }

                dataLine.drain();
                dataLine.close();
            } catch (Exception e){
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}

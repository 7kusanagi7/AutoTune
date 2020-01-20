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
            getDataLine().open(getAudioFormat());
            getDataLine().start();

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

    public TargetDataLine getDataLine() {
        if(dataLine == null){
            try {
                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, getAudioFormat());
                setDataLine((TargetDataLine) AudioSystem.getLine(dataLineInfo));
            } catch (Exception e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        return dataLine;
    }

    public void setDataLine(TargetDataLine dataLine) {
        this.dataLine = dataLine;
    }

    public AudioFormat getAudioFormat() {
        if(audioFormat == null){
            setAudioFormat(AudioFormater.getAudioFormat());
        }
        return audioFormat;
    }

    public void setAudioFormat(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }

    private class CaptureThread extends Thread{

        private byte buffer[] = new byte[10000];

        @Override
        public void run(){
            audioData = new ByteArrayOutputStream();

            try {
                while (isCapture){
                    int cnt = getDataLine().read(buffer, 0, buffer.length);

                    if(cnt > 0){
                        audioData.write(buffer, 0, cnt);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

}

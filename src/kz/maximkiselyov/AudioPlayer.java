package kz.maximkiselyov;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class AudioPlayer {

    private SourceDataLine dataLine;
    private AudioFormat audioFormat;
    private AudioInputStream audioStream;

    private AudioPlayer(){
        try {
            audioFormat = AudioFormatter.getDefaultAudioFormat();

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            dataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static class SingltonHelper {
        private static final AudioPlayer INSTANCE = new AudioPlayer();
    }

    public static AudioPlayer getInstance(){
        return SingltonHelper.INSTANCE;
    }

    public void audioPlay(byte[] audioData){
        InputStream inputStream = new ByteArrayInputStream(audioData);
        audioStream = new AudioInputStream(inputStream, audioFormat, audioData.length / audioFormat.getFrameSize());

        try {
            dataLine.open(audioFormat);
            dataLine.start();

            Thread playThread = new PlayThread();
            playThread.start();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private class PlayThread extends Thread {

        private byte[] buffer = new byte[10000];

        @Override
        public void run() {
            try {
                int cnt;
                while ((cnt = audioStream.read(buffer, 0, buffer.length)) != -1){
                    if(cnt > 0)
                        dataLine.write(buffer, 0, cnt);
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

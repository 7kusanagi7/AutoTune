package kz.maximkiselyov;

import javax.sound.sampled.AudioFormat;

public class AudioFormatter {

    private static AudioFormat defaultFormat;

    public static AudioFormat getDefaultAudioFormat(){
        if(defaultFormat == null) {
            float sampleRate = 16000.0F;
            int sampleSizeInBits = 16;
            int channels = 2;
            boolean signed = true;
            boolean bigEndian = false;

            defaultFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        }
        return defaultFormat;
    }

}

package kz.maximkiselyov;

import javax.sound.sampled.AudioFormat;

public class AudioFormatter {

    private static AudioFormat defaultFormat;

    public static AudioFormat getDefaultAudioFormat(){
        if(defaultFormat == null) {
            float sampleRate = 16000.0F;
            int sampleSizeInBits = 16;
            int channels = 1;
            boolean signed = true;
            boolean bigEndian = true;

            defaultFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        }
        return defaultFormat;
    }

}

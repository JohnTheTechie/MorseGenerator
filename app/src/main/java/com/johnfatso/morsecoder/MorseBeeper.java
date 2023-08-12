package com.johnfatso.morsecoder;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;

public class MorseBeeper {
    private final AudioTrack track;
    private final int count = (int)(44100.0 * 2.0 * (10000.0 / 1000.0)) & ~1;
    MorseBeeper() {
        this.track = new AudioTrack.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(44100)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                        .build())
                .setBufferSizeInBytes(this.count * (Short.SIZE / 8))
                .build();
    }

    private void generateTone(double freqHz, double volume)
    {
        if (volume > 1 || volume < 0){
            volume = 1; //will make sure it isn't too loud
        }
        short[] samples = new short[this.count];
        for(int i = 0; i < this.count; i += 2){
            short sample = (short)(volume * Math.sin(2 * Math.PI * i / (44100.0 / freqHz)) * 0x7FFF);
            samples[i] = sample;
            samples[i + 1] = sample;
        }
        this.track.write(samples, 0, count);
    }

    void startTone(double freq, double volume) {
        generateTone(freq, volume);
        this.track.play();
    }

    void stopTone() {
        this.track.stop();
        this.track.flush();
    }
}

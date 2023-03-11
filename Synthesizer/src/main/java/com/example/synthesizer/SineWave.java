package com.example.synthesizer;

import com.example.synthesizer.AudioClip;
import com.example.synthesizer.AudioComponent;

public class SineWave implements AudioComponent {
    int freq;
    // byte[] sample ;

    public SineWave(int frequency)
    {
        this.freq = frequency;
    }


    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        short maxValue = Short.MAX_VALUE;
        for (int i = 0; i < (AudioClip.SampleRate * AudioClip.duration); i++) {
            clip.setSample(i, (int)(maxValue * Math.sin(2 * (Math.PI) * freq * i / AudioClip.SampleRate)));
        }
        return clip;
    }

    @Override
    public boolean hasInput()
    {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }
}
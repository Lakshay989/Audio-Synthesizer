package com.example.synthesizer;

import com.example.synthesizer.AudioClip;
import com.example.synthesizer.AudioComponent;

public class SquareWave implements AudioComponent {
    int freq;
    AudioComponent input_ ;

    public SquareWave(int frequency)
    {
        this.freq = frequency;
    }


    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        short maxValue = Short.MAX_VALUE;
        for (int i = 0; i < (AudioClip.SampleRate * AudioClip.duration); i++) {
            if ((freq * i / AudioClip.SampleRate) % 1 > 0.5) {
                clip.setSample(i,maxValue);
            }
            else {
                clip.setSample(i, -maxValue);
            }
        }
        return clip;
    }

    @Override
    public boolean hasInput()
    {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input)
    {
        input_ = input ;

    }
}
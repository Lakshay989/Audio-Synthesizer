package com.example.synthesizer;

public class Waves implements AudioComponent {

    int freq;

    public void Squarewave(int frequency) {
        freq = frequency;
        AudioClip getClip ;
        AudioClip clip = new AudioClip();
        short maxValue = Short.MAX_VALUE;
        for (int i = 0; i < (AudioClip.SampleRate * AudioClip.duration); i++) {
            if ((freq * i / AudioClip.SampleRate) % 1 > 0.5) {
                clip.setSample(i,maxValue);
            } else {
                clip.setSample(i, -maxValue);
            }
        }
    }

    @Override
    public AudioClip getClip() {
        return null;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }
}

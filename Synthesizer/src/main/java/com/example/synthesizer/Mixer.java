package com.example.synthesizer;

import java.util.ArrayList;

public class Mixer implements AudioComponent {

    ArrayList<AudioComponent> inputs = new ArrayList<>();

    @Override
    public AudioClip getClip() {

        AudioClip mixerResult = new AudioClip();

        for (AudioComponent input: inputs)
        {
            mixerResult = addClip(mixerResult, input.getClip());
        }
        return mixerResult;
    }

    private AudioClip addClip(AudioClip clip1, AudioClip clip2)
    {
        AudioClip result = new AudioClip();

        if (clip1 != null)
        {

            for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
                int value = clip1.getSample(i) + clip2.getSample(i);
                value = AudioClip.clamp(value);
                result.setSample(i, value);

            }
        }

        else
        {
            return  clip2;
        }

        return result;

    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        inputs.add(input);
    }

}

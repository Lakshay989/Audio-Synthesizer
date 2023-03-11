package com.example.synthesizer;

public class LinearRamp implements AudioComponent
{
    int start_ ;
    int stop_ ;

    AudioComponent input_ ;

    public LinearRamp(int start, int stop)
    {
        start_ = start ;
        stop_ = stop ;
    }

    @Override
    public AudioClip getClip() {

        AudioClip clip = new AudioClip();

        for (int i =0 ; i<AudioClip.TOTAL_SAMPLES ; i++ )
        {
            clip.setSample(i,(int)((start_ * ( AudioClip.TOTAL_SAMPLES - i ) + stop_ * i)/ AudioClip.TOTAL_SAMPLES)) ; //Integer Division?
//            System.out.println(clip.getSample(i));
        }
        return clip ;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input)
    {
        input_ = input ;
    }
}
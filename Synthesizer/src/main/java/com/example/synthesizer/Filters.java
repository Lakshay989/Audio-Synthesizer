package com.example.synthesizer;

public class Filters implements AudioComponent
{
    double VolumeScale;

    AudioComponent input_ ;
    Filters(double volumeScale) {
        VolumeScale = volumeScale;
    }

    @Override
    public AudioClip getClip()
    {
        //AudioClip clip = new AudioClip() ;
        AudioClip original = input_.getClip();

        AudioClip result = new AudioClip() ; // Some modification of the original clip.

        for (int i =0 ; i< 88200 ; i++)
        {
            int sample = (int)(VolumeScale*original.getSample(i));

            sample = AudioClip.clamp(sample) ;
//            if (sample > Short.MAX_VALUE){
//                sample = Short.MAX_VALUE;
//            }
//            else if (sample < Short.MIN_VALUE){
//                sample = Short.MIN_VALUE;
//            }
            result.setSample(i, sample);
        }
        return result;

    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        input_ = input ;
    }
}

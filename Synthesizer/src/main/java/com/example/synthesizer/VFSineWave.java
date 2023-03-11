package com.example.synthesizer;

public class VFSineWave implements AudioComponent
{
    AudioComponent input_ ;

    int frequency_ ;
    public VFSineWave()
    {
   //     frequency_ = frequency ;
    }
    @Override
    public AudioClip getClip()
    {
        float phase = 0;

        short maxValue =Short.MAX_VALUE ;
        AudioClip output = new AudioClip() ;
        AudioClip original = input_.getClip();
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
//            System.out.println(original.getSample(i));
            phase += 2 * (Math.PI) * original.getSample(i) / AudioClip.SampleRate ;

            output.setSample(i, (int) (maxValue * Math.sin(phase))) ;
//            System.out.println(i);
        }
        return output ;
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
package com.example.synthesizer;
import javax.sound.sampled.*;

interface AudioComponent {

    AudioClip getClip();

    boolean hasInput() ;

    void connectInput(AudioComponent input);

 }



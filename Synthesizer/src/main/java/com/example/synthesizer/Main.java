package com.example.synthesizer;
import javax.sound.sampled.*;

public class Main
{
public static void main(String[] args)
{
// Get properties from the system about samples rates, etc.
// AudioSystem is a class from the Java standard library.
    Clip c = null; // Note, this is different from our AudioClip class.
    try {
        c = AudioSystem.getClip();
    } catch (LineUnavailableException e) {
        throw new RuntimeException(e);
    }

// This is the format that we're following, 44.1 KHz mono audio, 16 bits per sample.
    AudioFormat format16 = new AudioFormat( 44100, 16, 1, true, false );

    // Audio Components Sine Wave
    final AudioComponent gen = new SineWave(220) ;
    final AudioComponent gen1 = new SineWave(440) ;
    //AudioComponent gen2 = new SquareWave(880) ;

    //AudioClip clip = gen.getClip();         // Testing
   // AudioClip clip2 = gen2.getClip() ;

    // FILTERS
    //Filters filter = new Filters(0.9);
//    filter.connectInput(gen);
//    AudioClip clip = filter.getClip();


    //AudioComponent ramp =new LinearRamp(50,1000);
//    ramp.connectInput(gen);
    //LinearRamp ramp1 = new

//    AudioComponent VFclip = new VFSineWave();
//    VFclip.connectInput(ramp);
//   // filter.connectInput(VFclip);
//    //AudioClip clip = VFclip.getClip() ;
//    AudioClip clip = VFclip.getClip();

  Mixer mixer = new Mixer();
   mixer.connectInput(gen);
    mixer.connectInput(gen1);
   AudioClip MixedClip = mixer.getClip() ;

    try {
        c.open( format16, MixedClip.getData(), 0, MixedClip.getData().length ); // Reads data from our byte array to play it.
    } catch (LineUnavailableException e) {
        throw new RuntimeException(e);
    }

    // MIXER
//    Mixer mixer = new Mixer();
//    mixer.connectInput(gen);
//    mixer.connectInput(gen1);
//    AudioClip MixedClip = mixer.getClip() ;

    System.out.println( "About to play..." );
    c.start(); // Plays it.
    c.loop( 2 ); // Plays it 2 more times if desired, so 6 seconds total

// Makes sure the program doesn't quit before the sound plays.
    while( c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning() ){
        // Do nothing while we wait for the note to play.
//        System.out.println(c.getFramePosition());
    }

    System.out.println( "Done." );
    c.close();
}
}
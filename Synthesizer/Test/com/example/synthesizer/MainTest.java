package com.example.synthesizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    AudioClip audio = new AudioClip() ;

    @Test
    public void main() {
        for (byte i = (byte)Short.MIN_VALUE; i < (byte)Short.MAX_VALUE; i++) {
            audio.setSample(0, i);
            Assertions.assertEquals(audio.getSample(0), i);
        }

    }

}
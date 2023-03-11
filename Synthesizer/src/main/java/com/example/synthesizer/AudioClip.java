package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip
{

    static final double duration = 2.0 ;
    static final int SampleRate = 44100 ;

    public static int TOTAL_SAMPLES = (int)(SampleRate*duration) ;
    byte[] data = new byte[(int) duration*SampleRate*2] ;


    int getSample(int index)
    {

        int most = data[2*index +1];
        int least = Byte.toUnsignedInt(data[2*index]);

        //System.out.println(most);
        //System.out.println(least);
        int SampleValue = ((most<<8)|(least));

        return SampleValue ;
    }
    void setSample(int index, int value)
    {
        byte most = 0 ;
        byte least = 0 ;

        least = (byte) (value) ;

        // Might use another variable if value is changing
        most = (byte) (value>>8) ;

        data[2*index] = least ;

        data[(2*index)+1] = most ;
    }

    byte[] getData()
    {
        byte[] copy = Arrays.copyOf(data, (int) duration*SampleRate*2);
        return copy ;

    }

    public static int clamp(int value)
    {
        if(value > Short.MAX_VALUE)
        {
            value = Short.MAX_VALUE;

        }
        else if (value < Short.MIN_VALUE)
        {
            value = Short.MIN_VALUE;

        }

        return value;

    }
}
package com.example.lp.androidexam;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;

/**
 * Created by SharkGaming on 03/05/2017.
 */

public class SoundManager implements Runnable
{
    private static final SoundManager instance = new SoundManager();
    private SoundPool soundPool;
    int shoot = -1;

    public static SoundManager getInstance()
    {
        return instance;
    }

    private SoundManager()
    {
        super();
    }

    public void loadSounds(Context _context)
    {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try
        {
            AssetManager assetManager = _context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("firework.ogg");
            shoot = soundPool.load(descriptor, 0);

            // tilføj nye lyde her ved at genbruge de 2 ovenstående linjer med udskiftede variabler
        }

        catch(IOException e)
        {
            Log.e("error", "failed to load sound files");
        }
    }

    @Override
    public void run()
    {
        // SPil baggrunds musik her continuerligt
    }

    public void playSound(String _sound)
    {
        switch (_sound)
        {
            case "shoot":
                soundPool.play(shoot, 1, 1, 0, 0, 1);
                break;
        }
    }
}
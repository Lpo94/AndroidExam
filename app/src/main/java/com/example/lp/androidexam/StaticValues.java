package com.example.lp.androidexam;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by LP on 19-04-2017.
 */

public class StaticValues {
    private static StaticValues instance;

    public static StaticValues Instance(){
        if(instance == null)
        {
            instance = new StaticValues();
        }
        return instance;
    }


    private StaticValues()
    {

    }

    public void resetInstance()
    {
        instance = null;
    }


    //Player
    public Player globalPlayer;
    public MultiplayerObject btPlayer;
    public ArrayList<Player> allPlayers;

    //Lists
    public ArrayList<GameObject> colliders;

    public ArrayList<GameObject> gameObjects;

    public ArrayList<GameObject> tempObjects;

    public ArrayList<GameObject> objectsToRemove;

    public Fragment fragment;

    public FragmentManager fm;

    public FragmentTransaction ft;

    public MediaPlayer baggroundMusic;


    // Ints
    public int SCREEN_WIDTH;

    public int SCREEN_HEIGHT;

    public int deltaTime;

    public int gridWidth = 60;

    public int gridHeight = 60;

    //Floats
    public float WORLD_SPEED;

    public float WORLD_GRAVITY = 0.02f;

    // boolean
    public boolean gameFinished = false;

    // Longs
    public long currentTime;


    // Smaller things such as Contexts and similar
    public Context staticContext;

    public GameState gameState;


    // Bluetooth

    public BTService mBTService;

    public BluetoothAdapter BA;

    public BluetoothDevice connectedDevice;

    public String connectedDeviceAdress;

    public final UUID MY_UUID_INSECURE =UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

}

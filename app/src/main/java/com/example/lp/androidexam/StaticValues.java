package com.example.lp.androidexam;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class StaticValues {
    //Player
    public static Player globalPlayer;
    public static ArrayList<Player> allPlayers;

    //Lists
    public static ArrayList<GameObject> colliders;

    public static ArrayList<GameObject> gameObjects;

    public static ArrayList<GameObject> tempObjects;

    public static ArrayList<GameObject> objectsToRemove;

    public static Fragment fragment;

    public static FragmentManager fm;

    public static FragmentTransaction ft;


    // Ints
    public static int SCREEN_WIDTH;

    public static int SCREEN_HEIGHT;

    public static int deltaTime;

    public static int gridWidth = 60;

    public static int gridHeight = 60;

    //Floats
    public static float WORLD_SPEED;

    public static float WORLD_GRAVITY = 0.02f;

    // boolean
    public static boolean gameRunning = false;

    public static boolean endgame;

    // Longs

    public static long currentTime;


    // Smaller things such as Contexts and similar
    public static Context staticContext;

}

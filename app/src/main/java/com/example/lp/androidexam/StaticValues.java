package com.example.lp.androidexam;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class StaticValues {

    //Lists
    public static ArrayList<GameObject> colliders;

    public static ArrayList<GameObject> gameObjects;

    public static ArrayList<GameObject> tempObjects;

    public static ArrayList<GameObject> objectsToRemove;


    // Ints
    public static int SCREEN_WIDTH;

    public static int SCREEN_HEIGHT;

    // boolean

    public static boolean gameRunning = false;


    // Smaller things such as Contexts and similar
    public static Context staticContext;

}

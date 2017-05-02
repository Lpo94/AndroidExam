package com.example.lp.androidexam;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.Settings;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class LevelCreator {
    private ArrayList<GameObject> currentLevel= new ArrayList<>();
    private ArrayList<GameObject> testLevel = new ArrayList<>();
    public static Context staticContext;
    private int x;
    private int y;
    private int _i;
    String object;
    String[] split;



    public LevelCreator(int LevelIndex, Context _context)
    {
        staticContext = _context;
        if(StaticValues.gameObjects.size() != 0) {
            StaticValues.gameObjects.clear();
        }

        if(currentLevel.size() != 0) {
            currentLevel.clear();
        }


        switch(LevelIndex)
        {
            case 0:
                try {
                    AssetManager mngr = staticContext.getAssets();
                    InputStream is = mngr.open("map1.txt");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    object = new String(buffer);
                    split = object.split("\\s+");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                for (int i = 0; i < 24; i ++) {
                    _i ++;
                        switch (split[i]) {
                            case "A":
                                Player player = new Player(new Point(StaticValues.SCREEN_WIDTH/2,StaticValues.SCREEN_HEIGHT/2));
                                StaticValues.globalPlayer = player;
                                RaceCountdownTimer counter = new RaceCountdownTimer(player, new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 5) ,1, 6, R.drawable.countdown, 1000, 7);
                                testLevel.add(counter);
                                break;
                            case "B":
                                if(_i == 3)
                                {
                                    x = 10500;
                                    y = 800;
                                }
                                if(_i < 7) {
                                    Mud mud = new Mud(new Point(x, y), 1, 12, R.drawable.mud, 150, 11);
                                    testLevel.add(mud);
                                    x += 1200;
                                }
                                break;
                            case "C":
                                Ground ground = new Ground(new Point(0, 900), new Rect(1000, 100, 15000, 200));
                                testLevel.add(ground);
                                break;
                            case "D":
                                if(_i == 7) {
                                    Power powerspeed = new Power(new Point(23000, 750), 1, 4, R.drawable.powerupcoin, 100, 2);
                                    testLevel.add(powerspeed);
                                }

                                else if(_i == 8)
                                {
                                    Power powerspeed = new Power (new Point (12700, -100),1, 4, R.drawable.powerupcoin, 100, 2);
                                    testLevel.add(powerspeed);
                                }


                                break;
                            case "F":
                                ground = new Ground(new Point(12600, 1100), new Rect(1, 1, 11000, 100));
                                testLevel.add(ground);
                                break;
                            case "G":
                                if(_i == 10)
                                {
                                    x = 8300;
                                    y = 650;
                                }
                                if(_i < 12) {
                                    ground = new Ground(new Point(x, y), new Rect(1, 1, 1900, 100));
                                    testLevel.add(ground);
                                    y -= 300;
                                    x += 2200;

                                }
                                else if(_i >= 12)
                                {
                                    ground = new Ground(new Point(x, y), new Rect(1, 1, 1900, 100));
                                    testLevel.add(ground);
                                    y += 300;
                                    x += 2200;
                                }
                                break;
                            case "H":
                                ground = new Ground(new Point(23500, 875), new Rect(1, 1, 10000, 100));
                                testLevel.add(ground);
                                break;

                            case "I":
                                if(_i == 16)
                                {
                                    x = 21500;
                                    y = 550;
                                }

                                if(_i < 22)
                                {
                                    ground = new Ground(new Point(x, y), new Rect(1, 1, 1100, 100));
                                    testLevel.add(ground);
                                    x += 1500;
                                    y -= 300;
                                }
                                break;

                            case "J":
                                ground = new Ground(new Point(28500, 125), new Rect(1, 1, 100, 2100));
                                testLevel.add(ground);
                                break;

                            case "K":
                                ground = new Ground(new Point(28200, -950), new Rect(1, 1, 1000, 100));
                                testLevel.add(ground);
                                break;
                            case "L":
                                Goal goal = new Goal (new Point(29000, -1300), 1, 1, R.drawable.goal, 100, 1);
                                testLevel.add(goal);
                                break;

                            default:
                                break;
                        }
                    }
                currentLevel = testLevel;
        }

        for(GameObject go: currentLevel)
        {
            StaticValues.gameObjects.add(go);
        }
    }
}

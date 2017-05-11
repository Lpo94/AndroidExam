package com.example.lp.androidexam;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
/**
 * Created by LP on 19-04-2017.
 */

public class LevelCreator {
    private ArrayList<GameObject> currentLevel= new ArrayList<>();
    private ArrayList<GameObject> testLevel = new ArrayList<>();
private int x = 2;
    private int y = 24;
    private int xPos;
    private int yPos;
    private int _i;
    String object;
    String line;
    String[] split;
    AssetManager mngr;
    InputStream is;
    int lineNumber = 0;
    BufferedReader br;
    String map[][];



    public LevelCreator(int LevelIndex, Context _context)
    {
//        staticContext = _context;
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

                    mngr = StaticValues.staticContext.getAssets();
                    is = mngr.open("map1.txt");
                    br = new BufferedReader(new InputStreamReader(is));

                    map = new String [x][y];
                    for(int i = 0; i < x; i++)
                    {
                        line = br.readLine();
                        if(line == null)
                        {
                            br.close();
                        }
                        for(int j = 0; j < y; j++)
                        {
                            object = line.substring(j, j+1);
                            map[i][j] = object;
                        }
                    }


/*                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);


                    object = new String(buffer);*/

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

/*                for (int x = 0; x < 1 ; x++)
                {
                    for (int p = 0; p < 1 ; p++)
                    {
                        Mud mud = new Mud(new Point(x*32, y*32), 1, 12, R.drawable.mud, 150, 11);
                    }
                }*/
                for (int x = 0; x < 2; x++) {
                    for(int y = 0; y < 24; y++) {
                        _i++;
                        switch (map[x][y]) {
                            case "A":
                                Player player = new Player(new Point(StaticValues.SCREEN_WIDTH/2,StaticValues.SCREEN_HEIGHT/2));
                                StaticValues.globalPlayer = player;

                                RaceCountdownTimer counter = new RaceCountdownTimer(player, new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 5) ,1, 6, R.drawable.countdown, 1000, 7);
                                testLevel.add(counter);

                                PowerUp fireballup = new PowerUp(new Point(111, 211), PowerUp.PowerUpType.fireball);
                                testLevel.add(fireballup);

                                PowerUp speedup = new PowerUp(new Point(333, 211), PowerUp.PowerUpType.speed);
                                testLevel.add(speedup);

                                for (int j = 0; j < 100; j++)
                                {
                                    MapObject object  = new MapObject(new Point(-500+(j*52), 650), MapObject.ObjectType.flat);
                                    testLevel.add(object);
                                }

                                MapObject object  = new MapObject(new Point(900, 500), MapObject.ObjectType.box);
                                testLevel.add(object);
                                MapObject object2  = new MapObject(new Point(952, 500), MapObject.ObjectType.slopingLeft);
                                testLevel.add(object2);
                                MapObject object3  = new MapObject(new Point(1004, 500), MapObject.ObjectType.slopingRight);
                                testLevel.add(object3);
                                MapObject object4 = new MapObject(new Point(1056, 500), MapObject.ObjectType.fence);
                                testLevel.add(object4);


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
                                    PowerUp speed = new PowerUp(new Point(23000, 500), PowerUp.PowerUpType.speed);
                                    testLevel.add(speed);
                                }

                                else if(_i == 8)
                                {
                                    PowerUp speed2 = new PowerUp(new Point(12700, -100), PowerUp.PowerUpType.speed);
                                    testLevel.add(speed2);
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

                            case "M":
                                PowerUp pwUP = new PowerUp(new Point(100, 100), PowerUp.PowerUpType.fireball);
                                testLevel.add(pwUP);
                                break;


                          /*  case "P":
                                try {
                                    line = br.readLine();

                                    //split = line.split(",");
                                    //lineNumber ++;*//**//*
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                break;*/


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

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
/**
 * Created by LP on 19-04-2017.
 */

public class LevelCreator {
    private ArrayList<GameObject> currentLevel= new ArrayList<>();
    private ArrayList<GameObject> testLevel = new ArrayList<>();
    private int x = 1;
    private int y = 1;
    private int mapLength;

    private int xPos = 1;
    private int yPos = 1;
    private int _i;

    private boolean placement = false;


    String object;
    String line;
    String[] split;
    AssetManager mngr;
    InputStream is;
    int lineNumber = 0;
    BufferedReader br;
    String map[][];
    String[] ary;

    private String readFromFile(Context context) {

        String ret = "";

        try {
            mngr = StaticValues.staticContext.getAssets();
            is = mngr.open("map1.txt");

            if ( is != null ) {


                br = new BufferedReader(new InputStreamReader(is));

//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = br.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                is.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return ret;
    }

    public LevelCreator(int LevelIndex, Context _context)
    {
//        staticContext = _context;
        if(StaticValues.gameObjects.size() != 0) {
            StaticValues.gameObjects.clear();
        }

        if(currentLevel.size() != 0) {
            currentLevel.clear();
        }

        String testString = readFromFile(StaticValues.staticContext);
        ary = testString.split("");
        mapLength = ary.length;



        switch(LevelIndex)
        {
            case 0:
                for(int x = 1; x <= mapLength-1;x++) {
                    switch (ary[x]) {
                        case "A":
                            Player player = new Player(new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 2));
                            StaticValues.globalPlayer = player;
                            RaceCountdownTimer counter = new RaceCountdownTimer(player, new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 5), 1, 6, R.drawable.countdown, 1000, 7);
                            testLevel.add(counter);

                            PowerupFireball test = new PowerupFireball(new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 2));
                            StaticValues.tempObjects.add(test);


                            break;
                        case "B":
                            Mud mud = new Mud(new Point(StaticValues.gridWidth * xPos, StaticValues.gridHeight * yPos), 1, 12, R.drawable.mud, 150, 11);
                            testLevel.add(mud);
/*                                if (_i == 3) {
                                    xPos = 10500;
                                    yPos = 800;
                                }
                                if (_i < 7) {
                                    Mud mud = new Mud(new Point(xPos, yPos), 1, 12, R.drawable.mud, 150, 11);
                                    testLevel.add(mud);
                                    xPos += 1200;
                                }*/
                            break;
                        case "C":
                            if(groundCheckAroundMe(x, "C") != -1) {
                                int multiX = groundCheckAroundMe(x, "C");
                                Ground ground = new Ground(new Point(StaticValues.gridWidth * (xPos-multiX), StaticValues.gridHeight * yPos), new Rect(0, 0, StaticValues.gridWidth*multiX, StaticValues.gridHeight));
                                testLevel.add(ground);
                            }
                            break;

                        case "D":
                            if (_i == 7) {
                                PowerupSpeed powerspeed = new PowerupSpeed(new Point(StaticValues.gridWidth * x, StaticValues.gridHeight * y), 1, 4, R.drawable.powerupcoin, 100, 2);
                                testLevel.add(powerspeed);
                            } else if (_i == 8) {
                                PowerupSpeed powerspeed = new PowerupSpeed(new Point(StaticValues.gridWidth * x, StaticValues.gridHeight * y), 1, 4, R.drawable.powerupcoin, 100, 2);
                                testLevel.add(powerspeed);
                            }
                            break;
                        case"E":
                            if(groundCheckAroundMe(x, "E") != -1) {
                            int multiX = groundCheckAroundMe(x, "E");
                            Platform platform = new Platform(new Point(StaticValues.gridWidth * (xPos-multiX), StaticValues.gridHeight * yPos), new Rect(0, 0, StaticValues.gridWidth*multiX, StaticValues.gridHeight), false);
                            testLevel.add(platform);
                            }
                            break;

                        case "n":
                            yPos++;
                            xPos = 0;
                            break;

                        default:
                            break;
                    }
                    xPos++;
                }

                }
                currentLevel = testLevel;

        for(GameObject go: currentLevel)
        {
            StaticValues.gameObjects.add(go);
        }
    }

    private int groundCheckAroundMe(int _x, String _object)
    {
        int pos = _x;
        int i = 1;
        if(!checkInfrontofMe(_x, _object))
        {
            while(pos >= 0)
            {
                if(ary[pos-1].equals(_object))
                {
                    i++;
                    pos--;
                }
                else
                {
                    break;
                }

            }
        }
        if(checkInfrontofMe(_x, _object))
        {
            i = -1;
        }

        return i;
    }

    private boolean checkInfrontofMe(int _x, String _object)
    {
        boolean returnValue = false;
        if(_x+1 < mapLength)
        {
            if(ary[_x+1].equals(_object))
            {
                return true;
            }
        }


        return returnValue;
    }
}

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

    AssetManager mngr;
    InputStream is;
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

                            if(StaticValues.gameState == GameState.BluetoothMultiplayer)
                            {
                                StaticValues.btPlayer = new MultiplayerObject(new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 2));
                            }
                            StaticValues.globalPlayer = player;
                           // StaticValues.allPlayers.add(player);



                            RaceCountdownTimer counter = new RaceCountdownTimer(player, new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 5), 1, 6, R.drawable.countdown, 1000, 7);
                            testLevel.add(counter);

                            PowerUp testFireballPUP = new PowerUp(new Point(500, 50), PowerUp.PowerUpType.fireball);
                            testLevel.add(testFireballPUP);

                            PowerUp testSpeedPUP = new PowerUp(new Point(400, 50), PowerUp.PowerUpType.speed);
                            testLevel.add(testSpeedPUP);

                            Goal testGoal = new Goal(new Point(3000, 50));
                            testLevel.add(testGoal);


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

                                PowerUp testSpeedPUP2 = new PowerUp(new Point(700, 500), PowerUp.PowerUpType.speed);
                                StaticValues.tempObjects.add(testSpeedPUP2);
                            break;
                        case"E":
                            if(groundCheckAroundMe(x, "E") != -1) {
                            int multiX = groundCheckAroundMe(x, "E");
                            Platform platform = new Platform(new Point(StaticValues.gridWidth * (xPos-multiX), StaticValues.gridHeight * yPos), new Rect(0, 0, StaticValues.gridWidth*multiX, StaticValues.gridHeight), false);
                            testLevel.add(platform);
                            }
                            break;

                        case "f":
                            PowerUp testFireballPUP2 = new PowerUp(new Point(500, 500), PowerUp.PowerUpType.fireball);
                            StaticValues.tempObjects.add(testFireballPUP2);
                            break;

                        case "X":
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

package com.example.lp.androidexam;

import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class LevelCreator {
    private ArrayList<GameObject> currentLevel= new ArrayList<>();
    private ArrayList<GameObject> testLevel = new ArrayList<>();
    private ArrayList<GameObject> playerList = new ArrayList<>();

    public LevelCreator(int LevelIndex)
    {
        if(StaticValues.gameObjects.size() != 0) {
            StaticValues.gameObjects.clear();
        }

        if(currentLevel.size() != 0) {
            currentLevel.clear();
        }

        switch(LevelIndex)
        {
            case 0:
                AnimatedObject fire = new AnimatedObject(new Point(100, 100), 1, 3, R.drawable.fire, true, 100, 3);
                testLevel.add(fire);

                AnimatedObject counter = new AnimatedObject(new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 2) ,1, 6, R.drawable.countdown, true, 1000, 6);
                testLevel.add(counter);

                Player player = new Player(new Point(StaticValues.SCREEN_WIDTH/2,StaticValues.SCREEN_HEIGHT/2));
                testLevel.add(player);
                playerList.add(0,player);
                currentLevel = testLevel;
        }


        for(GameObject go: currentLevel)
        {
            StaticValues.gameObjects.add(go);
        }
    }

    public ArrayList<GameObject> GetPlayers()
    {
        return playerList;
    }


}

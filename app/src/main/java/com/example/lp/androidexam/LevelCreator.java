package com.example.lp.androidexam;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class LevelCreator {
    private ArrayList<GameObject> currentLevel= new ArrayList<>();
    private ArrayList<GameObject> testLevel = new ArrayList<>();

    public LevelCreator(int LevelIndex)
    {
        if(StaticValues.GameObjects.size() != 0) {
            StaticValues.GameObjects.clear();
        }

        if(currentLevel.size() != 0) {
            currentLevel.clear();
        }

        switch(LevelIndex)
        {
            case 0:
                Player player = new Player(new Point(StaticValues.SCREEN_WIDTH/2,StaticValues.SCREEN_HEIGHT/2));
                testLevel.add(player);

                currentLevel = testLevel;
        }


        for(GameObject go: currentLevel)
        {
            StaticValues.GameObjects.add(go);
        }
    }


}

package com.example.lp.androidexam;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class LevelCreator {
    private ArrayList<GameObject> currentLevel;
    private ArrayList<GameObject> testLevel;

    public LevelCreator(int LevelIndex)
    {
        StaticValues.GameObjects.clear();

        switch(LevelIndex)
        {
            case 0:
                testLevel.add(new Player(new Point(200,200),new Rect(100,100,200,200)));
        }


        for(GameObject go: currentLevel)
        {
            StaticValues.GameObjects.add(go);
        }
    }


}

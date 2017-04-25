package com.example.lp.androidexam;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.provider.Settings;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class LevelCreator {
    private ArrayList<GameObject> currentLevel= new ArrayList<>();
    private ArrayList<GameObject> testLevel = new ArrayList<>();


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
                Player player = new Player(new Point(StaticValues.SCREEN_WIDTH/2,StaticValues.SCREEN_HEIGHT/2));
//                testLevel.add(player);
                StaticValues.globalPlayer = player;


/*                FireObject fire = new FireObject(new Point(100, 100), 1, 3, R.drawable.fire, 100, 2);
                testLevel.add(fire);

                FireObject fire2 = new FireObject(new Point(400, 400), 1, 3, R.drawable.fire, 300, 1);
                testLevel.add(fire2);

               Power powerspeed = new Power (new Point (300, 300),1, 4, R.drawable.powerupcoin, 100, 2);
                testLevel.add(powerspeed);*/

                Mud mud= new Mud (new Point (300, 450),1, 12, R.drawable.mud, 150, 11);
                testLevel.add(mud);


                RaceCountdownTimer counter = new RaceCountdownTimer(player, new Point(StaticValues.SCREEN_WIDTH / 2, StaticValues.SCREEN_HEIGHT / 5) ,1, 6, R.drawable.countdown, 1000, 7);
                testLevel.add(counter);


                Ground ground = new Ground(new Point(0,900));
                testLevel.add(ground);


                currentLevel = testLevel;
        }


        for(GameObject go: currentLevel)
        {
            StaticValues.gameObjects.add(go);
        }
    }



}

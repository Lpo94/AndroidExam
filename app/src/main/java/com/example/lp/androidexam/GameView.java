package com.example.lp.androidexam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private GameThread gameThreadThread;
    private GestureDetector gestureDetector;
    private long frameTime;
    private LevelCreator levelCreator;
    private static GameView instance;

    public static int globalxSpeed = 1;

    public static GameView Instance(Context context)
    {
        if(instance == null)
        {
            instance = new GameView(context);
        }

            return instance;
    }

    private GameView(Context context) {
        super(context);
            getHolder().addCallback(this);
            gameThreadThread = new GameThread(getHolder(), this);
            setFocusable(true);
            StaticValues.staticContext = context;
            gestureDetector = new GestureDetector(context, new GestureListener());
            gestureDetector.setIsLongpressEnabled(true);

            newGame();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
            gameThreadThread = new GameThread(getHolder(), this);
            gameThreadThread.setRunning(true);
            gameThreadThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        while(true)
        {
            boolean retry = true;

            while(retry) {
                try {
                    gameThreadThread.setRunning(false);
                    gameThreadThread.join();
                } catch (Exception e) {e.printStackTrace();}
                retry = false;
            }
        }

    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
        _canvas.drawColor(Color.WHITE);

        for(GameObject go: StaticValues.tempObjects)
        {
            go.draw(_canvas);
        }
        if(StaticValues.globalPlayer != null) {
            StaticValues.globalPlayer.draw(_canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        if(StaticValues.endgame == false) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (gestureDetector.onTouchEvent(event)) {
                        if (StaticValues.globalPlayer != null) {
                            if (x < StaticValues.SCREEN_WIDTH / 2) {
                                StaticValues.globalPlayer.setDirection(-1);
                            } else if (x > StaticValues.SCREEN_WIDTH / 2) {
                                StaticValues.globalPlayer.setDirection(1);
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;

                case MotionEvent.ACTION_UP:
                    StaticValues.globalPlayer.setDirection(0);
                    break;
            }
        }
        else if(StaticValues.endgame == true)
        {
            if(event.getAction() == MotionEvent.ACTION_UP)
            {

            }
        }
        //   return true;
        return gestureDetector.onTouchEvent(event);
    }

    public void update()
    {
        StaticValues.tempObjects = StaticValues.gameObjects;

        StaticValues.deltaTime = (int)(System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();

        for(GameObject go: StaticValues.objectsToRemove)
        {
            if(StaticValues.gameObjects.contains(go))
            {
                StaticValues.gameObjects.remove(go);
            }
        }

        for(GameObject go: StaticValues.tempObjects)
        {
            go.update();
        }


        if(StaticValues.globalPlayer != null) {
            StaticValues.globalPlayer.update();
            for(GameObject go: StaticValues.tempObjects)
            {
                StaticValues.globalPlayer.onCollisionStay(go);
                StaticValues.globalPlayer.onCollisionEnter(go);

            }
            StaticValues.globalPlayer.onCollisionExit();

        }

        StaticValues.objectsToRemove.clear();


    }

    public void newGame()
    {
            StaticValues.colliders = new ArrayList<>();
            StaticValues.gameObjects = new ArrayList<>();
            StaticValues.objectsToRemove = new ArrayList<>();
            StaticValues.tempObjects = new ArrayList<>();
            levelCreator = new LevelCreator(0, getContext());

    }

    public void moveObjectX(int x)
    {
        for(GameObject go: StaticValues.tempObjects)
        {
            go.pos.x = go.pos.x + x;
        }
    }

    public void moveObjectY(int y)
    {
        for(GameObject go: StaticValues.tempObjects)
        {
            go.pos.y = go.pos.y + y;
        }
    }


}

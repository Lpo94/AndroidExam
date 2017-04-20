package com.example.lp.androidexam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private GameThread gameThreadThread;
    private GestureDetector gestureDetector;
    private long frameTime;
    private LevelCreator levelCreator;



    public GameView(Context context) {
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
//        int x = (int)event.getX();
//        int y = (int)event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if(gestureDetector.onTouchEvent(event)) {
//                    if (x < StaticValues.SCREEN_WIDTH / 2) {
//                        StaticValues.tempObjects.get(0).getPos().x -= 20;
//                    } else if (x > StaticValues.SCREEN_WIDTH / 2) {
//                        StaticValues.tempObjects.get(0).getPos().x += 20;
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
    }

    public void update()
    {
        StaticValues.tempObjects = StaticValues.gameObjects;

        int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
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


        StaticValues.objectsToRemove.clear();
    }

    public void newGame()
    {
        StaticValues.colliders = new ArrayList<>();
        StaticValues.gameObjects = new ArrayList<>();
        StaticValues.objectsToRemove = new ArrayList<>();
        StaticValues.tempObjects = new ArrayList<>();
        levelCreator = new LevelCreator(0);
    }


}

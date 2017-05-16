package com.example.lp.androidexam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Lars-Peter on 16/05/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThreadThread;
    private GestureDetector gestureDetector;
    private long frameTime;
    private LevelCreator levelCreator;
    private SoundManager soundManager;


    public GameThread getGameThreadThread() {
        return gameThreadThread;
    }


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThreadThread = new GameThread(getHolder(), this);
        setFocusable(true);
        StaticValues.staticContext = context;
        gestureDetector = new GestureDetector(context, new GestureListener());
        gestureDetector.setIsLongpressEnabled(true);
        soundManager = SoundManager.getInstance();
        soundManager.loadSounds(context);
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
        while (true) {
            boolean retry = true;

            while (retry) {
                try {
                    gameThreadThread.setRunning(false);
                    gameThreadThread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                retry = false;
            }
        }

    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
        _canvas.drawColor(Color.WHITE);

        // De her bitmaps behøver da ikke blive sat hver frame gør de? - Kasper
        Bitmap powerScreenDefeault = BitmapFactory.decodeResource(getResources(), R.drawable.powerupscreen1);
        Bitmap powerScreenSpeed = BitmapFactory.decodeResource(getResources(), R.drawable.powerupscreen2);
//        Bitmap  powerScreenFireball = BitmapFactory.decodeResource(getResources(),R.drawable.powerupscreen3);

        for (GameObject go : StaticValues.tempObjects) {
            go.draw(_canvas);
        }
        if (StaticValues.globalPlayer != null) {
            StaticValues.globalPlayer.draw(_canvas);
        }
        if (PowerUpClick.Clickable == true) {
            if (StaticValues.globalPlayer.canShoot) {
//                _canvas.drawBitmap(powerScreenFireball,StaticValues.SCREEN_WIDTH/2 -50 ,150,null);
            }

            if (StaticValues.globalPlayer.canSprint) {
                _canvas.drawBitmap(powerScreenSpeed, StaticValues.SCREEN_WIDTH / 2 - 50, 150, null);
            }
            powerScreenDefeault.recycle();
        }
        if (PowerUpClick.Clickable == false) {
            _canvas.drawBitmap(powerScreenDefeault, StaticValues.SCREEN_WIDTH / 2 - 50, 150, null);
            powerScreenSpeed.recycle();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (StaticValues.endgame == false) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if (gestureDetector.onTouchEvent(event)) {
                        if (StaticValues.globalPlayer != null) {
                            if (x < StaticValues.SCREEN_WIDTH / 2) {
                                StaticValues.globalPlayer.setDirection(-1);
                            } else if (x > StaticValues.SCREEN_WIDTH / 2) {
                                StaticValues.globalPlayer.setDirection(1);
                            }
                            if (x > StaticValues.SCREEN_WIDTH / 2 - 50 && x < StaticValues.SCREEN_WIDTH / 2 + 250 && y > 150 && y < 450 && PowerUpClick.Clickable == true) {
                                StaticValues.globalPlayer.speed += 0.5;
                                StaticValues.globalPlayer.usePowerup();
                                PowerUpClick.Clickable = false;
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
        } else if (StaticValues.endgame == true) {
            if (event.getAction() == MotionEvent.ACTION_UP) {

            }
        }
        //   return true;
        return gestureDetector.onTouchEvent(event);
    }

    public void update() {
        StaticValues.tempObjects = StaticValues.gameObjects;
        StaticValues.currentTime = System.currentTimeMillis();

        StaticValues.deltaTime = (int) (System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();


        for (GameObject go : StaticValues.tempObjects) {
            go.update();
        }


        if (StaticValues.globalPlayer != null) {
            StaticValues.globalPlayer.update();
            for (GameObject go : StaticValues.tempObjects) {
                StaticValues.globalPlayer.onCollisionStay(go);
                StaticValues.globalPlayer.onCollisionEnter(go);

            }
            StaticValues.globalPlayer.onCollisionExit();

        }

        for (GameObject go : StaticValues.objectsToRemove) {
            if (StaticValues.gameObjects.contains(go)) {
                StaticValues.gameObjects.remove(go);
            }
        }

        StaticValues.objectsToRemove.clear();


    }

    public void newGame() {
        StaticValues.allPlayers = new ArrayList<>();
        StaticValues.colliders = new ArrayList<>();
        StaticValues.gameObjects = new ArrayList<>();
        StaticValues.objectsToRemove = new ArrayList<>();
        StaticValues.tempObjects = new ArrayList<>();
        levelCreator = new LevelCreator(0, getContext());

    }


    public static void moveObjectX(int x) {
        for (GameObject go : StaticValues.tempObjects) {
            go.pos.x = go.pos.x + x;
        }
    }

    public static void moveObjectY(int y) {
        for (GameObject go : StaticValues.tempObjects) {
            go.pos.y = go.pos.y + y;
        }
    }
}
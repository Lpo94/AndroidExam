package com.example.lp.androidexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Lars-Peter on 16/05/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static GameThread gameThreadThread;
    private GestureDetector gestureDetector;
    private long frameTime;
    private LevelCreator levelCreator;
    private SoundManager soundManager;
    private PowerUpClick powerupBtn;
    private boolean gameFinished = false;




    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThreadThread = new GameThread(getHolder(), this);
        setFocusable(true);
        StaticValues.Instance().staticContext = context;
        gestureDetector = new GestureDetector(context, new GestureListener());
        gestureDetector.setIsLongpressEnabled(true);
        soundManager = SoundManager.getInstance();
        soundManager.loadSounds(context);
        powerupBtn = PowerUpClick.getInstance();
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

        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.draw(_canvas);
        }
        if (StaticValues.Instance().globalPlayer != null) {
            StaticValues.Instance().globalPlayer.draw(_canvas);
        }

        if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer)
        {
            StaticValues.Instance().btPlayer.draw(_canvas);
        }
        if (PowerUpClick.getInstance().clickable) {
            if (StaticValues.Instance().globalPlayer.canShoot) {
//                _canvas.drawBitmap(powerScreenFireball,StaticValues.Instance().SCREEN_WIDTH/2 -50 ,150,null);
            }

            if (StaticValues.Instance().globalPlayer.canSprint) {
                _canvas.drawBitmap(powerScreenSpeed, StaticValues.Instance().SCREEN_WIDTH / 2 - 50, 150, null);
            }
            powerScreenDefeault.recycle();
        }
        if (!PowerUpClick.getInstance().clickable) {
            _canvas.drawBitmap(powerScreenDefeault, StaticValues.Instance().SCREEN_WIDTH / 2 - 50, 150, null);
            powerScreenSpeed.recycle();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (!StaticValues.Instance().endgame) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if (gestureDetector.onTouchEvent(event)) {
                        if (StaticValues.Instance().globalPlayer != null) {
                            if (x < StaticValues.Instance().SCREEN_WIDTH / 2) {
                                StaticValues.Instance().globalPlayer.setDirection(-1);
                            } else if (x > StaticValues.Instance().SCREEN_WIDTH / 2) {
                                StaticValues.Instance().globalPlayer.setDirection(1);
                            }
                            if (x > StaticValues.Instance().SCREEN_WIDTH / 2 - 50 && x < StaticValues.Instance().SCREEN_WIDTH / 2 + 250 && y > 150 && y < 450 &&  PowerUpClick.getInstance().clickable) {

                                StaticValues.Instance().globalPlayer.usePowerup();
                                PowerUpClick.getInstance().clickable = false;
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;

                case MotionEvent.ACTION_UP:

                    StaticValues.Instance().globalPlayer.setDirection(0);
                    break;
            }
        } else if (StaticValues.Instance().endgame) {
            if (event.getAction() == MotionEvent.ACTION_UP) {

            }
        }
        //   return true;
        return gestureDetector.onTouchEvent(event);
    }

    public void update() {

        if(gameFinished)
        {
            endGame();
        }


        StaticValues.Instance().tempObjects = StaticValues.Instance().gameObjects;
        StaticValues.Instance().currentTime = System.currentTimeMillis();

        StaticValues.Instance().deltaTime = (int)(System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();


        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.update();
        }


        if (StaticValues.Instance().globalPlayer != null) {
            StaticValues.Instance().globalPlayer.update();
            for (GameObject go : StaticValues.Instance().tempObjects) {
                StaticValues.Instance().globalPlayer.onCollisionStay(go);
                StaticValues.Instance().globalPlayer.onCollisionEnter(go);

            }
            StaticValues.Instance().globalPlayer.onCollisionExit();

        }






        for (GameObject go : StaticValues.Instance().objectsToRemove) {
            if (StaticValues.Instance().gameObjects.contains(go)) {
                StaticValues.Instance().gameObjects.remove(go);
            }
        }
        if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer) {
            StaticValues.Instance().btPlayer.update();
        }

        StaticValues.Instance().objectsToRemove.clear();
    }

    public void newGame() {
        StaticValues.Instance().allPlayers = new ArrayList<>();
        StaticValues.Instance().colliders = new ArrayList<>();
        StaticValues.Instance().gameObjects = new ArrayList<>();
        StaticValues.Instance().objectsToRemove = new ArrayList<>();
        StaticValues.Instance().tempObjects = new ArrayList<>();
        levelCreator = new LevelCreator(0);

    }


    public static void moveObjectX(int x) {
        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.pos.x = go.pos.x + x;
        }
    }

    public static void moveObjectY(int y) {
        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.pos.y = go.pos.y + y;
        }
    }

    public void sendPlayers()
    {

        String data = StaticValues.Instance().btPlayer.getPos().x + "|" + StaticValues.Instance().btPlayer.getPos().y;
        StaticValues.Instance().mBTService.write(data.getBytes(Charset.defaultCharset()));
    }

    public void endGame()
    {
        gameThreadThread.setRunning(false);
        gameThreadThread = null;
        StaticValues.Instance().mBTService.stopService();
        StaticValues.Instance().mBTService = null;

        StaticValues.Instance().resetInstance();

        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }



}
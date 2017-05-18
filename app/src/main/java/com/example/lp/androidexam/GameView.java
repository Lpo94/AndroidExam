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

    float xAxis = 0f;
    float yAxis = 0f;

    float lastXAxis = 0f;
    float lastYAxis = 0f;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
       /* final int actionPeformed = event.getAction();

        switch(actionPeformed){
            case MotionEvent.ACTION_DOWN:{
                final float x = event.getX();
                final float y = event.getY();

                lastXAxis = x;
                lastYAxis = y;

                ed1.setText(Float.toString(lastXAxis));
                ed2.setText(Float.toString(lastYAxis));
                break;
            }

            case MotionEvent.ACTION_MOVE:{
                final float x = event.getX();
                final float y = event.getY();

                final float dx = x - lastXAxis;
                final float dy = y - lastYAxis;

                xAxis += dx;
                yAxis += dy;

                ed3.setText(Float.toString(xAxis));
                ed4.setText(Float.toString(yAxis));
                break;
            }
        }
        return true;*/

        ///////////////////////////////////////////////////////////////////////
  /*      // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        int x = (int) event.getX();
        int y = (int) event.getY();


        switch (maskedAction)
        {
            case MotionEvent.ACTION_DOWN:
                if (gestureDetector.onTouchEvent(event))
                {
                    if (StaticValues.Instance().globalPlayer != null)
                    {
                        if (x < StaticValues.Instance().SCREEN_WIDTH / 2)
                        {
                            StaticValues.Instance().globalPlayer.setDirection(-1);
                        } else if (x > StaticValues.Instance().SCREEN_WIDTH / 2)
                        {
                            StaticValues.Instance().globalPlayer.setDirection(1);
                        }
                        if(     x < PowerUpClick.getInstance().pos.x + PowerUpClick.getInstance().bitmapWidth &&
                                x >PowerUpClick.getInstance().pos.x &&
                                y < PowerUpClick.getInstance().pos.y + PowerUpClick.getInstance().bitmapHeight &&
                                y >PowerUpClick.getInstance().pos.y)
                        {
                            StaticValues.Instance().globalPlayer.usePowerup();
                        }
                    }
                }
            case MotionEvent.ACTION_POINTER_DOWN:
                {
                    if (gestureDetector.onTouchEvent(event))
                    {
                        if (StaticValues.Instance().globalPlayer != null)
                        {
                            if(     x < PowerUpClick.getInstance().pos.x + PowerUpClick.getInstance().bitmapWidth &&
                                    x >PowerUpClick.getInstance().pos.x &&
                                    y < PowerUpClick.getInstance().pos.y + PowerUpClick.getInstance().bitmapHeight &&
                                    y >PowerUpClick.getInstance().pos.y)
                            {
                                StaticValues.Instance().globalPlayer.usePowerup();
                            }
                        }
                    }
                // TODO use data
                break;
            }
            case MotionEvent.ACTION_MOVE:
                { // a pointer was moved
                // TODO use data
                break;
            }
            case MotionEvent.ACTION_UP:
                StaticValues.Instance().globalPlayer.setDirection(0);
            case MotionEvent.ACTION_POINTER_UP:
                StaticValues.Instance().globalPlayer.setDirection(0);
            case MotionEvent.ACTION_CANCEL:
                {
                // TODO use data
                break;
            }
        }
        invalidate();
        return gestureDetector.onTouchEvent(event);*/

///////////////////////////////////////////////////////////////////////
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (!StaticValues.Instance().endgame)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    if (gestureDetector.onTouchEvent(event))
                    {
                        if (StaticValues.Instance().globalPlayer != null)
                        {
                            if (x < StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                StaticValues.Instance().globalPlayer.setDirection(-1);
                            } else if (x > StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                StaticValues.Instance().globalPlayer.setDirection(1);
                            }
                            if(     x < PowerUpClick.getInstance().pos.x + PowerUpClick.getInstance().bitmapWidth &&
                                    x >PowerUpClick.getInstance().pos.x &&
                                    y < PowerUpClick.getInstance().pos.y + PowerUpClick.getInstance().bitmapHeight &&
                                    y >PowerUpClick.getInstance().pos.y)
                            {
                                StaticValues.Instance().globalPlayer.usePowerup();
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
            PowerUpClick.getInstance().update();
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

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
        _canvas.drawColor(Color.WHITE);


        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.draw(_canvas);
        }
        if (StaticValues.Instance().globalPlayer != null) {
            StaticValues.Instance().globalPlayer.draw(_canvas);
        }
        PowerUpClick.getInstance().draw(_canvas);
        if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer)
        {
            StaticValues.Instance().btPlayer.draw(_canvas);
        }

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
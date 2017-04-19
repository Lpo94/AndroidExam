package com.example.lp.androidexam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private GameThread gameThreadThread;
    private long frameTime;
    private LevelCreator levelCreator;



    public GameView(Context _context) {
        super(_context);
        getHolder().addCallback(this);
        gameThreadThread = new GameThread(getHolder(), this);
        setFocusable(true);
        StaticValues.StaticContext = _context;

        NewGame();
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
            try
            {
                gameThreadThread.setRunning(false);
                gameThreadThread.join();
            }
            catch (Exception e) {e.printStackTrace();}
        }

    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
        _canvas.drawColor(Color.WHITE);

        for(GameObject go: StaticValues.TempObjects)
        {
            go.Draw(_canvas);
        }
    }

    public void Update()
    {
        StaticValues.TempObjects = StaticValues.GameObjects;

        for(GameObject go: StaticValues.ObjectsToRemove)
        {
            if(StaticValues.GameObjects.contains(go))
            {
                StaticValues.GameObjects.remove(go);
            }
        }

        for(GameObject go: StaticValues.TempObjects)
        {
            go.Update();
        }



        StaticValues.ObjectsToRemove.clear();
    }

    public void NewGame()
    {
        StaticValues.Colliders = new ArrayList<>();
        StaticValues.GameObjects = new ArrayList<>();
        StaticValues.ObjectsToRemove = new ArrayList<>();
        StaticValues.TempObjects = new ArrayList<>();
        levelCreator = new LevelCreator(0);

    }


}

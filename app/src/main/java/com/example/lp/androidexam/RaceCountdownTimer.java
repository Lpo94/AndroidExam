package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by SharkGaming on 21/04/2017.
 */

public class RaceCountdownTimer extends GameObject
{
    // Player _player skal senere laves til at tage en liste med players så vi i multiplayer kan sørge for at dette sker for dem alle
    private Player player;

    public RaceCountdownTimer(Player _player, Point _pos, int _rows, int _columns, int _bitmapId, long _animationSpeed, int _frameCount)
    {
        player = _player;
        pos = _pos;
        rowsInSheet = _rows;
        columnsInSheet = _columns;
        bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),_bitmapId);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        setAnimationDelay(_animationSpeed);
        frameCount = _frameCount;
    }

    @Override
    public void update()
    {
        if(frameCount > 1)
        {

            long elapsedTime = (System.nanoTime() -StaticValues.currentTime) / 1000000;

            if(elapsedTime > animationDelay)
            {
                currentFrame++;
                StaticValues.currentTime = System.nanoTime();

                if(currentFrame > frameCount)
                {
                    player.setCanmove(true);
                    StaticValues.gameObjects.remove(this);
                }
            }
        }
    }
}

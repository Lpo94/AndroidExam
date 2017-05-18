package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by SharkGaming on 03/05/2017.
 */

public class Fireball extends GameObject
{
    private enum Animations { fadeIn, continous, connecting}
    private Animations curAnim;
    public Player owner;
    private int projectileSpeed;
    private long removeDelay;
    private long removeTimer;
    private SoundManager soundManager;

    public Fireball(Player _player)
    {
        owner = _player;
        pos = new Point(_player.pos.x + bitmapWidth, _player.pos.y);
        isSolid = false;

        rowsInSheet = 1;
        columnsInSheet = 28;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.fireball);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 200;
        frameCount = 28;

        soundManager = SoundManager.getInstance();
        soundManager.playSound("shoot");

        removeDelay = (System.currentTimeMillis()) + 8000;
        curAnim = Animations.fadeIn;
        setSpeed(50);
    }

    @Override
    public void update()
    {
        lifeTimer();
        if(curAnim == Animations.continous) pos.x += projectileSpeed;

        elapsedTime = (System.nanoTime() - startTime) / 1000000;

        if(rect != null)
        {
            rect.set(pos.x-rect.width()/2,pos.y -rect.height()/2, pos.x+rect.width()/2,pos.y+rect.height()/2);
        }

        if(elapsedTime > animationDelay)
        {
            currentFrame++;
            startTime = System.nanoTime();

            switch (curAnim)
            {
                case fadeIn:
                    if(currentFrame > 10)
                    {
                        curAnim = Animations.continous;
                    }
                    break;

                case continous:
                    if(currentFrame > 18)
                    {
                        currentFrame = 11;
                    }
                    break;

                case connecting:
                    if(currentFrame > 28)
                    {
                         removeThis();
                    }
                    break;
            }
        }
    }

    private void lifeTimer()
    {
        removeTimer = (System.currentTimeMillis()) - 8000;

        if(removeTimer > removeDelay)
        {
            removeThis();
        }
    }

    private void setSpeed(int _value)
    {
        projectileSpeed = _value;
    }

    private void removeThis()
    {
        StaticValues.Instance().tempObjects.remove(this);
    }

}

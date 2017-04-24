package com.example.lp.androidexam;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by LP on 19-04-2017.
 */

public class Player extends GameObject {

    public int colour;
    private boolean canMove = true;
    private boolean falling = false;
    private boolean jumping = false;
    private float velocity = 4;
    private float defaultVelocity;
    private int direction = 0;

    public void setCanmove(boolean _value)
    {
        canMove = _value;
    }

    public Player(Point _pos) {
        super();
        pos = _pos;
        rect = new Rect(100,100,200,200);
        speed = 2;
        defaultVelocity = velocity;
        colour = new Color().GREEN;
    }

    @Override
    public void update() {
        super.update();


        if(pos.y >= StaticValues.SCREEN_HEIGHT/2)
        {
            falling = false;
            if(velocity < 0) {
                jumping = false;
                velocity = defaultVelocity ;
            }
            pos.y = StaticValues.SCREEN_HEIGHT/2;
        }
        else
        {
            falling = true;
        }



        if(canMove)
        {
            if(direction != 0) {
                switch (direction) {
                    case -1:
                        GameView.Instance(null).moveObjectX((int)-(speed * StaticValues.deltaTime));
//                        pos.x -= speed * StaticValues.deltaTime;
                        break;
                    case 1:
                        GameView.Instance(null).moveObjectX((int)(speed * StaticValues.deltaTime));
//                        pos.x += speed * StaticValues.deltaTime;
                        break;
                }
            }

            if(falling && !jumping)
            {
                pos.y += StaticValues.WORLD_GRAVITY * StaticValues.deltaTime;
            }

            if(jumping)
            {
                pos.y -= velocity * StaticValues.deltaTime;
                velocity -= StaticValues.WORLD_GRAVITY * StaticValues.deltaTime;
            }
        }

        rect.set(pos.x-rect.width()/2,pos.y -rect.height()/2,
                pos.x+rect.width()/2,pos.y+rect.height()/2);
    }

    @Override
    protected void doCollision(GameObject _other) {
        super.doCollision(_other);
    }

    @Override
    public void draw(Canvas _canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);
        _canvas.drawRect(rect,paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(48);
        _canvas.drawText("X:" + pos.x + " Y:" + pos.y,pos.x,pos.y,paint);
    }

    //-1 = left, 1 = right;
    public void setDirection(int _direction)
    {
        direction = _direction;
    }

    public void playerJump()
    {
        if(!jumping) {
            jumping = true;
        }
    }




}

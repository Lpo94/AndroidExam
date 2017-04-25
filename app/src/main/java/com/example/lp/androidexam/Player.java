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
    private boolean canMove;
    private boolean falling;
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
        canMove = false;
        falling = true;
    }

    @Override
    public void update() {
        super.update();

        if(pos.y < 0)
        {
            pos.y = 0;
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
            int valueCheck = Math.round(rect.bottom + StaticValues.WORLD_GRAVITY * StaticValues.deltaTime);

            if(isObjectSolid(new Point(pos.x,valueCheck)))
            {
                falling = false;
                if(velocity < 0) {
                    jumping = false;
                    velocity = defaultVelocity ;
                }
            }
            else
            {
                falling = true;
            }

            if(falling && !jumping)
            {
                GameView.Instance(null).moveObjectY((int)(StaticValues.WORLD_GRAVITY * StaticValues.deltaTime));
//                pos.y += StaticValues.WORLD_GRAVITY * StaticValues.deltaTime;
            }

            if(jumping)
            {
                GameView.Instance(null).moveObjectY((int)+(velocity * StaticValues.deltaTime));
//                pos.y -= velocity * StaticValues.deltaTime;
                velocity -= StaticValues.WORLD_GRAVITY * StaticValues.deltaTime;
            }
        }

    }

    @Override
    protected void doCollision(GameObject _other) {
        super.doCollision(_other);
        if(_other instanceof FireObject)
        {
            colour = new Color().BLUE;
        }
       if (_other instanceof Power) {
           speed += 5;

       }
    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
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


    private boolean isObjectSolid(Point _p)
    {
        for(GameObject go: StaticValues.tempObjects)
        {
            if(go.isSolid) {
                if (go.getRect() != null) {
                    Rect r = new Rect(_p.x, _p.y, _p.x + 50, _p.y + 50);
                    if (Rect.intersects(go.getRect(),r)) {
                        return true;
                    }
                }
            }
        }
        return  false;
    }



//go.getRect().contains(r)
}

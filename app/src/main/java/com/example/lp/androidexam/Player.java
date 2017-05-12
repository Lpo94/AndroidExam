package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Matrix;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LP on 19-04-2017.
 */

public class Player extends GameObject {
    // Generel
    public int colour;
    public boolean canMove;
    private boolean falling;
    private boolean jumping = false;
    private boolean slowed = false;
    private float timer = 0;
    private float velocity = 4;
    private float defaultVelocity;
    private int direction = 0;
    MainActivity mainActivity;
    // Animation
    private enum Animations { idle, walking, falling, stunned}
    private Animations curAnim;
    private Point newPoint, oldPoint;
    private boolean isStunned; // skal sættes hvis stunned sakl være længere tid en stun animationen for at få den til at loope.
    private boolean startingStun; // skal sættes det øjeblik spilleren bliver stunned, den kører selv automatisk videre til stunned efter.
    private long stunDelay;

    // Powerup
    public boolean canShoot, canSprint, isSprinting;
    public PowerUp currentPowerup;
    public long sprintTimer;


    public void setCanmove(boolean _value)
    {
        canMove = _value;
    }


    public Player(Point _pos) {
        super();
        pos = _pos;
        rect = new Rect(100,100,200,200);
        speed = 0.5f;
        defaultVelocity = velocity;
        colour = new Color().GREEN;
        canMove = false;
        falling = true;

        setPlayerSprite(getPlayerNumber());
        curAnim = Animations.idle;

        currentPowerup = new PowerUp(new Point(0, 0), PowerUp.PowerUpType.none);
    }

    @Override
    public void update()
    {
        manageAnimationStates();

        oldPoint = pos;

        // Add så animationDelay falder når man har speedboost for at simulere sprint
        if(canMove && newPoint == oldPoint && !isStunned)
        {
            curAnim = Animations.idle;
        }
        if(canMove && newPoint != oldPoint)
        {
            curAnim = Animations.walking;
        }

        newPoint = pos;

        if(startingStun)
        {
            stunTimer();
            canMove = false;
            canShoot = false;
            canSprint = false;
        }

        if(isSprinting) sprint();




        if(rect != null)
        {
            rect.set(pos.x-rect.width()/2,pos.y -rect.height()/2, pos.x+rect.width()/2,pos.y+rect.height()/2);
        }


        if(timer >= 0 && slowed == true)
        {
            timer --;
        }

        else if(timer <= 0 && slowed == true)
        {
            speed = 2;
            slowed = false;
        }

        if(pos.y < 0)
        {
            pos.y = 0;
        }

        if(canMove && !isStunned)
        {
            if(direction != 0) {
                switch (direction) {
                    case -1:
                        GameView.Instance(null).moveObjectX((int)(speed * StaticValues.deltaTime));
//                        pos.x -= speed * StaticValues.deltaTime;
                        break;
                    case 1:
                        GameView.Instance(null).moveObjectX((int)-(speed * StaticValues.deltaTime));
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

    private void manageAnimationStates()
    {
        elapsedTime = (System.nanoTime() -StaticValues.currentTime) / 1000000;

        if(elapsedTime > animationDelay)
        {
            currentFrame++;

            switch (curAnim)
            {
                case idle:
                    currentFrame = 0;
                    break;

                case walking:
                    if (currentFrame > 6)
                    {
                        currentFrame = 1;
                    }
                    break;

                case falling:
                    if (currentFrame > 10)
                    {
                        startingStun = false;
                        isStunned = true;
                        curAnim = Animations.stunned;
                        stunDelay = System.nanoTime() + 3000;
                    }
                    break;

                case stunned:
                    if (currentFrame > 13)
                    {
                        currentFrame = 10;
                    }
                    break;
            }
        }
    }

    private void stunTimer()
    {

        if(StaticValues.currentTime > stunDelay)
        {
            isStunned = false;
        }
    }

    public void sprint()
    {
        speed = 2;

        if(StaticValues.currentTime > sprintTimer)
        {
            isSprinting = false;
            speed = 0.5f;
        }
    }

    @Override
    protected void doCollision(GameObject _other) {
        super.doCollision(_other);

        if(_other instanceof FireObject)
        {
            colour = new Color().BLUE;
        }

        if(_other instanceof Mud)
        {
            speed = 1;
            slowed = true;
            timer = 10;
        }

        if(_other instanceof Goal)
        {
            canMove = false;
            ((Goal) _other).addPlayerToList(this);
        }

        if(_other instanceof Fireball)
        {
            if(((Fireball)_other).owner != this)
            {
                startingStun = true;
            }
        }

        if(_other instanceof PowerUp)
        {
            if(((PowerUp)_other).getType() == PowerUp.PowerUpType.fireball && ((PowerUp) _other).canPlayerCollect(this))
            {
                currentPowerup = new PowerUp(pos, PowerUp.PowerUpType.fireball);
                PowerUpClick.Clickable = true;
            }

            if(((PowerUp)_other).getType() == PowerUp.PowerUpType.speed && ((PowerUp) _other).canPlayerCollect(this))
            {
                currentPowerup = new PowerUp(pos, PowerUp.PowerUpType.speed);
                PowerUpClick.Clickable = true;
            }
        }
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
            if(go.isSolid)
            {
                if (go.getRect() != null)
                {
                    Rect r = new Rect(_p.x, _p.y, _p.x + 100, _p.y + 100);
                    if (Rect.intersects(go.getRect(),r))
                    {
                        return true;
                    }
                }
            }
        }
        return  false;
    }

    // Skal implementeres færdigt når multiplayer er færdigt og added.
    private int getPlayerNumber()
    {
 /*       for (int i = 0; i < rooom.participants.lenght; i++)
        {
            if(i = waiting rooom.participants[this])
            {
                return i;
            }
        }*/

        return 3;
    }

    private void setPlayerSprite(int _playerNumber)
    {


        switch (_playerNumber)
        {
            case 1:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.player_cow);
                break;

            case 2:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.player_dino);
                break;

            case 3:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.giraf_sheet);
                break;

            case 4:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.player_parrot);
                break;
        }

        rowsInSheet = 1;
        columnsInSheet = 14;
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        setAnimationDelay(20);
        frameCount = 14;
    }

    public void usePowerup()
    {
        currentPowerup.use(this);
    }


//go.getRect().contains(r)
}

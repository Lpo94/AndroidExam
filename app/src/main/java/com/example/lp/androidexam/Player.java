package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LP on 19-04-2017.
 */

public class Player extends GameObject {

    public int colour;
    public boolean canMove;
    private boolean falling;
    private boolean jumping = false;
    private boolean slowed = false;
    private float timer = 0;
    private float velocity = 4;
    private float defaultVelocity;
    private int direction = 0;

    private enum Animations { idle, walking, falling, stunned}
    private Animations curAnim;
    private Point newPoint, oldPoint;
    private boolean isStunned; // skal sættes hvis stunned sakl være længere tid en stun animationen for at få den til at loope.
    private boolean isFalling; // skal sættes det øjeblik spilleren bliver stunned, den kører selv automatisk videre til stunned efter.
    public void setCanmove(boolean _value)
    {
        canMove = _value;
    }

    private boolean canShoot, canSprint;



    public Player(Point _pos) {
        super();
        pos = _pos;
        rect = new Rect(100,100,200,200);
        speed = 2;
        defaultVelocity = velocity;
        colour = new Color().GREEN;
        canMove = false;
        falling = true;

        setPlayerSprite(getPlayerNumber());
        curAnim = Animations.idle;

/*        isShooting = true; *//*for at skyde*/

// Fjern de 2 herunder og put i LevelCreatoren når vi har fixet hvorfor de ik blir tegnet der
        PowerupFireball test = new PowerupFireball(new Point(StaticValues.SCREEN_WIDTH / 3, StaticValues.SCREEN_HEIGHT / 4));
        StaticValues.tempObjects.add(test);
    }

    @Override
    public void update()
    {
        // Add så animationDelay falder når man har speedboost for at simulere sprint
        if(canMove && newPoint == pos && !isStunned) curAnim = Animations.idle;
        if(canMove && newPoint != pos) curAnim = Animations.walking;
        if(isFalling) curAnim = Animations.falling;
        if(isStunned) curAnim = Animations.stunned;
        newPoint = pos;

        elapsedTime = (System.nanoTime() -startTime) / 1000000;
        if(elapsedTime > animationDelay)
        {
            currentFrame++;
            startTime = System.nanoTime();

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
                        isFalling = false;
                        isStunned = true;
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

    @Override
    protected void doCollision(GameObject _other) {
        super.doCollision(_other);
        if(_other instanceof FireObject)
        {
            colour = new Color().BLUE;
        }
       if (_other instanceof PowerupSpeed)
       {
           canSprint = true;
           canShoot = false;

           PowerUpClick.Clickable=true;
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
       }

        if(_other instanceof Fireball)
        {
            if(((Fireball)_other).owner != this)
            {
                isStunned = true;
            }
        }

        if(_other instanceof PowerupFireball)
        {
            if(((PowerupFireball)_other).canPlayerCollect(this))
            {
                canShoot = true;
                canSprint = false;

                // Gør så spilleren kan bruge den
                PowerUpClick.Clickable=true;
            }
        }
    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);


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

    private void usePowerup()
    {
        if(canSprint)
        {
            // Do sprint
            canSprint = false;
        }

        if (canShoot)
        {
            Fireball fireball = new Fireball(this);
            StaticValues.tempObjects.add(fireball);
            canShoot = false;
        }
    }


//go.getRect().contains(r)
}

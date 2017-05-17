package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Vibrator;

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
    private Vibrator vibrator = (Vibrator)StaticValues.staticContext.getSystemService(StaticValues.staticContext.VIBRATOR_SERVICE);
    // Animation
    private enum Animations { idle, walking, falling, stunned}
    private Animations curAnim;
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
            speed = 0.5f;
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
                        GameView.moveObjectX((int)(speed * StaticValues.deltaTime));
//                        pos.x -= speed * StaticValues.deltaTime;
                        sourceY = bitmapHeight;
                        break;
                    case 1:
                        GameView.moveObjectX((int)-(speed * StaticValues.deltaTime));
//                        pos.x += speed * StaticValues.deltaTime;
                        sourceY = 0;
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
                GameView.moveObjectY((int)(StaticValues.WORLD_GRAVITY * StaticValues.deltaTime));
//                pos.y += StaticValues.WORLD_GRAVITY * StaticValues.deltaTime;
            }

            if(jumping)
            {
                GameView.moveObjectY((int)+(velocity * StaticValues.deltaTime));
//                pos.y -= velocity * StaticValues.deltaTime;
                velocity -= StaticValues.WORLD_GRAVITY * StaticValues.deltaTime;
            }
        }

    }

    private void manageAnimationStates()
    {
        // Add så animationDelay falder når man har speedboost for at simulere sprint
        if(canMove && direction == 0 && !isStunned)
        {
            curAnim = Animations.idle;
        }
        if(canMove && direction != 0)
        {
            curAnim = Animations.walking;
        }
        if(startingStun || isStunned)
        {
            if(startingStun)
            {
                curAnim = Animations.falling;
            }
            if(isStunned)
            {
                curAnim = Animations.stunned;
            }

            canMove = false;
            stunTimer();
        }


        elapsedTime = (System.nanoTime() - startTime) / 1000000;

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
                        startingStun = false;
                        isStunned = true;
                        stunDelay = StaticValues.currentTime + 3000;
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
            canMove = true;
        }
    }

    public void sprint()
    {
        speed = 2;
        animationDelay = 25;

        if(StaticValues.currentTime > sprintTimer)
        {
            isSprinting = false;
            speed = 0.5f;
            animationDelay = 75;
        }
    }

    @Override
    protected void doCollision(GameObject _other) {
        super.doCollision(_other);

        if(canMove)
        {
            if(_other instanceof Mud)
            {
                speed = 1;
                slowed = true;
                timer = 10;
            }

            if(_other instanceof Goal)
            {
                canMove = false;
                curAnim = Animations.idle;
                ((Goal) _other).collect(this);
            }

            if(_other instanceof Fireball && ((Fireball)_other).owner != this)
            {
                startingStun = true;
                vibrator.vibrate(500);
            }

            if(_other instanceof FireObject)
            {
                startingStun = true;
                vibrator.vibrate(500);
                ((FireObject)_other).removeThis();
            }

            if(_other instanceof PowerUp)
            {
                if(((PowerUp)_other).canCollect(this))
                {
                    if(((PowerUp)_other).getType() == PowerUp.PowerUpType.fireball)
                    {
                        currentPowerup = new PowerUp(pos, PowerUp.PowerUpType.fireball);
                        PowerUpClick.Clickable = true;
                    }

                    if(((PowerUp)_other).getType() == PowerUp.PowerUpType.speed)
                    {
                        currentPowerup = new PowerUp(pos, PowerUp.PowerUpType.speed);
                        PowerUpClick.Clickable = true;
                    }
                }
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
    public int getPlayerNumber()
    {
 /*       for (int i = 0; i < rooom.participants.lenght; i++)
        {
            if(i = waiting rooom.participants[this])
            {
                return i;
            }
        }*/

        return 2;
    }

    private void setPlayerSprite(int _playerNumber)
    {
        switch (_playerNumber)
        {
            case 1:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.giraf_sheet);
                break;

            case 2:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.dino_sheet);
                break;

            case 3:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.parrot_sheet);
                break;

            case 4:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.cow_sheet);
                break;
        }

        rowsInSheet = 2;
        columnsInSheet = 14;
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 50;
        frameCount = 14;
    }

    public void usePowerup()
    {
        currentPowerup.use(this);
    }




//go.getRect().contains(r)
}

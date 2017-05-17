package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by SharkGaming on 09/05/2017.
 */

public class PowerUp extends GameObject
{
    public ArrayList<Player> collectedBy;
    public static boolean Clickable = false;
    public enum PowerUpType { fireball, speed, none}
    private PowerUpType type = PowerUpType.none;
    public PowerUpType getType() { return  type;}

    public PowerUp(Point _pos, PowerUpType _type)
    {
        collectedBy = new ArrayList<>();
        isSolid = false;
        setPos(_pos);
        setType(_type);
    }

    public void setType(PowerUpType _type)
    {
        type = _type;

        switch (type)
        {
            case fireball:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.pickup_fireball);
                rowsInSheet = 1;
                columnsInSheet = 8;
                bitmapHeight = bitmap.getHeight() / rowsInSheet;
                bitmapWidth = bitmap.getWidth() / columnsInSheet;
                animationDelay = 50;
                frameCount = 7;
                break;

            case speed:
                bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.pickup_speed);
                rowsInSheet = 1;
                columnsInSheet = 4;
                bitmapHeight = bitmap.getHeight() / rowsInSheet;
                bitmapWidth = bitmap.getWidth() / columnsInSheet;
                animationDelay = 200;
                frameCount = 3;
                break;

            case none:
                break;
        }
    }

    public void use(Player _player)
    {
        switch (type)
        {
            case fireball:
                Fireball fireball = new Fireball(_player);
                StaticValues.tempObjects.add(fireball);
                type = PowerUpType.none;
                break;

            case speed:
                _player.sprintTimer = StaticValues.currentTime + 5000;
                _player.sprint();
                type = PowerUpType.none;
                break;

            case none:
                break;
        }
    }

    private void removeThis()
    {
        StaticValues.tempObjects.remove(this);
    }

    public boolean canPlayerCollect(Player _player)
    {
        if(collectedBy.contains(_player))
        {
            return false;
        }
        else
        {
            addToPlayer(_player);
            return true;
        }
    }

    private void addToPlayer(Player _player)
    {
        collectedBy.add(_player);

        if(this.getType() == PowerUpType.fireball)
        {
         _player.canShoot = true;
         _player.canSprint = false;
        }

        if(this.getType() == PowerUpType.speed)
        {
            _player.canShoot = false;
            _player.canSprint = true;
        }

        if(collectedBy.size() > 3)
        {
            removeThis();
        }
    }

}

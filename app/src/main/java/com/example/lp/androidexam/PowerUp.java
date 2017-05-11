package com.example.lp.androidexam;

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
                setBitmap(R.drawable.pickup_fireball);
                setRowsInSheet(1);
                setColumnsInSheet(8);
                setbitmapHeight();
                setbitmapWidth();
                setAnimationDelay(10000);
                setFrameCount(7);
                break;

            case speed:
                setBitmap(R.drawable.pickup_speed);
                setRowsInSheet(1);
                setColumnsInSheet(4);
                setbitmapHeight();
                setbitmapWidth();
                setAnimationDelay(10000);
                setFrameCount(3);
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

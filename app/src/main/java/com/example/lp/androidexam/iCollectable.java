package com.example.lp.androidexam;

import java.util.ArrayList;

/**
 * Created by SharkGaming on 16/05/2017.
 */

public interface iCollectable
{
    // Bruges af powerup, ild, mud, goal, fireball?
    ArrayList<Player> hasCollected = new ArrayList<>();

    boolean listContainsPlayer(Player _player);
    void collect(Player _player);

    // fireball, powerups, mud, fireobject
    void remove();

/*    asdasd*/
    // lav stun og slow længde fra fireobject og mud variere for at giv randomness?
}

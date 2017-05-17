package com.example.lp.androidexam;

import java.util.ArrayList;

/**
 * Created by SharkGaming on 16/05/2017.
 */

public interface iCollectable
{
    boolean canCollect(Player _player);
    void collect(Player _player);
}

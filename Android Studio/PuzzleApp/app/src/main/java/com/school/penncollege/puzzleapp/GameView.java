package com.school.penncollege.puzzleapp;

import android.annotation.SuppressLint;
import android.widget.TextView;
import android.content.Context;

/**
 * Created by Bailey Miller on 3/1/2018.
 */

@SuppressLint("AppCompatCustomView")
public class GameView extends TextView
{
    private int x, y;

    public GameView(int x, int y, Context c)
    {
        super(c);
        this.x = x;
        this.y = y;
    }
}

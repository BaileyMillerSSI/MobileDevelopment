package com.example.mapwithmarker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.widget.TextView;

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

    public int GetX()
    {
        return x;
    }

    public int GetY()
    {
        return y;
    }

    public Point GetPoint()
    {
        return new Point(GetX(), GetY());
    }

}

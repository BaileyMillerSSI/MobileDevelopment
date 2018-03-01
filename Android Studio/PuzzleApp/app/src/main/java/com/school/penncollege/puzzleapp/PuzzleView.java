package com.school.penncollege.puzzleapp;

import java.util.Random;
import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;

public class PuzzleView extends TableLayout {
    private GameView [] tvs;
    private PuzzleEngine puzzle;

    private int labelHeight;
    private int startY; // start y coordinate of TextView being moved
    private int startTouchY; // start y coordinate of current touch
    private int emptyPosition;
    private int [] positions;

    public PuzzleView( Activity activity, int width, int height,
                       PuzzleEngine puzzle) {
        super( activity );
        this.puzzle = puzzle;
        buildGuiByCode( activity, width, height, puzzle);
    }

    public void buildGuiByCode( Activity activity, int width, int height, PuzzleEngine puzzle) {

        tvs = new GameView[puzzle.GetCurrentGameSize()];
        int pos = 0;
        for (int i = 0; i < puzzle.GetRow(); i++ )
        {
            // Create all the rows
            TableRow tr = new TableRow(activity);

            for (int x = 0; x< puzzle.GetWidth(); x++)
            {
                // Create all the columns per row
                GameView tv = new GameView(i, x, activity);
                tv.setGravity(Gravity.CENTER);
                String text = (puzzle.GetTextAt(i, x));
                tv.setText(text);
                tv.setBackgroundColor( getResources().getColor(R.color.pale));

                int h = height / puzzle.GetRow();
                int w = width / puzzle.GetWidth();

                int finalSize = 0;

                if(h < w)
                    finalSize = h;
                else
                    finalSize = w;

                tv.setWidth(finalSize);
                tv.setHeight(finalSize);

                tvs[pos] = tv;
                pos+=1;
                tr.addView(tv);
            }

            addView(tr);
        }
    }

    // Returns the index of tv within the array tvs
    public int indexOfTextView( View tv ) {
        if( ! ( tv instanceof GameView ) )
            return -1;
        for( int i = 0; i < tvs.length; i++ ) {
            if( tv == tvs[i] )
                return i;
        }
        return -1;
    }

    public void updateStartPositions( int index, int y ) {
//        startY = params[index].topMargin;
//        startTouchY = y;
//        emptyPosition = tvPosition( index );
    }

    // moves the TextView at index index
    public void MoveToBlank(View v)
    {
        // Switch places with the blank

        // Update the game board

        //Reload the UI
    }

    public void enableListener( OnTouchListener listener ) {
        for( int i = 0; i < tvs.length; i++ )
            tvs[i].setOnTouchListener( listener );
    }

    public void disableListener( ) {
        for( int i = 0; i < tvs.length; i++ )
            tvs[i].setOnTouchListener( null );
    }

    // Returns position index within screen of TextField at index tvIndex
    // Accuracy is half a TextView's height
    public int tvPosition( int tvIndex ) {
        return 0;
    }

    // Swaps tvs[tvIndex] and tvs[positions[toPosition]]
    public void placeTextViewAtPosition( int tvIndex, int toPosition ) {
        // Move current TextView to position position
//        params[tvIndex].topMargin = toPosition * labelHeight;
//        tvs[tvIndex].setLayoutParams( params[tvIndex] );

        // Move TextView just replaced to empty spot
//        int index = positions[toPosition];
//        params[index].topMargin = emptyPosition * labelHeight;
//        tvs[index].setLayoutParams( params[index] );

        // Reset positions values
//        positions[emptyPosition] = index;
//        positions[toPosition] = tvIndex;
    }

    // Returns the current user solution as an array of Strings
    public String [] currentSolution( ) {
//        String [] current = new String[tvs.length];
//        for( int i = 0; i < current.length; i++ )
//            current[i] = tvs[positions[i]].getText( ).toString( );
//
//        return current;
        return new String[0];
    }

    // returns index of TextView whose location includes y
    public int indexOfTextView( int y ) {
        int position = y / labelHeight;
        return 0;
    }

    // returns text inside TextView whose index is tvIndex
    public String getTextViewText( int tvIndex ) {
        return tvs[tvIndex].getText( ).toString( );
    }

    // replace text inside TextView whose index is tvIndex with s
    public void setTextViewText( int tvIndex, String s ) {
        tvs[tvIndex].setText( s );
    }
}

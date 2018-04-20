package com.example.mapwithmarker;

import android.app.Activity;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PuzzleView extends TableLayout {
    private GameView [] tvs;
    private PuzzleEngine puzzle;
    private OnTouchListener ls;

    private Activity a;
    private int width, height;

    public PuzzleView(Activity activity, int width, int height,
                      PuzzleEngine puzzle) {
        super( activity );
        this.puzzle = puzzle;
        this.a = activity;
        this.width = width;
        this.height = height;
        buildGuiByCode();
    }

    public void buildGuiByCode() {

        removeAllViews();
        tvs = new GameView[puzzle.GetCurrentGameSize()];
        int pos = 0;
        for (int i = 0; i < puzzle.GetRow(); i++ )
        {
            // Create all the rows
            TableRow tr = new TableRow(a);

            for (int x = 0; x< puzzle.GetWidth(); x++)
            {
                // Create all the columns per row
                GameView tv = new GameView(i, x, a);
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


    // moves the TextView at index index
    public void MoveToBlank(View v)
    {
        //Get Blank
        Point blank = puzzle.GetBlankSpace();
        GameView myGv = (GameView) v;
        // Switch places with the blank
        puzzle.SwitchPlace(myGv.GetPoint(), blank);
        // Update the game board
        buildGuiByCode();
        //Reload the UI
        enableListener(ls);
    }

    public GameView GetBlankView(Point blank)
    {
        for (int i = 0; i< tvs.length; i++)
        {
            GameView gv = (GameView) (tvs[i]);
            if(gv.GetPoint() == blank)
                return gv;
        }

        return null;
    }

    public void enableListener( OnTouchListener listener ) {
        ls = listener;
        for( int i = 0; i < tvs.length; i++ )
            tvs[i].setOnTouchListener( listener );
    }

    public void disableListener( ) {
        for( int i = 0; i < tvs.length; i++ )
            tvs[i].setOnTouchListener( null );
    }

    // returns text inside TextView whose index is tvIndex
    public String getTextViewText(int tvIndex ) {
        return tvs[tvIndex].getText( ).toString( );
    }

    // replace text inside TextView whose index is tvIndex with s
    public void setTextViewText( int tvIndex, String s ) {
        tvs[tvIndex].setText( s );
    }
}

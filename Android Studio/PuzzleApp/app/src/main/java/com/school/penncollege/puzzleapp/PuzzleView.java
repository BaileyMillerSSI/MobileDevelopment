package com.school.penncollege.puzzleapp;

import java.util.Random;
import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Color;

public class PuzzleView extends RelativeLayout {
<<<<<<< HEAD
    private TextView [][] tvs;
    private LayoutParams [][] params;
    private int [][] colors;
=======
    private TextView [] tvs;
    private LayoutParams [] params;
>>>>>>> 5d43575ac931d038239ba9ea77501d90e27351fb

    private int labelHeight;
    private int labelWidth;
    private int startY; // start y coordinate of TextView being moved
    private int startTouchY; // start y coordinate of current touch
    private int emptyPosition;
    private int [][] positions;

    public PuzzleView( Activity activity, int width, int height,
<<<<<<< HEAD
                       int numRow, int numCol ) {
        super( activity );
        buildGuiByCode( activity, width, height, numRow, numCol );
    }

    public void buildGuiByCode( Activity activity, int width, int height,
                                int numRow, int numCol ) {
        positions = new int[numRow][numCol];
        tvs = new TextView[numRow][numCol];
        colors = new int[tvs.length][tvs[0].length];
        params = new LayoutParams[tvs.length][tvs[0].length];
        Random random = new Random();
        labelHeight = height / numCol;
        labelWidth = width / numRow;
        for (int i = 0; i < tvs.length; i++) {
            for (int j = 0; j < tvs[i].length; j++) {
                tvs[i][j] = new TextView(activity);
                tvs[i][j].setGravity(Gravity.CENTER);
                colors[i][j] = Color.rgb(random.nextInt(255),
                        random.nextInt(255), random.nextInt(255));
                tvs[i][j].setBackgroundColor(colors[i][j]);
                params[i][j] = new LayoutParams(width, labelHeight);
                params[i][j].leftMargin = 0;
                params[i][j].topMargin = labelHeight * i;
                addView(tvs[i][j], params[i][j]);
            }
        }
    }

    public void fillGui( String [][] scrambledText ) {
        int minFontSize = DynamicSizing.MAX_FONT_SIZE;
        for (int i = 0; i < tvs.length; i++) {
            for (int j = 0; j < tvs[i].length; j++) {
                tvs[i][j].setText(scrambledText[i][j]);
                positions[i][j] = i;

                tvs[i][j].setWidth(params[i][j].width);
                tvs[i][j].setPadding(20, 5, 20, 5);

                // find font size dynamically
                int fontSize = DynamicSizing.setFontSizeToFitInView(tvs[i][j]);
                if (minFontSize > fontSize)
                    minFontSize = fontSize;
            }
        }

        Log.w("MainActivity", "font size = " + minFontSize);
=======
                       int rows, int columns ) {
        super( activity );
        buildGuiByCode( activity, width, height, rows, columns );
    }

    public void buildGuiByCode( Activity activity, int width, int height,
                                int rows, int columns ) {
        positions = new int[rows * columns];
        tvs = new TextView[rows * columns];
        params = new LayoutParams[tvs.length];
        Random random = new Random( );
        labelHeight = height / rows;
        for( int i = 0; i < tvs.length; i++ ) {
            tvs[i] = new TextView( activity );
            tvs[i].setGravity( Gravity.CENTER );
            params[i] = new LayoutParams( width/columns, labelHeight );
            tvs[i].setBackgroundColor( getResources().getColor(R.color.pale));
            params[i].leftMargin = 0;
            params[i].topMargin = labelHeight * i;
            addView( tvs[i], params[i] );
        }
    }

    public void fillGui( String [] scrambledText ) {
        int minFontSize = 16;
        for( int i = 0; i < tvs.length; i++ ) {
            tvs[i].setText( scrambledText[i] );
            positions[i] = i;

            tvs[i].setWidth( params[i].width );
            tvs[i].setPadding( 20, 5, 20, 5 );
        }
>>>>>>> 5d43575ac931d038239ba9ea77501d90e27351fb
        // set font size for TextViews
        for (int i = 0; i < tvs.length; i++) {
            for (int j = 0; j < tvs[i].length; j++) {
                tvs[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP, minFontSize);
            }
        }
    }
    // Returns the index of tv within the array tvs
    public int indexOfTextView( View tv ) {
        if (!(tv instanceof TextView))
            return -1;
        for (int i = 0; i < tvs.length; i++) {
            for (int j = 0; j < tvs[i].length; j++) {
                if (tv == tvs[i][j])
                    return i;
            }

        }
        return -1;
    }

    public void updateStartPositions( int row,int col, int y ) {
        startY = params[row][col].topMargin;
        startTouchY = y;
        emptyPosition = tvPosition( row, col );
    }

    // moves the TextView at index index
    public void moveTextViewVertically( int row,int col, int y ) {
        params[row][col].topMargin = startY + y - startTouchY;
        tvs[row][col].setLayoutParams( params[row][col] );
    }

    public void enableListener( OnTouchListener listener ) {
        for (int i = 0; i < tvs.length; i++) {
            for (int j = 0; j < tvs[i].length; j++) {
                tvs[i][j].setOnTouchListener(listener);
            }
        }
    }
    public void disableListener( ) {
        for (int i = 0; i < tvs.length; i++) {
            for (int j = 0; j < tvs[i].length; j++) {
                tvs[i][j].setOnTouchListener(null);
            }
        }
    }

    // Returns position index within screen of TextField at index tvIndex
    // Accuracy is half a TextView's height
    public int tvPosition( int tvRow, int tvWidth ) {
        return ( params[tvRow][tvWidth].topMargin + labelHeight/2 ) / labelHeight;
    }

    // Swaps tvs[tvIndex] and tvs[positions[toPosition]]
    public void placeTextViewAtPosition( int tvRow,int tvCol, int toPosition ) {
        // Move current TextView to position position
        params[tvRow][tvCol].topMargin = toPosition * labelHeight;
        tvs[tvRow][tvCol].setLayoutParams( params[tvRow][tvCol] );

        // Move TextView just replaced to empty spot
        int index = positions[tvRow][toPosition];
        params[tvRow][index].topMargin = emptyPosition * labelHeight;
        tvs[tvRow][index].setLayoutParams( params[tvRow][index] );

        // Reset positions values
        positions[tvRow][emptyPosition] = index;
        positions[tvRow][toPosition] = tvCol;
    }

    // Returns the current user solution as an array of Strings
    public String [][] currentSolution( ) {
        String [][] current = new String[tvs.length][tvs[0].length];
        for (int i=0; i<current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
            current[i][j] =  tvs[i][j].getText( ).toString( );

    }
        }
        return current;
    }

    // returns index of TextView whose location includes y
    public int indexOfTextViewCol( int x, int y ) {
        int positionY = y / labelHeight;
        int positionX = x /labelWidth;
        return positions[positionX][positionY];
    }
    public int indexOfTextViewRow(int x, int y){
        int positionY = y / labelHeight;
        int positionX = x /labelWidth;
        return positions[positionX][positionY];
    }

    // returns text inside TextView whose index is tvIndex
    public String getTextViewText(int row, int col) {
        return tvs[row][col].getText( ).toString( );
    }

    // replace text inside TextView whose index is tvIndex with s
    public void setTextViewText( int row, int col, String s ) {
        tvs[row][col].setText( s );
    }
}

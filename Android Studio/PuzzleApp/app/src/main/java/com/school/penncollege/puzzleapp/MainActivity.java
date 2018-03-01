package com.school.penncollege.puzzleapp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {


    public static int STATUS_BAR_HEIGHT = 24; // in dp
    public static int ACTION_BAR_HEIGHT = 56; // in dp
private PuzzleView puzzleView;
private Puzzle puzzle;

    private int statusBarHeight;
    private int actionBarHeight;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        puzzle = new Puzzle( );

        Point size = new Point( );
        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        int screenHeight = size.y;
        int puzzleWidth = size.x;

        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics( );
        float pixelDensity = metrics.density;

        TypedValue tv = new TypedValue( );
        actionBarHeight = ( int ) ( pixelDensity * ACTION_BAR_HEIGHT );
        if( getTheme( ).resolveAttribute( android.R.attr.actionBarSize,
                tv, true ) )
            actionBarHeight = TypedValue.complexToDimensionPixelSize( tv.data,
                    metrics );

        statusBarHeight = ( int ) ( pixelDensity * STATUS_BAR_HEIGHT );
        int resourceId =
                res.getIdentifier( "status_bar_height", "dimen", "android" );
        if( resourceId != 0 ) // found resource for status bar height
            statusBarHeight = res.getDimensionPixelSize( resourceId );

        int puzzleHeight = screenHeight - statusBarHeight - actionBarHeight;
puzzleView = new PuzzleView( this, puzzleWidth, puzzleHeight, puzzle.getNumberOfRows( ),puzzle.getNumberCol() );
String [][] scrambled = puzzle.scramble( );
puzzleView.fillGui( scrambled );
puzzleView.enableListener( this );

        setContentView( puzzleView );

        DoubleTapHandler dth = new DoubleTapHandler( );
        detector = new GestureDetector( this, dth );
        detector.setOnDoubleTapListener( dth );
    }

    public boolean onTouchEvent( MotionEvent event ) {
        detector.onTouchEvent( event );
        return true;
    }

    public boolean onTouch( View v, MotionEvent event ) {
        int index = puzzleView.indexOfTextView( v );
        int action = event.getAction( );
        switch( action ) {
            case MotionEvent.ACTION_DOWN:
                // initialize data before move
                puzzleView.updateStartPositions( index, index, ( int ) event.getY( ) );
                // bring v to front
                puzzleView.bringChildToFront( v );
                break;
            case MotionEvent.ACTION_MOVE:
                // update y position of TextView being moved
                puzzleView.moveTextViewVertically( index, index, ( int ) event.getY( ) );
                break;
            case MotionEvent.ACTION_UP:
                // move is complete: swap the 2 TextViews
                int newPosition = puzzleView.tvPosition( index, index );
                puzzleView.placeTextViewAtPosition( index,index, newPosition );
                // if user just won, disable listener to stop the game
                if( puzzle.solved( puzzleView.currentSolution( ) ) )
                   puzzleView.disableListener( );
                break;
            }
                return true;
        }


    private class DoubleTapHandler
            extends GestureDetector.SimpleOnGestureListener {
        public boolean onDoubleTapEvent( MotionEvent event ) {
            int touchY = ( int ) event.getRawY( );
            int touchX = ( int ) event.getRawX();
            // y coordinate of the touch within puzzleView is
            // touchY - actionBarHeight - statusBarHeight
            int col = puzzleView.indexOfTextViewCol( touchX,touchY
                    - actionBarHeight - statusBarHeight );
            int row = puzzleView.indexOfTextViewRow(touchX,touchY-actionBarHeight-statusBarHeight);
            if( puzzleView.getTextViewText(touchX, touchY )
                    .equals( puzzle.wordToChange( ) ) )
                puzzleView.setTextViewText( row,col, puzzle.replacementWord( ) );
            return  true;
        }
    }
}


package com.example.mapwithmarker;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{



    public static int STATUS_BAR_HEIGHT = 24; // in dp
    public static int ACTION_BAR_HEIGHT = 56; // in dp
    private PuzzleView puzzleView;
    private PuzzleEngine _puzzle;

    private int statusBarHeight;
    private int actionBarHeight;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        int gameTracker =0;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            gameTracker = extras.getInt("game");
        }
        if(gameTracker==1) {
            _puzzle = new PuzzleEngine(PuzzleEngine.EasyMode);
        }else if(gameTracker == 2){
            _puzzle = new PuzzleEngine(PuzzleEngine.EasyMode);
        }else if (gameTracker == 3){
            _puzzle = new PuzzleEngine(PuzzleEngine.HardMode);
        }else{
            _puzzle = new PuzzleEngine(PuzzleEngine.EasyMode);
        }

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
        puzzleView = new PuzzleView( this, puzzleWidth, puzzleHeight, _puzzle );

        puzzleView.enableListener( this );

        setContentView( puzzleView );

        detector = new GestureDetector( this, new GestureDetector.SimpleOnGestureListener());
    }

    public boolean onTouchEvent( MotionEvent event ) {
        detector.onTouchEvent( event );
        return true;
    }

    public boolean onTouch( View v, MotionEvent event ) {
        int index = puzzleView.indexOfTextView( v );
        GameView gv = (GameView)v;
        int action = event.getAction( );
        switch( action ) {
            case MotionEvent.ACTION_DOWN:
                // bring v to front
                puzzleView.bringChildToFront( v );
                break;
            case MotionEvent.ACTION_MOVE:

                if( _puzzle.solved(_puzzle.GetBoard()) )
                {
                    puzzleView.disableListener( );
                    int gameTracker =0;
                    Bundle extras = getIntent().getExtras();
                    if(extras != null){
                        gameTracker = extras.getInt("game");
                    }
                    Log.d("myTag", Integer.toString(gameTracker));

                    Intent mapsActivity = new Intent(getApplicationContext(),MapsMarkerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("gameTracker",gameTracker);
                    mapsActivity.putExtras(bundle);
                    startActivity(mapsActivity);
                    break;

                }

                // Determine if this piece is allowed to move
                if(_puzzle.CanMove(_puzzle.GetBlankSpace(), gv.GetPoint()))
                {
                    // Switch with blank space
                    puzzleView.MoveToBlank(v);
                }
                break;
            case MotionEvent.ACTION_UP:
                // if user just won, disable listener to stop the game
                if( _puzzle.solved(_puzzle.GetBoard()) )
                    puzzleView.disableListener( );
                break;
        }
//                return true;
        return true;
    }

}



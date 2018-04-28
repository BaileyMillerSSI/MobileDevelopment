package com.example.mapwithmarker;

import android.graphics.Point;

import java.util.Random;

public class PuzzleEngine
{
    // Used for starting games
    public static final int EasyMode = 9; // 3x3
    public static final int MediumMode = 12; //3x4
    public static final int HardMode = 16; //4x4

    // Means the game hasn't started
    private int CurrentGameMode = 0;

    private int[][] Board;
    private int[][] solutionBoard;

    public int GetCurrentGameSize()
    {
        return this.CurrentGameMode;
    }
    public int GetWidth()
    {
        return Board[0].length;
    }

    public int GetRow()
    {
        return Board.length;
    }

    public String GetTextAt(int x, int y)
    {
        try
        {
            int num = Board[x][y];
            if(num != -1)
                return Integer.toString(num);
            else
            {
                return " ";
            }
        }catch (Exception e)
        {
            return "";
        }
    }

    public PuzzleEngine(int size)
    {
        // Can I create a basic gameboard?
//        this.CurrentGameMode = PuzzleEngine.EasyMode;
//
//        Board = new int[3][3];
//
//        Board[0][0] = 1;
//        Board[0][1] = 2;
//        Board[0][2] = 3;
//
//        Board[1][0] = 4;
//        Board[1][1] = 5;
//        Board[1][2] = 6;
//
//        Board[2][0] = 7;
//        Board[2][1] = 8;
//        Board[2][2] = -1;

//        // Determine game mode
        switch(size)
        {
            case PuzzleEngine.EasyMode:
                CreateGameBoard(3,3);
                this.CurrentGameMode = size;
                break;
            case PuzzleEngine.MediumMode:
                CreateGameBoard(3,4);
                this.CurrentGameMode = size;
                break;

        case PuzzleEngine.HardMode:
                CreateGameBoard(4,4);
                this.CurrentGameMode = size;
               break;
           default:
               CreateGameBoard(3, 3);
               this.CurrentGameMode = PuzzleEngine.EasyMode;
               break;

        }
    }

    public Point GetBlankSpace()
    {
        // Find space that has the -1 in it
        for ( int i = 0; i < GetRow(); ++i ) {
            for ( int j = 0; j < GetWidth(); ++j ) {
                if ( Board[i][j] == -1 ) {
                    return new Point(i, j);
                }
            }
        }

        return new Point();
    }

    public int[][] GetBoard()
    {
        return this.Board;
    }

    public boolean CanMove(Point blank, Point me)
    {
        if(me.x +1 == blank.x && me.y == blank.y)
        {
            // Directly Above
            return true;

        }else if(me.x -1 == blank.x && me.y == blank.y)
        {
            // Directly Below
            return true;

        }else if(me.y +1 == blank.y && me.x == blank.x)
        {
            // Directly to right
            return true;
        }else if(me.y -1 == blank.y && me.x == blank.x)
        {
            // Directly to left
            return true;
        }

        return false;
    }

    public void scramble( ) {

        Random random = new Random();

        int [][] scrambled = new int[GetRow()][GetWidth()];
        for (int i=0; i<Board.length; i++) {
            for (int j = 0; j < Board[i].length; j++) {
                scrambled[i][j]=Board[i][j];
            }
        }

        while( solved( scrambled ) ) {
            for (int i=0; i<scrambled.length; i++) {
                for (int j = 0; j < scrambled[i].length; j++) {
                    int randR = random.nextInt(scrambled.length - i) + i;
                    int randC = random.nextInt(scrambled[0].length - i) + i;
                    int temp = scrambled[i][j];
                    scrambled[i][j] = scrambled[randR][randC];
                    scrambled[randR][randC] =temp;
                }
            }
        }
        this.Board = scrambled;
    }

    public boolean solved(int[][] solution)
    {
        if( solution != null && solution.length == GetRow() ) {
            for (int i=0; i<GetRow(); i++) {
                for (int j = 0; j < GetWidth(); j++) {
                    if (solution[i][j] != (solutionBoard[i][j]))
                        return false;
                }
            }
            return true;
        }
        else
            return false;
    }

    public void SwitchPlace(Point p, Point p2) {
        int numAtP = Board[p.x][p.y];
        int numAtP2 = Board[p2.x][p2.y];

        Board[p.x][p.y] = numAtP2;
        Board[p2.x][p2.y] = numAtP;
    }

    private void CreateGameBoard(int width, int height)
    {
        this.Board = new int[width][height];


        // Create the game board across
        int totalBlocks = width*height; //Easy = 9 blocks
        int runningCount = 1;
        for (int h = 0; h < height; h++)
        {
            //Create all the columns
            for(int w = 0; w < width; w++)
            {
                if(runningCount != totalBlocks)
                {
                    //Create all the rows
                    this.Board[h][w] = runningCount;
                    runningCount+=1;
                }else
                {
                    this.Board[h][w] = -1; // Marks the blank spot on the board
                }
            }
        }
       solutionBoard= this.Board;
        scramble();

        // Ensure blank is bottom right

        //Find bottom right
        //[2][2]
        Point blank = GetBlankSpace();
        SwitchPlace(new Point(2, 2), blank);
    }
}

package com.school.penncollege.puzzleapp;

public class PuzzleEngine
{
    // Used for starting games
    public static final int EasyMode = 9; // 3x3
    public static final int MediumMode = 12; //3x4
    public static final int HardMode = 16; //4x4

    // Means the game hasn't started
    private int CurrentGameMode = 0;

    private int[][] Board;

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
        this.CurrentGameMode = PuzzleEngine.EasyMode;
        Board = new int[3][3];
        Board[0][0] = 1;
        Board[0][1] = 2;
        Board[0][2] = 3;

        Board[1][0] = 4;
        Board[1][1] = 5;
        Board[1][2] = 6;

        Board[2][0] = 7;
        Board[2][1] = 8;
        Board[2][2] = -1;

//        // Determine game mode
//        switch(size)
//        {
//            case PuzzleEngine.EasyMode:
//                CreateGameBoard(3,3);
//                this.CurrentGameMode = size;
//                break;
//            case PuzzleEngine.MediumMode:
//                CreateGameBoard(3,4);
//                this.CurrentGameMode = size;
//                break;
//
//            case PuzzleEngine.HardMode:
//                CreateGameBoard(4,4);
//                this.CurrentGameMode = size;
//                break;
//            default:
//                CreateGameBoard(3, 3);
//                this.CurrentGameMode = PuzzleEngine.EasyMode;
//                break;
//
//        }
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
    }
}

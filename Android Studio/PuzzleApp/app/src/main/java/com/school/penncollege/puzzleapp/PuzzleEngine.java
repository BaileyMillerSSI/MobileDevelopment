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

    public PuzzleEngine(int size)
    {
        this.CurrentGameMode = size;

        // Determine game mode
        switch(size)
        {
            case PuzzleEngine.EasyMode:
                this.Board = new int[3][3];
                break;
            case PuzzleEngine.MediumMode:
                this.Board = new int[3][4];
                break;

            case PuzzleEngine.HardMode:
                this.Board = new int[4][4];
                break;
        }
    }
}

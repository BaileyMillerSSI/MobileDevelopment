package com.school.penncollege.puzzleapp;

import java.util.Random;

public class Puzzle {
    public static final int NUMBER_ROWS = 3;
    public static final int NUMBER_COL = 4;
    String [][] parts;
    Random random = new Random( );

    public Puzzle( ) {
        parts = new String[NUMBER_ROWS][NUMBER_COL];
        parts[0][1] = "1";
        parts[0][2] = "2";
        parts[0][3] = "2";
        parts[0][4] = "4";
        parts[1][1] = "5";
        parts[1][2] = "6";
        parts[1][3] = "7";
        parts[1][4] = "8";
        parts[2][1] = "9";
        parts[2][2] = "10";
        parts[2][3] = "11";
        parts[2][4] = "12";
        parts[2][5] = "13";
    }

    public boolean solved( String [][] solution ) {
        if( solution != null && solution.length == parts.length ) {
            for (int i=0; i<parts.length; i++) {
                for (int j = 0; j < parts[i].length; j++) {
                    if (!solution[i][j].equals(parts[i][j]))
                        return false;
                }
            }
            return true;
        }
        else
            return false;
    }

    public String [][] scramble( ) {
        String [][] scrambled = new String[parts.length][parts[1].length];
        for( int i = 0; i < scrambled.length; i++ )
            scrambled[i] = parts[i];

        while( solved( scrambled ) ) {
            for (int i=0; i<scrambled.length; i++) {
                for (int j = 0; j < scrambled[i].length; j++) {
                    int randR = random.nextInt(scrambled.length - i) + i;
                    int randC = random.nextInt(scrambled.length - i) + i;
                    String temp = scrambled[i][j];
                    scrambled[i][j] = scrambled[randR][randC];
                    scrambled[randR][randC] =temp;
                }
            }
        }
        return scrambled;
    }

    public int getNumberOfRows( ) {
        return parts.length;
    }
    public int getNumberCol( ){return parts[0].length;}

    public String wordToChange( ) {
        return "MOBILE";
    }

    public String replacementWord( ) {
        return "ANDROID";
    }
}

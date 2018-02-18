package com.school.penncollege.todolist;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Bailey Miller on 2/18/2018.
 */

public class DatabaseManager extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "todoDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TableName = "TodoList";


    public DatabaseManager( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public void onCreate( SQLiteDatabase db ) {
        // build sql create statement
        String sqlCreate =
        "create table " + TableName + "( id integer primary key autoincrement, title text, dateCreated text, dateDue text, isDone integer";

        db.execSQL( sqlCreate );
    }

    public void onUpgrade( SQLiteDatabase db,
                           int oldVersion, int newVersion ) {
        // Drop old table if it exists
        db.execSQL( "drop table if exists " + TableName);
        // Re-create tables
        onCreate( db );
    }

    public void insert( TodoItem candy ) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlInsert = "";
        db.execSQL( sqlInsert );
        db.close( );
    }

    public void deleteById( int id ) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlDelete = "";
        db.execSQL( sqlDelete );
        db.close( );
    }

    public void updateById( int id, String name, double price ) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = "";
        db.execSQL( sqlUpdate );
        db.close( );
    }

    public ArrayList<Integer> selectAll( ) {
        String sqlQuery = "select id from " + TableName;

        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );

        ArrayList<Integer> allIds = new ArrayList<Integer>( );
        while( cursor.moveToNext( ) ) {
            int currentId = Integer.parseInt(cursor.getString(0));
            allIds.add(currentId);
        }
        db.close( );
        return allIds;
    }

    public TodoItem selectById( int id ) {
        String sqlQuery = "select title, dateDue, dateCreated, isDone from " + TableName;
        sqlQuery += "where id = " + id;

        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );

        TodoItem item = null;
//        if( cursor.moveToFirst( ) )
//            candy = new Candy( Integer.parseInt( cursor.getString( 0 ) ),
//                    cursor.getString( 1 ), cursor.getDouble( 2 ) );
        return item;
    }
}

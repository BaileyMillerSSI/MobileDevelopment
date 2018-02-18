package com.school.penncollege.todolist;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.spec.ECField;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

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
        "create table " + TableName + "( id integer primary key autoincrement, title text, dateCreated text, dateDue text, isDone integer );";

        db.execSQL( sqlCreate );

    }


    public void onUpgrade( SQLiteDatabase db,
                           int oldVersion, int newVersion ) {
        // Drop old table if it exists
        db.execSQL( "drop table if exists " + TableName);
        // Re-create tables
        onCreate( db );
    }

    public void insert( TodoItem item ) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlInsert = "insert into " + TableName + "(title, dateCreated, dateDue, isDone) values ("
                + item.GetTitle(true) + ", "
                + item.GetCreationTime(true) + ", "
                + item.GetDueTime(true) + ", "
                + item.GetStatus()
                + ")";
        try
        {
            db.execSQL( sqlInsert );
        }catch (Exception e)
        {
            String message = e.getMessage();
        }
        db.close( );
    }

    public void delete( TodoItem item) {
        deleteById(item.GetId());
    }

    public void deleteById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlDelete = "delete from " + TableName + " where id = " + id;
        db.execSQL( sqlDelete );
        db.close( );
    }

    public void update( TodoItem item ) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = "update " + TableName +
                " set "
                + "title = " + item.GetTitle(true) + ", "
                + "dateDue = " + item.GetDueTime(true) + ", "
                + "dateCreated = " + item.GetCreationTime(true) + ", "
                + "isDone = " + item.GetStatus()
                + " where id = " + item.GetId();
        db.execSQL( sqlUpdate );
        db.close( );
    }

    public ArrayList<Integer> selectAll( ) {
        String sqlQuery = "select id from " + TableName;


        String name = this.getDatabaseName();
        SQLiteDatabase db = null;
        try
        {
            db = this.getWritableDatabase( );
        }catch(Exception e)
        {
            String message = e.getMessage();
        }
        Cursor cursor = db.rawQuery( sqlQuery, null );

        ArrayList<Integer> allIds = new ArrayList<Integer>( );
        while( cursor.moveToNext( ) ) {
            int currentId = Integer.parseInt(cursor.getString(0));
            allIds.add(currentId);
        }
        db.close( );
        return allIds;
    }

    public void DeleteAll()
    {
        for (int id: selectAll()) {
            deleteById(id);
        }
    }

    public void select( TodoItem item ) {
        String sqlQuery = "select title, dateDue, dateCreated, isDone from " + TableName;
        sqlQuery += " where id = " + item.GetId();

        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );

        if( cursor.moveToFirst( ) )
        {
            Date dd = null, dc = null;
            try {

                String t = cursor.getString(0);
                String sdd = cursor.getString(1);
                String sdc = cursor.getString(2);
                String st = cursor.getString(3);

                dd= new SimpleDateFormat("MM/dd/yyyy").parse(cursor.getString(1));
                dc= new SimpleDateFormat("MM/dd/yyyy").parse(cursor.getString(2));
            } catch (ParseException e) {
                String message = e.getMessage();
            }
            // Update all the values here, I will write a method that accepts the values and updates the object
            item.SetValues(cursor.getString(0), dc, dd, cursor.getInt(3) != 0);
        }
    }
}

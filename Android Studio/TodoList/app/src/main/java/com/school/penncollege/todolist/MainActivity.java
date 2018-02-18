package com.school.penncollege.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_main);


        //MainActivity.dbManager.DeleteAll();

        //TodoItem tst = new TodoItem("Bailey was here!", new Date(), new Date(), false);

        // Get Me a list of all the todo items
        ArrayList<TodoItem> items = new ArrayList<TodoItem>();
        for (int id: MainActivity.dbManager.selectAll()) {
            items.add(new TodoItem(id));
        }

        // Test Cases
        // Get the first one
        TodoItem first = items.get(0);
        // Log it's status
        Log.d("Item Status", Integer.toString(first.GetStatus()));
        // Change status
        first.ToggleStatus();
        // Log status
        Log.d("Item Status", Integer.toString(first.GetStatus()));
        // Restart application and these will be backwards now
    }
}

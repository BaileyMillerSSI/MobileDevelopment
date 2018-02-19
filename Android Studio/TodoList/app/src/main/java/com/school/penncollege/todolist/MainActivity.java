package com.school.penncollege.todolist;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView lTask;

    public static DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainActivity.dbManager == null)
        {
            MainActivity.dbManager = new DatabaseManager(this);
        }

        setContentView(R.layout.activity_main);

        lTask = findViewById(R.id.list_view);

        loadTaskList();
    }

    private void loadTaskList(){

        ArrayList<TodoItem> Tasks = MainActivity.dbManager.GetAllTasks();

        // Put all the tasks onto the view
        for (TodoItem item: Tasks)
        {
            Log.d("Item: " + item.GetId(), "Title: " + item.GetTitle(false));
        }
    }

}

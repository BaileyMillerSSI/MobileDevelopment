package com.school.penncollege.todolist;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.graphics.Point;
import java.util.ArrayList;
import java.util.Date;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    ListView lTask;

    public static DatabaseManager dbManager;

    private int buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainActivity.dbManager == null)
        {
            MainActivity.dbManager = new DatabaseManager(this);
        }
        setContentView(R.layout.activity_main);


        Point size = new Point( );
        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        buttonWidth = size.x / 2;


        MainActivity.dbManager.DeleteAll();

        new TodoItem("Bailey was here 1", new Date(), new Date("2/18/2018"),false);
        new TodoItem("Bailey was here 2", new Date(), new Date("2/10/2018"),false);
        loadTaskList();
    }

    private void loadTaskList(){

        ScrollView sv = findViewById(R.id.scrollView);
        ArrayList<TodoItem> Tasks = MainActivity.dbManager.GetAllTasks();

        if(Tasks.size() > 0)
        {
            sv.removeAllViewsInLayout();
            ButtonHandler bh = new ButtonHandler();

            GridLayout grid = new GridLayout(this);
            grid.setRowCount((Tasks.size() +1) /2);
            grid.setColumnCount(2);

            // Create a bunch of buttons?
            Button[] TaskButtons = new Button[Tasks.size()];
            int i = 0;

            for (TodoItem item: Tasks)
            {
                TaskButtons[i] = new TodoButton(this, item);
                grid.addView(TaskButtons[i], buttonWidth, GridLayout.LayoutParams.WRAP_CONTENT);
                TaskButtons[i].setOnClickListener( bh );
                i++;
            }

            sv.addView(grid);
        }
    }

}

class ButtonHandler implements View.OnClickListener {
    public void onClick( View v ) {
        TodoButton btn = ((TodoButton)v);
        btn.ToggleStatus();
        btn.UpdateView();
    }
}

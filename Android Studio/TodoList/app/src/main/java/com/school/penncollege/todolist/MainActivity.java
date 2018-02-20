package com.school.penncollege.todolist;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.graphics.Point;
import java.util.ArrayList;

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
        buttonWidth = size.x /2 ;

        loadTaskList();
    }

    protected void onResume( ) {
        super.onResume( );
        loadTaskList();
    }


    private void loadTaskList(){

        ScrollView sv = findViewById(R.id.scrollView);
        ArrayList<TodoItem> Tasks = MainActivity.dbManager.GetAllTasks();

        if(Tasks.size() > 0)
        {
            sv.removeAllViewsInLayout();
            ChangeStatusHandler bh = new ChangeStatusHandler();

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

    public void GoToInsert(View view) {
        Intent insertIntent = new Intent( this, InsertActivity.class );
        this.startActivity( insertIntent );
    }

    public void GoToDelete(View view) {
        Intent deleteIntent = new Intent( this, DeleteActivity.class );
        this.startActivity( deleteIntent );
    }
}

class ChangeStatusHandler implements View.OnClickListener {
    public void onClick( View v ) {
        TodoButton btn = ((TodoButton)v);
        btn.ToggleStatus();
        btn.UpdateView();
    }
}

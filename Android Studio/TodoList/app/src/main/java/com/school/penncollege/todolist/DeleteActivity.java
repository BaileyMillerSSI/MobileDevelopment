package com.school.penncollege.todolist;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {


    int buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Point size = new Point( );
        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        buttonWidth = size.x/2;

        loadTaskList();
    }

    private void loadTaskList(){

        ScrollView sv = findViewById(R.id.scrollView);
        ArrayList<TodoItem> Tasks = MainActivity.dbManager.GetAllTasks();

        if(Tasks.size() > 0)
        {
            sv.removeAllViewsInLayout();
            DeleteHandler bh = new DeleteHandler();

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

    public void goBack( View v ) {
        this.finish( );
    }
}

class DeleteHandler implements View.OnClickListener {
    public void onClick( View v ) {
        TodoButton btn = ((TodoButton)v);
        MainActivity.dbManager.delete(btn.getItem());
        btn.SetDeleted();
        btn.UpdateView();
    }
}

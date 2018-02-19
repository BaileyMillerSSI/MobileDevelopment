package com.school.penncollege.todolist;

/**
 * Created by gabrielle on 2/18/2018.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class InsertActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        dbManager = new DatabaseManager( this );
        setContentView( R.layout.activity_insert );
    }

    public void insert( View v ) {
        // Get all the values
        // Get Title
        // Get DateDue

        //v.findViewById(R.id.input_title);
        //v.findViewById(R.id.input_Date_Due);

        // Create using the model
        // This will create a new todo item and store it in the database
        new TodoItem("", new Date(), new Date(), false);

        // Reset the view
        // Reset Title

        // Reset Due Date

    }

    public void goBack( View v ) {
        this.finish( );
    }
}

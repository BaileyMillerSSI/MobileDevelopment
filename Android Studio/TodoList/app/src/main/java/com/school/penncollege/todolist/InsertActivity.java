package com.school.penncollege.todolist;

/**
 * Created by gabrielle on 2/18/2018.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {


    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_insert );
    }

    public void insert( View v ) {

    }

    public void goBack( View v ) {
        this.finish( );
    }
}

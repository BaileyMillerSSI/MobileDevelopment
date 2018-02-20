package com.school.penncollege.todolist;

/**
 * Created by gabrielle on 2/18/2018.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InsertActivity extends AppCompatActivity {


    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_insert );

        final EditText date = findViewById(R.id.editDate);
        final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        date.setText(formatter.format(new Date()));

    }

    public void goBack( View v ) {
        this.finish( );
    }

    public void saveAndExit(View v)
    {
        EditText t = findViewById(R.id.editTitle);
        EditText date = findViewById(R.id.editDate);


        if(t.getText().toString().length() != 0)
        {
            try {
                new TodoItem(t.getText().toString(), new Date(), (new SimpleDateFormat("MM/dd/yyyy")).parse(date.getText().toString()), false);
            } catch (ParseException e) {

            }
            goBack(v);
        }
    }
}

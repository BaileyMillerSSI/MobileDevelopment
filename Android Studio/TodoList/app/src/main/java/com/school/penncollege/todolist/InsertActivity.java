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

    public static int getCountSubString(String str , String sub) {
        int n = 0, m = 0, counter = 0, counterSub = 0;
        while (n < str.length()) {
            counter = 0;
            m = 0;
            while (m < sub.length() && str.charAt(n) == sub.charAt(m)) {
                counter++;
                m++;
                n++;
            }
            if (counter == sub.length()) {
                counterSub++;
                continue;
            } else if (counter > 0) {
                continue;
            }
            n++;
        }

        return counterSub;
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

    public void saveAndExit(View v)
    {
        EditText t = findViewById(R.id.editTitle);
        EditText date = findViewById(R.id.editDate);

        try {
            new TodoItem(t.getText().toString(), new Date(), (new SimpleDateFormat("MM/dd/yyyy")).parse(date.getText().toString()), false);
        } catch (ParseException e) {

        }

        goBack(v);
    }
}

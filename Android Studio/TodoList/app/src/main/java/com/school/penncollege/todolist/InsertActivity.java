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
    private DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        dbManager = new DatabaseManager( this );
        setContentView( R.layout.activity_insert );
    }

    public void insert( View v ) {
        // Retrieve name and price
        EditText nameEditText = ( EditText) findViewById( R.id.input_task );
        EditText priceEditText = ( EditText) findViewById( R.id.input_date );
        String name = nameEditText.getText( ).toString( );
        String priceString = priceEditText.getText( ).toString( );

        // insert new candy in database
        try {
            String task = String.copyValueOf()
            Task candy = new Candy( 0, name, price );
            dbManager.insert( candy );
            Toast.makeText( this, "Task added", Toast.LENGTH_SHORT ).show( );
        } catch( NumberFormatException nfe ) {
            Toast.makeText( this, "error", Toast.LENGTH_LONG ).show( );
        }

        // clear data
        nameEditText.setText( "" );
        priceEditText.setText( "" );
    }

    public void goBack( View v ) {
        this.finish( );
    }
}

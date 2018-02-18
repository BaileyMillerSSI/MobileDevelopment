package com.school.penncollege.todolist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        lTask = (ListView)findViewById(R.id.lTask);

        loadTaskList();
    }

    private void loadTaskList(){
        ArrayList<String> taskList = dbHelper.getTaskList();
        if(mAdapter==null){
            mAdapter = new ArrayAdaper<String>(this,R.layout.activity_insert,R.id.task_title,taskList);
            lTask.setAdapter(mAdapter);
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);

        //change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId( );
        switch ( id ) {
            case R.id.action_add:
                Intent insertIntent
                        = new Intent(this, InsertActivity.class);
                this.startActivity(insertIntent);
                return true;
            case R.id.action_delete:
                Intent deleteIntent
                        = new Intent(this, DeleteActivity.class);
                this.startActivity(deleteIntent);
                return true;
            case R.id.action_update:
                Intent updateIntent
                        = new Intent(this, UpdateActivity.class);
                this.startActivity(updateIntent);
                return true;
            case R.id.action_reset:
                total = 0.0;
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }

        }
    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            // retrieve price of the candy and add it to total
            total += ((CandyButton) v).getPrice();
            String pay =
                    NumberFormat.getCurrencyInstance().format(total);
            Toast.makeText(MainActivity.this, pay,
                    Toast.LENGTH_LONG).show();
        }
    }
}

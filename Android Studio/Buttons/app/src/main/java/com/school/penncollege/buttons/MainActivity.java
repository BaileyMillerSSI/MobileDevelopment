package com.school.penncollege.buttons;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modifyLayout(getResources().getConfiguration());
    }

    public void onConfigurationChanged(Configuration config)
    {
        super.onConfigurationChanged(config);
        modifyLayout(config);
    }

    public void modifyLayout(Configuration config)
    {
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView(R.layout.activity_main);
        }else if(config.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setContentView(R.layout.activity_other);
        }
    }

    public void RotateScreen(View view)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}

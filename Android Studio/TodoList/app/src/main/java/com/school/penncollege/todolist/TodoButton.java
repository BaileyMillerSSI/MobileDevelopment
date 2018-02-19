package com.school.penncollege.todolist;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by Bailey Miller on 2/18/2018.
 */

public class TodoButton extends Button
{
    private TodoItem item;

    public TodoButton(Context c, TodoItem i)
    {
        super(c);
        item = i;

        UpdateText();
        UpdateView();
    }

    public String GetTitle()
    {
        String firstPart = "";
        if(item.GetStatus() == 1)
        {
            // True
            firstPart = getResources().getString(R.string.checkMark);
        }else
        {
            // False
            firstPart = getResources().getString(R.string.blankMark);
        }
        String title = firstPart + " | " + item.GetTitle(false) + " - " + item.GetDueTime(false);
        return  title;
    }

    public void UpdateView()
    {
        UpdateText();
        // Set the background color based on date due!
        try {
            Date dd = new SimpleDateFormat("MM/dd/yyyy").parse(item.GetDueTime(false));
            Date today = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate());

            int PastDue = dd.compareTo(today);
            if(PastDue < 0 && item.GetStatus() == 0)
            {
                // Date is past today's date!
                // Set background color to red!
                super.setBackgroundResource(R.color.PastDue);
            }else
            {
                super.setBackgroundResource(R.color.Normal);
            }

        } catch (Exception e) {
            String message = e.getMessage();
        }

    }

    public void UpdateText()
    {
        super.setText(GetTitle());
    }

    public void ToggleStatus()
    {
        item.ToggleStatus();
    }
}

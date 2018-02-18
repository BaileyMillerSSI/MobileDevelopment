package com.school.penncollege.todolist;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Bailey Miller on 2/15/2018.
 */

public class TodoItem
{
    private int ItemId;
    private boolean IsDone;
    private String Title;
    private Date DateCreated;
    private Date DateDue;

    public TodoItem(int id)
    {
        this.ItemId = id;
        // Get data from the database
        this.GetData();
    }

    public TodoItem(String title, Date dc, Date dd, boolean status)
    {
        // Set these values
        this.Title = title;
        this.DateCreated = dc;
        this.DateDue = dd;
        this.IsDone = status;
        //Call the create function
        MainActivity.dbManager.insert(this);
    }

    public int GetId()
    {
        return  this.ItemId;
    }
    public int GetStatus()
    {
        return (this.IsDone) ? 1: 0;
    }
    public void SetStatus(boolean status)
    {
        this.IsDone = status;
        this.UpdateDatabase();
    }

    public void ToggleStatus()
    {
        SetStatus(!IsDone);
    }

    public String GetTitle(boolean wrap)
    {
        if(wrap)
        {
            return "\"" + this.Title + "\"";
        }else
        {
            return this.Title;
        }
    }
    public void SetTitle(String t)
    {
        this.Title = t;
        this.UpdateDatabase();
    }

    public String GetCreationTime(boolean wrap)
    {
        if(wrap)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            return "\"" + formatter.format(this.DateCreated) + "\"";
        }else
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            return formatter.format(this.DateCreated);
        }
    }

    public String GetDueTime(boolean wrap)
    {
        if(wrap)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            return "\"" + formatter.format(this.DateDue) + "\"";
        }else
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            return formatter.format(this.DateDue);
        }
    }
    public void SetDueTime(Date d)
    {
        this.DateDue =d;
        this.UpdateDatabase();
    }

    public void SetValues(String title, Date dc, Date dd, boolean status)
    {
        this.Title = title;
        this.DateCreated = dc;
        this.DateDue = dd;
        this.IsDone = status;
    }

    // Have the model tell the database an update has occurred?
    private void UpdateDatabase()
    {
        MainActivity.dbManager.update(this);
    }

    private void GetData()
    {
        MainActivity.dbManager.select(this);
    }
}

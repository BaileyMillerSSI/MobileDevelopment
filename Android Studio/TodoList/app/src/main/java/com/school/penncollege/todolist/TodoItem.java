package com.school.penncollege.todolist;

import java.util.Date;

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

    public int GetId()
    {
        return  this.ItemId;
    }

    public boolean GetStatus()
    {
        return this.IsDone;
    }

    public void SetStatus(boolean status)
    {
        this.IsDone = status;
        this.UpdateDatabase();
    }

    public String GetTitle()
    {
        return this.Title;
    }

    public Date GetCreationTime()
    {
        return this.DateCreated;
    }



    // Have the model tell the database an update has occurred?
    private void UpdateDatabase()
    {
        // Call a static method that updates the database according to this model
        // Database.UpdateModel(this);
    }

    private void GetData()
    {
        // Call a static method that gets all the fields from the database for this model
        // Database.GetModel(this);
    }
}

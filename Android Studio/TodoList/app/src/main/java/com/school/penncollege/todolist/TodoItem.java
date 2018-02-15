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


    public TodoItem(String t)
    {
        this.Title = t;
        this.DateCreated = new Date();
        this.IsDone = false;
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
    }

    public String GetTitle()
    {
        return this.Title;
    }

    public Date GetCreationTime()
    {
        return this.DateCreated;
    }

}

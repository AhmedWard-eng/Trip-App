package com.example.trip_app;

import java.util.ArrayList;
import java.util.Date;

public class Trip
{
    private String title, start, end, status;
    private Date date;
    private ArrayList<String> notes;
    private Boolean isRound;
    private int colorId;

    public Trip(String title){ this.title = title; }

    public Trip(String title, String start, String end, String status, Date date, int colorId)
    {
        this.title = title;
        this.start = start;
        this.end = end;
        this.status = status;
        this.date = date;
        this.colorId = colorId;
    }

    public Trip(String title, String start, String end, Date date, ArrayList<String> notes, Boolean isRound)
    {
        this.title = title;
        this.start = start;
        this.end = end;
        this.date = date;
        this.notes = notes;
        this.isRound = isRound;
    }

    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStart()
    {
        return start;
    }
    public void setStart(String start)
    {
        this.start = start;
    }

    public String getEnd()
    {
        return end;
    }
    public void setEnd(String end)
    {
        this.end = end;
    }

    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }

    public ArrayList<String> getNotes()
    {
        return notes;
    }
    public void setNotes(ArrayList<String> notes)
    {
        this.notes = notes;
    }

    public Boolean isRound()
    {
        return isRound;
    }
    public void isRound(Boolean round)
    {
        isRound = round;
    }

    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getColorId()
    {
        return colorId;
    }
    public void setColorId(int colorId)
    {
        this.colorId = colorId;
    }
}

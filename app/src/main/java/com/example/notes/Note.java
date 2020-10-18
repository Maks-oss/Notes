package com.example.notes;

import java.io.Serializable;

public class Note implements Serializable {
    private String name, date, text;

    public String getName() {
        return name;
    }

    public Note(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Note(String name, String date, String text) {
        this.name = name;
        this.date = date;
        this.text = text;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

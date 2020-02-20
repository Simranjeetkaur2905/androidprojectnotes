package com.example.notesapplication;

public class Notes {
    int id;
    String nameofnote , description ,date ,time;

    public Notes(int id, String nameofnote, String description, String date, String time) {
        this.id = id;
        this.nameofnote = nameofnote;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getNameofnote() {
        return nameofnote;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}

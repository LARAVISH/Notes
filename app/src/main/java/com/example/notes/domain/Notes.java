package com.example.notes.domain;

import java.util.Date;

public class Notes {

    private final String id;
    private final String name;
    private final String message;
    private final Date currentDate;

    public Notes(String id, String name, String message, Date currentDate) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.currentDate = currentDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Date getCurrentDate() {
        return currentDate;
    }
}

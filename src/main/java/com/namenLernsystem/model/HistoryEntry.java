package com.namenLernsystem.model;

import javax.ws.rs.FormParam;

public class HistoryEntry {
    @FormParam("message")
    private String message;

    public HistoryEntry(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

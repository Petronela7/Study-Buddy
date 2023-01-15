package com.example.studybuddy.model;


import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

public class Session implements Serializable {

    private int sessionId;
    private String sessionName;
    private String sessionGroup;
    private String sessionYear;
    private String sessionStartDate;
    private String sessionStartTime;
    private String sessionLocation;

    public Session(int sessionId, String sessionName, String sessionSubject, String sessionYear, String sessionStartDate, String sessionStartTime, String sessionLocation) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.sessionGroup = sessionSubject;
        this.sessionYear = sessionYear;
        this.sessionStartDate = sessionStartDate;
        this.sessionStartTime = sessionStartTime;
        this.sessionLocation = sessionLocation;
    }

    public Session() {

    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionGroup() {
        return sessionGroup;
    }

    public void setSessionGroup(String sessionSubject) {
        this.sessionGroup = sessionSubject;
    }

    public String getSessionYear() {
        return sessionYear;
    }

    public void setSessionYear(String sessionYear) {
        this.sessionYear = sessionYear;
    }

    public String getSessionStartDate() {
        return sessionStartDate;
    }

    public void setSessionStartDate(String sessionStartDate) {
        this.sessionStartDate = sessionStartDate;
    }

    public String getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(String sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public String getSessionLocation() {
        return sessionLocation;
    }

    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }
}

package com.project.appproject.utilities;

public class Authentication {
    private String sessionId;
    private int personType;
    private int personId;
    private int klasseId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getPersonType() {
        return personType;
    }

    public void setPersonType(int personType) {
        this.personType = personType;
    }

    public int getKlasseId() {
        return klasseId;
    }

    public void setKlasseId(int klasseId) {
        this.klasseId = klasseId;
    }
}

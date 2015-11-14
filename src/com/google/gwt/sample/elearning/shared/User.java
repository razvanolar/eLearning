package com.google.gwt.sample.elearning.shared;

/**
 * Created by Horea on 14/11/2015.
 */
public class User {
    private String userid;
    private String nume;
    private String parola;
    private String prenume;
    private String email;

    public User() {
    }

    public User(String userid, String nume, String parola, String prenume, String email) {
        this.userid = userid;
        this.nume = nume;
        this.parola = parola;
        this.prenume = prenume;
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public String getNume() {
        return nume;
    }

    public String getParola() {
        return parola;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getEmail() {
        return email;
    }
}

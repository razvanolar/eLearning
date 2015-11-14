package com.google.gwt.sample.elearning.shared;

/**
 * Created by Horea on 14/11/2015.
 */
public class Curs {
    Profesor profesor;
    String denumire;

    public Curs() {
    }

    public Curs(Profesor profesor, String denumire) {
        this.profesor = profesor;
        this.denumire = denumire;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getDenumire() {
        return denumire;
    }
}

package com.google.gwt.sample.elearning.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horea on 14/11/2015.
 */
public class Student extends User {

    long nrMatricol;
    List<Curs> cursuriInscrise = new ArrayList<Curs>();

    public Student() {
    }

    public Student(String userid, String nume, String parola, String prenume, String email, long nrMatricol) {
        super(userid, nume, parola, prenume, email);
        this.nrMatricol = nrMatricol;
    }

    public long getNrMatricol() {
        return nrMatricol;
    }

    public List<Curs> getCursuriInscrise() {
        return cursuriInscrise;
    }

    public void addCurs(Curs curs) {
        if(!cursuriInscrise.contains(curs)) {
            cursuriInscrise.add(curs);
        } else {
            throw new RuntimeException("Sunteti deja inscris la acest curs!");
        }
    }
}

package com.google.gwt.sample.elearning.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horea on 14/11/2015.
 */
public class Profesor extends User {

    List<Curs> cursuriPredate = new ArrayList<>();

    public Profesor() {
    }

    public Profesor(String userid, String nume, String parola, String prenume, String email) {
        super(userid, nume, parola, prenume, email);
    }

    public List<Curs> getCursuriInscrise() {
        return cursuriPredate;
    }

    public void addCurs(Curs curs) {
        if(!cursuriPredate.contains(curs)) {
            cursuriPredate.add(curs);
        } else {
            throw new RuntimeException("Sunteti deja inscris la acest curs!");
        }
    }
}

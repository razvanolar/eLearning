package com.google.gwt.sample.elearning.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horea on 14/11/2015.
 */
public class Professor extends UserData {

    List<Lecture> teachedLectures = new ArrayList<>();

    public Professor() {
    }

    public Professor(String userId, String password, String firstName, String lastName, String email) {
        super(userId, password, firstName, lastName, email);
    }

    public List<Lecture> getTeachedLectures() {
        return teachedLectures;
    }

    public void addCurs(Lecture curs) {
        if(!teachedLectures.contains(curs)) {
            teachedLectures.add(curs);
        } else {
            throw new RuntimeException("You are already enrolled to this lecture!");
        }
    }
}

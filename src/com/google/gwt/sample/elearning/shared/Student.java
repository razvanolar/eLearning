package com.google.gwt.sample.elearning.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horea on 14/11/2015.
 */
public class Student extends UserData {

    long registrationNo;
    List<Lecture> enrolledCourses = new ArrayList<Lecture>();

    public Student() {
    }

    public Student(String userId, String password, String firstName, String lastName, String email,
        long registrationNo) {
        super(userId, password, firstName, lastName, email);
        this.registrationNo = registrationNo;
    }


    public long getRegistrationNo() {
        return registrationNo;
    }

    public List<Lecture> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addCurs(Lecture curs) {
        if(!enrolledCourses.contains(curs)) {
            enrolledCourses.add(curs);
        } else {
            throw new RuntimeException("You are already enrolled to this lecture!");
        }
    }
}

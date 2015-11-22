package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;

import javax.jws.soap.SOAPBinding;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by Horea on 14/11/2015.
 */
public class Professor extends UserData {

  List<Lecture> teachedLectures = new ArrayList<Lecture>();

  public Professor() {
  }

  public Professor(long id, String username, String password, String firstName, String lastName, String email) {
    super(id, username, password, firstName, lastName, email, UserRoleTypes.PROFESSOR);
  }

  public Professor(String username, String password, String firstName, String lastName, String email) {
    super(username, password, firstName, lastName, email, UserRoleTypes.PROFESSOR);
  }

  public Professor(UserData user){
    super(user.getId(), user.getUsername() , user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), UserRoleTypes.PROFESSOR);
  }

  public List<Lecture> getTeachedLectures() {
    return teachedLectures;
  }

  public void addCurs(Lecture curs) {
    if (!teachedLectures.contains(curs)) {
      teachedLectures.add(curs);
    } else {
      throw new RuntimeException("You are already enrolled to this lecture!");
    }
  }

  @Override
  public String toString() {
    return super.getFirstName() + " " + super.getLastName();
  }
}

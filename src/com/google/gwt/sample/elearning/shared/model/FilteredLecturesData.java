package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 *
 * Created by razvanolar on 29.12.2015.
 */
public class FilteredLecturesData implements IsSerializable {

  private List<Lecture> enrolledLectures;
  private List<Lecture> unenrolledLectures;

  public FilteredLecturesData() {}

  public FilteredLecturesData(List<Lecture> enrolledLectures, List<Lecture> unenrolledLectures) {
    this.enrolledLectures = enrolledLectures;
    this.unenrolledLectures = unenrolledLectures;
  }

  public List<Lecture> getEnrolledLectures() {
    return enrolledLectures;
  }

  public List<Lecture> getUnenrolledLectures() {
    return unenrolledLectures;
  }
}

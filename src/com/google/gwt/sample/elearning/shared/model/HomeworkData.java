package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Ambrozie Paval on 12/12/2015.
 */
public class HomeworkData implements IsSerializable {
  private long id;
  private String title;
  private int score;
  private String text;
  private long courseId;

  public HomeworkData() {
  }

  public HomeworkData(long id, String title, int score, String text) {
    this.id = id;
    this.title = title;
    this.score = score;
    this.text = text;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }
}

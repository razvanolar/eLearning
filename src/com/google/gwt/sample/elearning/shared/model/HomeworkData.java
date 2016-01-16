package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

/**
 * Created by Ambrozie Paval on 12/12/2015.
 */
public class HomeworkData implements IsSerializable {
  private long id;
  private String title;
  private Date beginDate = new Date();
  private Date endDate = new Date();
  private String text;
  private long courseId;
  private int score;


  public HomeworkData() {
  }

  public HomeworkData(long id, String title, int score, String text) {
    this.id = id;
    this.title = title;
    this.score = score;
    this.text = text;
  }

  public HomeworkData(long id, String title, Date beginDate, Date endDate, long courseId, int score) {
    this.id = id;
    this.title = title;
    this.beginDate = beginDate;
    this.endDate = endDate;
    this.courseId = courseId;
    this.score = score;
  }
  public HomeworkData(String title, Date beginDate, Date endDate, long courseId, int score) {
    this.title = title;
    this.beginDate = beginDate;
    this.endDate = endDate;
    this.courseId = courseId;
    this.score = score;
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

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}

package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 *
 * Created by razvanolar on 30.11.2015.
 */
public class LectureTestData implements IsSerializable {

  private long id;
  private String name;
  private long duration;
  private long lectureId;
  private List<QuestionData> questions;

  public LectureTestData() {}

  public LectureTestData(long lectureId, String name, long duration, List<QuestionData> questions) {
    this.lectureId = lectureId;
    this.name = name;
    this.duration = duration;
    this.questions = questions;
  }

  public LectureTestData(long id, String name, long duration, long courseId, List<QuestionData> questions) {
    this.id = id;
    this.name = name;
    this.duration = duration;
    this.lectureId = courseId;
    this.questions = questions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<QuestionData> getQuestions() {
    return questions;
  }

  public void setQuestions(List<QuestionData> questions) {
    this.questions = questions;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getLectureId() {
    return lectureId;
  }

  public void setLectureId(long lectureId) {
    this.lectureId = lectureId;
  }
}

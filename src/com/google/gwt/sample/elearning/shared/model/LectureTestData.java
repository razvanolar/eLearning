package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 *
 * Created by razvanolar on 30.11.2015.
 */
public class LectureTestData implements IsSerializable {

  private String name;
  private long duration;
  private List<QuestionData> questions;

  public LectureTestData() {}

  public LectureTestData(String name, long duration, List<QuestionData> questions) {
    this.name = name;
    this.duration = duration;
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
}

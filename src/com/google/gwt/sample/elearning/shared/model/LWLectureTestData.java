package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by razvanolar on 27.11.2015.
 *
 * A light weight version of LectureTestData
 */
public class LWLectureTestData implements IsSerializable {

  private String name;
  private int questionsNo;
  private int totalScore;

  public LWLectureTestData() {}

  public LWLectureTestData(String name, int questionsNo, int totalScore) {
    this.name = name;
    this.questionsNo = questionsNo;
    this.totalScore = totalScore;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getQuestionsNo() {
    return questionsNo;
  }

  public void setQuestionsNo(int questionsNo) {
    this.questionsNo = questionsNo;
  }

  public int getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(int totalScore) {
    this.totalScore = totalScore;
  }
}

package com.google.gwt.sample.elearning.shared.model;

/***
 * Created by razvanolar on 27.11.2015.
 *
 * A light weight version of LectureTestData
 */
public class LWLectureTestData {

  private String key;
  private String name;
  private int questionsNo;
  private int totalScore;

  public LWLectureTestData(String name, int questionsNo, int totalScore) {
    this.key = name;
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

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}

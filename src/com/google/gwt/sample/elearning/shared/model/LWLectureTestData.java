package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razvanolar on 27.11.2015.
 *
 * A light weight version of LectureTestData
 */
public class LWLectureTestData implements IsSerializable {

  private String key;
  private long id;
  private String name;
  private int questionsNo;
  private int totalScore;
  private long courseId;
  private ArrayList<TestQuestion> questions;

  public LWLectureTestData() {}

  public LWLectureTestData(String name, int questionsNo, int totalScore) {
    this.name = name;
    this.questionsNo = questionsNo;
    this.totalScore = totalScore;
  }

  public LWLectureTestData(String name, int questionsNo, int totalScore, long courseId) {
    this.key = name;
    this.name = name;
    this.questionsNo = questionsNo;
    this.totalScore = totalScore;
    this.courseId = courseId;
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

  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ArrayList<TestQuestion> getQuestions() {
    return questions;
  }

  public void setQuestions(ArrayList<TestQuestion> questions) {
    this.questions = questions;
  }

}

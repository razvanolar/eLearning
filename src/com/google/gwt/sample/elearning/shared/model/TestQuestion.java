package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Ambrozie Paval on 11/30/2015.
 */
public class TestQuestion implements IsSerializable{
  private String question;
  private int score;
  private ArrayList<AnswerData> answers;

  public TestQuestion(String question, int score) {
    this.question = question;
    this.score = score;
    answers = new ArrayList<AnswerData>();
  }

  public TestQuestion() {
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public List<AnswerData> getAnswers() {
    return answers;
  }

  public void setAnswers(ArrayList<AnswerData> answers) {
    this.answers = answers;
  }
}

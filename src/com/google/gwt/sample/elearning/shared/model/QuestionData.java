package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 30.11.2015.
 */
public class QuestionData implements IsSerializable {

  private long score = 0;
  private String value = "";
  private List<AnswerData> answers;

  public QuestionData() {}

  public QuestionData(String value, long score, List<AnswerData> answers) {
    this.value = value;
    this.score = score;
    this.answers = answers;
  }

  public long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }

  public List<AnswerData> getAnswers() {
    return answers;
  }

  public void setAnswers(List<AnswerData> answers) {
    this.answers = answers;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void addAnswer(AnswerData answer) {
    if (answers == null)
      answers = new ArrayList<AnswerData>();
    answers.add(answer);
  }
}

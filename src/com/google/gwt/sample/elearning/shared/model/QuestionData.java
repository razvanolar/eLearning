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
  private String question = "";
  private List<AnswerData> answers;

  public QuestionData() {}

  public QuestionData(String question, long score, List<AnswerData> answers) {
    this.question = question;
    this.score = score;
    this.answers = answers;
  }

  public QuestionData(String question, long score) {
    this.question = question;
    this.score = score;
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

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public void addAnswer(AnswerData answer) {
    if (answers == null)
      answers = new ArrayList<AnswerData>();
    answers.add(answer);
  }
}

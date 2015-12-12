package com.google.gwt.sample.elearning.server.service.collector.test;

import com.google.gwt.sample.elearning.shared.model.AnswerData;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;
import com.google.gwt.sample.elearning.shared.model.QuestionData;

/**
 *
 * Created by Ambrozie Paval on 12/12/2015.
 */
public class TestXMLConvertor {
  public static final String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

  public String convertToXML(LectureTestData test) {
    String rez = xmlHeader;
    rez += "\n<" + TestElementTypes.TEST.name() + " id=\"" + test.getId() + "\" name=\"" + test.getName() + "\" duration=\"" + test.getDuration() + "\" course=\"" + test.getCourseId() + "\">";
    rez += convertTestToXML(test);
    rez += "\n</" + TestElementTypes.TEST.name() + ">";
    return rez;
  }

  private String convertTestToXML(LectureTestData test) {
    StringBuilder testXML = new StringBuilder();
    for (QuestionData questionData : test.getQuestions()) {
      testXML.append(convertQuestionToXML(questionData));
    }
    return testXML.toString();
  }

  private String convertQuestionToXML(QuestionData questionData) {
    StringBuilder questionXML = new StringBuilder();
    questionXML.append("\n\t<").append(TestElementTypes.QUESTION.name()).append(" value=\"").append(questionData.getQuestion()).append("\" score=\"").append(questionData.getScore()).append("\">");
    for (AnswerData answerData : questionData.getAnswers()) {
      questionXML.append(convertAnswerToXML(answerData));
    }
    questionXML.append("\n\t</").append(TestElementTypes.QUESTION.name()).append(">");
    return questionXML.toString();
  }

  private String convertAnswerToXML(AnswerData answerData) {
    return "\n\t\t<" + TestElementTypes.ANSWER.name() + " isCorrect=\"" + answerData.isTrue() + "\">" +
        answerData.getValue() +
        "</" + TestElementTypes.ANSWER.name() + ">";
  }
}

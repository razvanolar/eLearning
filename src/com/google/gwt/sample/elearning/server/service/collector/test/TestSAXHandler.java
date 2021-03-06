package com.google.gwt.sample.elearning.server.service.collector.test;

import com.google.gwt.sample.elearning.shared.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 *
 * Created by Ambrozie Paval on 11/30/2015.
 */
public class TestSAXHandler extends DefaultHandler{
  private String content = null;
  private static LectureTestData testData;
  private QuestionData testQuestion;
  private static ArrayList<QuestionData> questions;
  private AnswerData answerData;
  private static ArrayList<AnswerData> answers;

  public static LectureTestData getTestData() {
    return testData;
  }

  @Override
  public void startElement(String uri, String localName, String tagName, Attributes attributes) throws SAXException {
    TestElementTypes startTag = TestElementTypes.valueOf(tagName.toUpperCase());
    switch (startTag){
      case TEST:
        questions = new ArrayList<>();
        testData = new LectureTestData();
        String id = attributes.getValue("id");
        testData.setId(Long.parseLong(id));
        String name = attributes.getValue("name");
        testData.setName(name);
        String duration = attributes.getValue("duration");
        testData.setDuration(Long.parseLong(duration));
        String courseId = attributes.getValue("course");
        testData.setLectureId(Long.parseLong(courseId));
        break;
      case QUESTION:
        answers = new ArrayList<>();
        String question = attributes.getValue("value");
        int score = Integer.parseInt(attributes.getValue("score"));
        testQuestion = new QuestionData(question, score);
        break;
      case ANSWER:
        answerData = new AnswerData();
        answerData.setIsTrue(Boolean.parseBoolean(attributes.getValue("isCorrect")));
        break;
    }
  }

  @Override
  public void endElement(String uri, String localName, String tagName) throws SAXException {
    TestElementTypes endTag = TestElementTypes.valueOf(tagName.toUpperCase());
    switch (endTag){
      case TEST:
        testData.setQuestions(questions);
        break;
      case QUESTION:
        testQuestion.setAnswers(answers);
        questions.add(testQuestion);
        break;
      case ANSWER:
        answerData.setValue(content);
        answers.add(answerData);
        break;
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    content = String.copyValueOf(ch, start, length).trim();
  }
}

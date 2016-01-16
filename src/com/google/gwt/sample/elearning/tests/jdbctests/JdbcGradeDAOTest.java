package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.server.repository.JDBC.*;
import com.google.gwt.sample.elearning.server.service.LectureServiceImpl;
import com.google.gwt.sample.elearning.shared.model.*;
import org.junit.Test;

import java.util.*;

/**
 * Created by Valy on 1/9/2016.
 */
public class JdbcGradeDAOTest {
  GradeDAO gradeDAO = new JdbcGradeDAO();
  HomeworkDAO homeworkDAO = new JdbcHomeworkDAO();
  LectureTestDAO lectureTestDAO = new JdbcLectureTestDAO();
  UserDAO userDAO = new JdbcUserDAO();
  LectureServiceImpl lectureService = new LectureServiceImpl();

  @Test
  public void addDataTest() {
    LectureTestData lectureTestData = lectureTestDAO.getLectureTestById(3);
    UserData userData = userDAO.getUserById(3);
    //create user response
    List<AnswerData> answers = new ArrayList<AnswerData>();
    AnswerData answerData1 = new AnswerData("a",true);
    AnswerData answerData2 = new AnswerData("b",false);
    AnswerData answerData3 = new AnswerData("c",true);
    answerData1.setIsSelected(true);
    answerData2.setIsSelected(false);
    answerData3.setIsSelected(true);
    answers.add(answerData1);
    answers.add(answerData2);
    answers.add(answerData3);
    List<QuestionData> questions = new ArrayList<QuestionData>();
    QuestionData questionData = new QuestionData("test",60,answers);
    questions.add(questionData);

    List<AnswerData> answers2 = new ArrayList<AnswerData>();
    AnswerData answerData4 = new AnswerData("a",true);
    AnswerData answerData5 = new AnswerData("b",false);
    AnswerData answerData6 = new AnswerData("c",true);
    answerData4.setIsSelected(true);
    answerData5.setIsSelected(false);
    answerData6.setIsSelected(false);
    answers2.add(answerData4);
    answers2.add(answerData5);
    answers2.add(answerData6);
    QuestionData questionData2 = new QuestionData("test2",40,answers2);
    questions.add(questionData2);
    lectureTestData.setQuestions(questions);

    lectureService.resolveTest(userData.getId(),lectureTestData);
  }
}

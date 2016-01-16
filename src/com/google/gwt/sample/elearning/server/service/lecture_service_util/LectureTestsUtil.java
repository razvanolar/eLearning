package com.google.gwt.sample.elearning.server.service.lecture_service_util;

import com.google.gwt.sample.elearning.server.ServerUtil;
import com.google.gwt.sample.elearning.server.service.collector.test.TestXMLHandler;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.LWLectureTestData;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;
import com.google.gwt.sample.elearning.shared.model.UserData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 29.11.2015.
 */
public class LectureTestsUtil {

  public LectureTestData getTest(UserData user, long lectureId, String testName) {
    try {
      File testFile = new File(ServerUtil.getLectureTestsDirectoryPath(lectureId) + testName + ".xml");
      if (!testFile.exists())
        throw new FileNotFoundException();
      BufferedReader reader = new BufferedReader(new FileReader(testFile));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null)
        stringBuilder.append(line);
      TestXMLHandler testXMLHandler = new TestXMLHandler(stringBuilder.toString());
      return testXMLHandler.parse();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
      throw new ELearningException("Specified file does not exist. userId: " + user.getId() + " lectureId: " + lectureId +
          " testName: " + testName, ex);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new ELearningException("Failed to retrieve test data" + user.getId() + " lectureId: " + lectureId +
              " testName: " + testName, ex);
    }
  }

  public List<LWLectureTestData> getAllLWTests(UserData user, long lectureId) throws ELearningException {
    try {
      File testsPath = new File(ServerUtil.getLectureTestsDirectoryPath(lectureId));
      if (!testsPath.exists())
        throw new FileNotFoundException();

      List<LWLectureTestData> result = new ArrayList<LWLectureTestData>();

      File[] files = testsPath.listFiles();
      if (files != null)
        for (File file : files)
          if (file.getName().endsWith(".props")) {
            LWLectureTestData testData = createLWDataFromProp(file);
            if (testData != null)
              result.add(testData);
          }
      return result;
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
      throw new ELearningException("Specified file does not exists. userId: " + user.getId() + " and lectureId: "
              + lectureId);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new ELearningException("Failed to retrieve tests for userId: " + user.getId() + " and lectureId: "
              + lectureId + ". Error message: " + ex.getMessage(), ex);
    }
  }


  private LWLectureTestData createLWDataFromProp(File propFile) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(propFile));

      String line;
      String name = propFile.getName().replace(".props", "");
      int questionNo = 0, totalScore = 0;
      while ((line = reader.readLine()) != null) {
        if (line.contains("QUESTIONS_NO="))
          questionNo = Integer.parseInt(line.split("=")[1].trim());
        if (line.contains("TOTAL_SCORE="))
          totalScore = Integer.parseInt(line.split("=")[1].trim());
      }
      reader.close();
      return new LWLectureTestData(name, questionNo, totalScore);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void createTestXMLFile(String xml, String title, long lectureId) throws ELearningException{
    try {
      String testsPath = ServerUtil.getLectureTestsDirectoryPath(lectureId);
      File file = new File(testsPath);
      if (!file.exists() && !file.mkdirs())
        throw new ELearningException("File " + title + " can not be created");
      File testFile = new File(testsPath + title + ".xml");
      BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
      writer.write(xml);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new ELearningException(e.getMessage(), e);
    }
  }

  public void createTestPropsFile(int questionNo, int totalScore, String title, long lectureId) throws ELearningException{
    try {
      String testsPath = ServerUtil.getLectureTestsDirectoryPath(lectureId);
      File file = new File(testsPath);
      if (!file.exists() && !file.mkdirs())
        throw new ELearningException("File " + title + " can not be created");
      File testFile = new File(testsPath + title + ".props");
      BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
      String result = "QUESTIONS_NO=" + questionNo + "\nTOTAL_SCORE=" + totalScore + "\nSOLVED=FALSE";
      writer.write(result);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new ELearningException(e.getMessage(), e);
    }
  }

  public void updateTestFiles() throws ELearningException {

  }
}

package com.google.gwt.sample.elearning.server.service.lecture_service_util;

import com.google.gwt.sample.elearning.server.ServerUtil;
import com.google.gwt.sample.elearning.server.service.collector.homework.HomeworkXMLHandler;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.sample.elearning.shared.model.UserData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Cristi on 12/23/2015.
 */
public class LectureHomeworkUtil {
  public List<HomeworkData> getAllHomeworks(UserData user, long lectureId){
    try {
      File lecturesPath = new File(ServerUtil.getLectureHomeworksDirectoryPath(lectureId));
      if (!lecturesPath.exists())
        throw new FileNotFoundException();

      List<HomeworkData> result = new ArrayList<>();

      File[] files = lecturesPath.listFiles();
      if (files != null)
        for (File file : files) {
          HomeworkData homeworkData = createHomeworkFromXML(file);
          if (homeworkData != null)
            result.add(homeworkData);
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

  private HomeworkData createHomeworkFromXML(File file) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null)
        stringBuilder.append(line);
      HomeworkXMLHandler homeworkXMLHandler = new HomeworkXMLHandler(stringBuilder.toString());
      return homeworkXMLHandler.parse();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
      throw new ELearningException("Specified file does not exist", ex);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new ELearningException("Failed to retrieve test data", ex);
    }
  }

  public void createHomework(String xml, long lectureId, HomeworkData homeworkData) {
    try {
      String homeworkPath = ServerUtil.getLectureHomeworksDirectoryPath(lectureId);
      File file = new File(homeworkPath);
      if (!file.exists() && !file.mkdirs())
        throw new ELearningException("File " + homeworkData.getTitle() + " can not be created");
      File testFile = new File(homeworkPath + homeworkData.getTitle() + ".xml");
      BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
      writer.write(xml);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new ELearningException(e.getMessage(), e);
    }
  }

  public void updateHomework(long lectureId, HomeworkData homeworkData) {

  }

  public void deleteHomework(long lectureId, HomeworkData homeworkData) {

  }
}

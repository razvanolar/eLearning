package com.google.gwt.sample.elearning.server.service.lecture_service_util;

import com.google.gwt.sample.elearning.server.ServerUtil;
import com.google.gwt.sample.elearning.server.service.collector.homework.HomeworkXMLHandler;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.sample.elearning.shared.model.UserData;
import org.apache.commons.io.FileUtils;

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
      if (!lecturesPath.exists() && !lecturesPath.mkdirs())
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
    try (BufferedReader reader = new BufferedReader(new FileReader(file))){
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
      String homeworkDirPath = ServerUtil.getLectureHomeworksDirectoryPath(lectureId);
      String homeworkProjPath = ServerUtil.getLectureHomeworksProjectPath(lectureId);
      File dirFile = new File(homeworkDirPath);
      File projFile = new File(homeworkProjPath);
      if (!dirFile.exists() && !dirFile.mkdirs())
        throw new ELearningException("File " + homeworkData.getTitle() + " can not be created");
      if (!projFile.exists() && !projFile.mkdirs())
        throw new ELearningException("File " + homeworkData.getTitle() + " can not be created");
      File testDirFile = new File(homeworkDirPath + homeworkData.getTitle() + ".xml");
      File testProjFile = new File(homeworkProjPath + homeworkData.getTitle() + ".xml");
      BufferedWriter dirWriter = new BufferedWriter(new FileWriter(testDirFile));
      BufferedWriter projWriter = new BufferedWriter(new FileWriter(testProjFile));
      dirWriter.write(xml);
      projWriter.write(xml);
      dirWriter.close();
      projWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new ELearningException(e.getMessage(), e);
    }
  }

  public void updateHomework(String xml,long lectureId, HomeworkData homeworkData) {
    String homeworkDirPath = ServerUtil.getLectureHomeworksDirectoryPath(lectureId)+homeworkData.getTitle()+ ".xml";
    String homeworkProjPath = ServerUtil.getLectureHomeworksProjectPath(lectureId)+homeworkData.getTitle()+ ".xml";
    File dirFile = new File(homeworkDirPath);
    File projFile = new File(homeworkProjPath);
    if (!dirFile.exists())
      throw new ELearningException("File " + homeworkData.getTitle() + " can not be updated");
    if (!projFile.exists())
      throw new ELearningException("File " + homeworkData.getTitle() + " can not be updated");
    else{
      try {
        FileUtils.forceDelete(dirFile);
        FileUtils.forceDelete(projFile);
        File testDirFile = new File(homeworkDirPath);
        File testProjFile = new File(homeworkProjPath);
        BufferedWriter dirWriter = new BufferedWriter(new FileWriter(testDirFile));
        BufferedWriter projWriter = new BufferedWriter(new FileWriter(testProjFile));
        dirWriter.write(xml);
        projWriter.write(xml);
        dirWriter.close();
        projWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public void deleteHomework(long lectureId, HomeworkData homeworkData) {
    try {
      String homeworkDirPath = ServerUtil.getLectureHomeworksDirectoryPath(lectureId)+homeworkData.getTitle()+ ".xml";
      String homeworkProjPath = ServerUtil.getLectureHomeworksProjectPath(lectureId)+homeworkData.getTitle()+ ".xml";
      File dirFile = new File(homeworkDirPath);
      File projFile = new File(homeworkProjPath);
      if (!dirFile.exists())
        throw new ELearningException("File " + homeworkData.getTitle() + " can not be deleted");
      if (!projFile.exists())
        throw new ELearningException("File " + homeworkData.getTitle() + " can not be deleted");
      FileUtils.forceDelete(dirFile);
      FileUtils.forceDelete(projFile);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ELearningException(e.getMessage(), e);
    }
  }

  public void resolveHomework(long lectureid, long professorId, long userId, String homeworkTitle, String solution) {
    try{
      String homeworkProjPath = ServerUtil.getResolvedHomeworksProjectPath(professorId, userId);
      String homeworkDirPath = ServerUtil.getResolvedHomeworksDirectoryPath(professorId, userId);
      File projFile = new File(homeworkProjPath);
      File dirFile = new File(homeworkDirPath);
      if (!dirFile.exists() && !dirFile.mkdirs())
        throw new ELearningException("File " + homeworkTitle + " can not be created");
      if (!projFile.exists() && !projFile.mkdirs())
        throw new ELearningException("File " + homeworkTitle + " can not be created");

      File homeWorkProjFile = new File(homeworkProjPath + homeworkTitle + ".txt");
      File homeWorkDirFile = new File(homeworkDirPath + homeworkTitle + ".txt");
      BufferedWriter dirWriter = new BufferedWriter(new FileWriter(homeWorkDirFile));
      BufferedWriter projWriter = new BufferedWriter(new FileWriter(homeWorkProjFile));
      dirWriter.write(solution);
      projWriter.write(solution);
      dirWriter.close();
      projWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new ELearningException(e.getMessage(), e);
    }
  }
}

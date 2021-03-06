package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.FileExtensionTypes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * Created by razvanolar on 23.11.2015.
 */
public class ServerUtil {
  public static final String PATH = "elearning\\app_files\\";
  public static final String LECTURES_PATH = "lectures\\";
  public static final String TESTS_PATH = "tests\\";
  public static final String HOMEWORKS_PATH = "homeworks\\";
  public static final String FILES_PATH = "files\\";
  public static final String LOG_PATH = "logs\\";
  public static final String USER_SOLVED_TESTS_PATH = "user_solved_tests\\";
  public static final String FORUMS_PATH = "forums\\";
  public static final String RESOLVED_HOMEWORKS_PATH = "resolved_homeworks\\";
  public static final String HTML_DOCUMENT = "<!DOCTYPE html>\n"
      + "<html>"
      + "\t<head>\n"
      + "\t\t<title>{0}</title>\n"
      + "\t</head>\n"
      + "\t<body>\n"
      + "{1}\n"
      + "\t</body>\n"
      + "</html>";

  public static String getHtmlDocumentText(String title, String content) {
    String doc = HTML_DOCUMENT;
    doc = doc.replace("{0}", title);
    doc = doc.replace("{1}", content);
    return doc;
  }

  public static String getUserDirectoryPath(long userId) {
    return PATH + userId + "\\";
  }

  public static String getUserDirectoryPath(UserData user) {
    return getUserDirectoryPath(user.getId());
  }

  public static String getLectureFilesDirectoryPath(long lectureId) {
    return PATH + LECTURES_PATH + lectureId + "\\" + FILES_PATH;
  }

  public static String getLecturesFileProjectPath(long lectureId) {
    return ProjectDirPath.PROJECT_DIR_PATH.getPath() + getLectureFilesDirectoryPath(lectureId);
  }

  public static String getLectureTestsDirectoryPath(long lectureId) {
    return PATH + LECTURES_PATH + lectureId + "\\" + TESTS_PATH;
  }

  public static String getLectureTestsProjectPath(long lectureId) {
    return ProjectDirPath.PROJECT_DIR_PATH.getPath() + getLectureTestsDirectoryPath(lectureId);
  }

  public static String getLectureForumsPath(long lectureId){
    return ProjectDirPath.PROJECT_DIR_PATH.getPath() + PATH + LECTURES_PATH + lectureId + "\\" + FORUMS_PATH;
  }

  public static FileExtensionTypes getFileExensionByName(String fileName) {
    for (FileExtensionTypes extension : FileExtensionTypes.values())
      if (fileName.endsWith(extension.getValue()))
        return extension;
    return null;
  }

  public static String getFileContentType(String fileName) {
    FileExtensionTypes extension = getFileExensionByName(fileName);
    if (extension == null)
      return "application/octet-stream";
    if (extension == FileExtensionTypes.HTML)
      return "text/html";
    if (extension == FileExtensionTypes.PDF)
      return "application/pdf";
    if (extension == FileExtensionTypes.PNG)
      return "image/png";
    if (extension == FileExtensionTypes.XML)
      return "application/xml";
    return "application/octet-stream";
  }

  public static String getLectureHomeworksDirectoryPath(long lectureId) {
    return PATH + LECTURES_PATH + lectureId + "\\" + HOMEWORKS_PATH;
  }

  public static String getLectureHomeworksProjectPath(long lectureId) {
    return ProjectDirPath.PROJECT_DIR_PATH.getPath() + getLectureHomeworksDirectoryPath(lectureId);
  }

  public static String getLogFile() {
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();
    return ProjectDirPath.PROJECT_DIR_PATH.getPath() + PATH + LOG_PATH + "log" + dateFormat.format(date);
  }

  public static String getLogsPath() {
    return ProjectDirPath.PROJECT_DIR_PATH.getPath() + PATH + LOG_PATH;
  }

  public static String getUserSolvedTestsDirectoryPath(long usersId) {
    return PATH + USER_SOLVED_TESTS_PATH + usersId + "\\";
  }

  public static String getUserSolvedTestsProjectPath(long usersId) {
    return ProjectDirPath.PROJECT_DIR_PATH.getPath() + getUserSolvedTestsDirectoryPath(usersId);
  }

  public static String getResolvedHomeworksDirectoryPath(long professorId, long userId){
    return getUserDirectoryPath(professorId) + RESOLVED_HOMEWORKS_PATH + userId + "\\";
  }

  public static String getResolvedHomeworksProjectPath(long professorId, long userId){
    return ProjectDirPath.PROJECT_DIR_PATH.getPath() + getResolvedHomeworksDirectoryPath(professorId, userId);
  }

}

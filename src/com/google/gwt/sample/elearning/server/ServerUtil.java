package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.FileExtensionTypes;

/***
 * Created by razvanolar on 23.11.2015.
 */
public class ServerUtil {
  public static final String PATH = "elearning\\app_files\\";
  public static final String LECTURES_PATH = "lectures\\";
  public static final String TESTS_PATH = "tests\\";
  public static final String FILES_PATH = "files\\";
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

  public static String getLectureTestsDirectoryPath(long lectureId) {
    return PATH + LECTURES_PATH + lectureId + "\\" + TESTS_PATH;
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
}

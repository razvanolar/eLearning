package com.google.gwt.sample.elearning.server;

import com.google.gwt.sample.elearning.shared.model.UserData;

/***
 * Created by razvanolar on 23.11.2015.
 */
public class ServerUtil {
  public static final String PATH = "c:\\elearning\\";
  public static final String LECTURES_PATH = "lectures\\";

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

  public static String getUserDirectoryPath(UserData user) {
    return PATH + user.getId() + "\\";
  }

  public static String getUserLectureDirectoryPath(UserData user, long lectureId) {
    return getUserDirectoryPath(user) + LECTURES_PATH + lectureId + "\\";
  }
}

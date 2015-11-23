package com.google.gwt.sample.elearning.server;

/***
 * Created by razvanolar on 23.11.2015.
 */
public class ServerUtil {
  public static final String PATH = "c:\\elearning\\";
  public static final String LECTURES_PATH = PATH + "lectures\\";

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
}

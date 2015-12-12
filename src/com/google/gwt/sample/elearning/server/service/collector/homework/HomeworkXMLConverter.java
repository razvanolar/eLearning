package com.google.gwt.sample.elearning.server.service.collector.homework;

import com.google.gwt.sample.elearning.server.service.collector.test.TestElementTypes;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;

/**
 * Created by Ambrozie Paval on 12/12/2015.
 */
public class HomeworkXMLConverter {
  public static final String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

  public String convertToXML(HomeworkData homework) {
    String rez = xmlHeader;
    rez += "\n<" + HomeworkElementTypes.HOMEWORK.name() + " id=\"" + homework.getId() + "\">";
    rez += convertAttributesToXML(homework);
    rez += "\n</" + HomeworkElementTypes.HOMEWORK.name() + ">";
    return rez;
  }

  private String convertAttributesToXML(HomeworkData homework) {
    StringBuilder xmlContent = new StringBuilder();
    xmlContent.append("\n\t<").append(HomeworkElementTypes.TITLE.name()).append(">").append(homework.getTitle()).append("</").append(HomeworkElementTypes.TITLE).append(">");
    xmlContent.append("\n\t<").append(HomeworkElementTypes.COURSE.name()).append(">").append(homework.getCourseId()).append("</").append(HomeworkElementTypes.COURSE).append(">");
    xmlContent.append("\n\t<").append(HomeworkElementTypes.SCORE.name()).append(">").append(homework.getScore()).append("</").append(HomeworkElementTypes.SCORE).append(">");
    xmlContent.append("\n\t<").append(HomeworkElementTypes.TEXT.name()).append(">").append(homework.getText()).append("</").append(HomeworkElementTypes.TEXT).append(">");
    return xmlContent.toString();
  }
}

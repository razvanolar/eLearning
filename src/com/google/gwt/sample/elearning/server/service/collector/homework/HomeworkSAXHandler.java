package com.google.gwt.sample.elearning.server.service.collector.homework;

import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Ambrozie Paval on 12/12/2015.
 */
public class HomeworkSAXHandler extends DefaultHandler{
  String content = null;
  private static HomeworkData homeworkData;

  public static HomeworkData getHomeworkData() {
    return homeworkData;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    HomeworkElementTypes startTag = HomeworkElementTypes.valueOf(qName.toUpperCase());
    switch (startTag){
      case HOMEWORK:
        homeworkData = new HomeworkData();
        String id = attributes.getValue("id");
        homeworkData.setId(Long.parseLong(id));
        break;
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    HomeworkElementTypes endTag = HomeworkElementTypes.valueOf(qName.toUpperCase());
    switch(endTag){
      case TITLE:
        homeworkData.setTitle(content);
        break;
      case COURSE:
        homeworkData.setCourseId(Long.parseLong(content));
        break;
      case SCORE:
        homeworkData.setScore(Integer.parseInt(content));
        break;
      case TEXT:
        homeworkData.setText(content);
        break;
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    content = String.copyValueOf(ch, start, length).trim();
  }
}

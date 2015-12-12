package com.google.gwt.sample.elearning.server.service.collector.homework;

import com.google.gwt.sample.elearning.server.service.collector.test.TestSAXHandler;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ambrozie Paval on 12/12/2015.
 */
public class HomeworkXMLHandler {
  SAXParserFactory parserFactory;
  SAXParser parser;
  HomeworkSAXHandler homeworkSAXHandler;
  String fileResource;

  public HomeworkXMLHandler(String fileResource) throws ParserConfigurationException, SAXException {
    this.fileResource = fileResource.trim().replaceFirst("^([\\W]+)<","<");
    parserFactory = SAXParserFactory.newInstance();
    parser = parserFactory.newSAXParser();
    homeworkSAXHandler = new HomeworkSAXHandler();
  }

  public HomeworkData parse() throws IOException, SAXException {
    InputStream systemResourceAsStream = new ByteArrayInputStream(fileResource.getBytes());
    parser.parse(systemResourceAsStream, homeworkSAXHandler);
    return HomeworkSAXHandler.getHomeworkData();
  }
}

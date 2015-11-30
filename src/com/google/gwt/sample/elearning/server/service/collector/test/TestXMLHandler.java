package com.google.gwt.sample.elearning.server.service.collector.test;

import com.google.gwt.sample.elearning.shared.model.LectureTestData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Created by Ambrozie Paval on 11/30/2015.
 */
public class TestXMLHandler {
  SAXParserFactory parserFactory;
  SAXParser parser;
  TestSAXHandler testSAXHandler;
  String fileResource;

  public TestXMLHandler(String fileResource) throws ParserConfigurationException, SAXException {
    this.fileResource = fileResource.trim().replaceFirst("^([\\W]+)<","<");
    parserFactory = SAXParserFactory.newInstance();
    parser = parserFactory.newSAXParser();
    testSAXHandler = new TestSAXHandler();
  }

  public LectureTestData parse() throws IOException, SAXException {
    InputStream systemResourceAsStream = new ByteArrayInputStream(fileResource.getBytes());
    parser.parse(systemResourceAsStream, testSAXHandler);
    return TestSAXHandler.getTestData();
  }
}

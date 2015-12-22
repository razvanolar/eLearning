package com.google.gwt.sample.elearning.server.service.collector.forum;

import com.google.gwt.sample.elearning.shared.model.ForumData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ambrozie Paval on 12/22/2015.
 */
public class ForumXMLHandler {
  SAXParserFactory parserFactory;
  SAXParser parser;
  ForumSAXHandler forumSAXHandler;
  String fileResource;

  public ForumXMLHandler(String fileResource) throws ParserConfigurationException, SAXException {
    this.fileResource = fileResource.trim().replaceFirst("^([\\W]+)<","<");
    parserFactory = SAXParserFactory.newInstance();
    parser = parserFactory.newSAXParser();
    forumSAXHandler = new ForumSAXHandler();
  }

  public ForumData parse() throws IOException, SAXException {
    InputStream systeamResourceAsStream = new ByteArrayInputStream(fileResource.getBytes());
    parser.parse(systeamResourceAsStream, forumSAXHandler);
    return ForumSAXHandler.getForumData();
  }
}

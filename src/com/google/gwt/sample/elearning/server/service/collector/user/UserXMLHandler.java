package com.google.gwt.sample.elearning.server.service.collector.user;

import com.google.gwt.sample.elearning.shared.model.UserData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * Created by Ambrozie Paval on 1/3/2016.
 */
public class UserXMLHandler {
  SAXParserFactory parserFactory;
  SAXParser parser;
  UserSAXHandler userSAXHandler;
  String fileResource;

  public UserXMLHandler(String fileResource) throws ParserConfigurationException, SAXException {
    this.fileResource = fileResource.replaceFirst("^([\\W]+)<","<");
    parserFactory = SAXParserFactory.newInstance();
    parser = parserFactory.newSAXParser();
    userSAXHandler = new UserSAXHandler();
  }

  /**
   *
   * @return the list of users corresponding to the ones sent within the xml file
   * @throws IOException
   * @throws SAXException
   */
  public List<UserData> parse() throws IOException, SAXException {
    InputStream systemResourceAsStream = new ByteArrayInputStream(fileResource.getBytes());
    parser.parse(systemResourceAsStream, userSAXHandler);
    return UserSAXHandler.getUsers();
  }
}

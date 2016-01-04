package com.google.gwt.sample.elearning.server.service.collector.user;

import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Ambrozie Paval on 1/3/2016.
 */
public class UserSAXHandler extends DefaultHandler{
  private String content = null;
  private static UserData userData;
  private static List<UserData> users;

  public static List<UserData> getUsers(){
    return users;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    UserElementTypes startTag = UserElementTypes.valueOf(qName.toUpperCase());
    switch(startTag){
      case USERS:
        users = new ArrayList<>();
        break;
      case USER:
        userData = new UserData();
        String idUser = attributes.getValue("id");
        userData.setId(Long.parseLong(idUser));
        break;
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    UserElementTypes endTag = UserElementTypes.valueOf(qName.toUpperCase());
    switch (endTag){
      case USER:
        users.add(userData);
        break;
      case USERNAME:
        userData.setUsername(content);
        break;
      case PASSWORD:
        userData.setPassword(content);
        break;
      case FIRSTNAME:
        userData.setFirstName(content);
        break;
      case LASTNAME:
        userData.setLastName(content);
        break;
      case EMAIL:
        userData.setEmail(content);
        break;
      case ROLE:
        UserRoleTypes userRole = UserRoleTypes.valueOf(content.toUpperCase());
        userData.setRole(userRole);
        break;
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    content = String.copyValueOf(ch, start, length).trim();
  }
}

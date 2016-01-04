package com.google.gwt.sample.elearning.server.service.collector.user;

import com.google.gwt.sample.elearning.shared.model.UserData;

import java.util.List;

/**
 *
 * Created by Ambrozie Paval on 1/3/2016.
 */
public class UserXMLConverter {
  public static final String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

  public String convertToXML(List<UserData> users){
    String rez = xmlHeader;
    rez += "\n<" + UserElementTypes.USERS.name() + ">";
    rez += convertUsersToXML(users);
    rez += "\n</" + UserElementTypes.USERS.name() + ">";
    return rez;
  }

  private String convertUsersToXML(List<UserData> users) {
    String rez = "";
    for(UserData user : users){
      rez += convertUserDataToXML(user);
    }
    return rez;
  }

  private String convertUserDataToXML(UserData user) {
    String userXml = "\n\t<" + UserElementTypes.USER.name() + " id=\"" + user.getId() + "\">";
    userXml += "\n\t\t<" + UserElementTypes.USERNAME.name() + ">" + user.getUsername() + "</" + UserElementTypes.USERNAME.name() + ">";
    userXml += "\n\t\t<" + UserElementTypes.PASSWORD.name() + ">" + user.getPassword() + "</" + UserElementTypes.PASSWORD.name() + ">";
    userXml += "\n\t\t<" + UserElementTypes.FIRSTNAME.name() + ">" + user.getFirstName() + "</" + UserElementTypes.FIRSTNAME.name() + ">";
    userXml += "\n\t\t<" + UserElementTypes.LASTNAME.name() + ">" + user.getLastName() + "</" + UserElementTypes.LASTNAME.name() + ">";
    userXml += "\n\t\t<" + UserElementTypes.EMAIL.name() + ">" + user.getEmail() + "</" + UserElementTypes.EMAIL.name() + ">";
    userXml += "\n\t\t<" + UserElementTypes.ROLE.name() + ">" + user.getRole().name() + "</" + UserElementTypes.ROLE.name() + ">";
    userXml += "\n\t</" + UserElementTypes.USER.name() + ">";
    return userXml;
  }

}

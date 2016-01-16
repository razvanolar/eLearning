package com.google.gwt.sample.elearning.server.service.collector.forum;

import com.google.gwt.sample.elearning.server.service.collector.homework.HomeworkElementTypes;
import com.google.gwt.sample.elearning.shared.model.ForumCommentData;
import com.google.gwt.sample.elearning.shared.model.ForumData;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;

/**
 * Created by Ambrozie Paval on 12/22/2015.
 */
public class ForumXMLConverter {
  public static final String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

  public String convertToXML(ForumData forumData) {
    String rez = xmlHeader;
    rez += "\n<" + ForumElementTypes.FORUM.name() + " id=\"" + forumData.getId() + " courseId=\"" + forumData.getCourseId() + "\"" +  "\">";
    rez += convertForumDataToXML(forumData);
    rez += "\n</" + ForumElementTypes.FORUM.name() + ">";
    return rez;
  }

  private String convertForumDataToXML(ForumData forumData) {
    return "\n\t<" + ForumElementTypes.TOPIC.name() + ">" + forumData.getTopic() + "</" + ForumElementTypes.TOPIC.name() + ">" +
        convertCommentsToXML(forumData);
  }

  private String convertCommentsToXML(ForumData forumData) {
    StringBuilder forumComments = new StringBuilder();
    forumComments.append("\n\t<").append(ForumElementTypes.COMMENTS.name()).append(">");
    for(ForumCommentData commentData : forumData.getCommentList()) {
      forumComments.append(convertCommentDataToXML(commentData));
    }
    forumComments.append("\n\t</").append(ForumElementTypes.COMMENTS.name()).append(">");
    return forumComments.toString();
  }

  private String convertCommentDataToXML(ForumCommentData commentData) {
    return "\n\t\t<" + ForumElementTypes.COMMENT.name() + " id=\"" + commentData.getId() + "\"" + " userId=\"" + commentData.getUserId() + "\">" +
        "\n\t\t\t" + commentData.getCommentText() +
        "\n\t\t</" + ForumElementTypes.COMMENT.name() + ">";
  }
}

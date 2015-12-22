package com.google.gwt.sample.elearning.server.service.collector.forum;

import com.google.gwt.sample.elearning.shared.model.ForumCommentData;
import com.google.gwt.sample.elearning.shared.model.ForumData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ambrozie Paval on 12/22/2015.
 */
public class ForumSAXHandler extends DefaultHandler {
  private String content = null;
  private static ForumData forumData;
  private List<ForumCommentData> forumComments;
  private ForumCommentData commentData;

  public static ForumData getForumData(){
    return forumData;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    ForumElementTypes startTag = ForumElementTypes.valueOf(qName.toUpperCase());
    switch (startTag) {
      case FORUM:
        forumData = new ForumData();
        String idForum = attributes.getValue("id");
        forumData.setId(Long.parseLong(idForum));
        String courseId = attributes.getValue("courseId");
        forumData.setCourseId(Long.parseLong(courseId));
        break;
      case COMMENTS:
        forumComments = new ArrayList<>();
        break;
      case COMMENT:
        commentData = new ForumCommentData();
        String idComment = attributes.getValue("id");
        commentData.setId(Long.parseLong(idComment));
        String userId = attributes.getValue("userId");
        commentData.setUserId(Long.parseLong(userId));
        break;
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    ForumElementTypes endTag = ForumElementTypes.valueOf(qName.toUpperCase());
    switch (endTag) {
      case FORUM:
        forumData.setCommentList(forumComments);
        break;
      case TOPIC:
        forumData.setTopic(content);
        break;
      case COMMENT:
        commentData.setCommentText(content);
        forumComments.add(commentData);
        break;
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    content = String.copyValueOf(ch, start, length).trim();
  }
}

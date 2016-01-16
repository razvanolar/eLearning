package com.google.gwt.sample.elearning.server.service;

import com.google.gwt.sample.elearning.client.service.ForumService;
import com.google.gwt.sample.elearning.server.ServerUtil;
import com.google.gwt.sample.elearning.server.service.collector.forum.ForumXMLConverter;
import com.google.gwt.sample.elearning.server.service.collector.forum.ForumXMLHandler;
import com.google.gwt.sample.elearning.shared.exception.ELearningException;
import com.google.gwt.sample.elearning.shared.model.ForumCommentData;
import com.google.gwt.sample.elearning.shared.model.ForumData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ambrozie Paval on 1/16/2016.
 */
public class ForumServiceImpl extends RemoteServiceServlet implements ForumService {

  private void persistForumToFile(ForumData forumData) throws Exception {
    String filePath = ServerUtil.getLectureForumsPath(forumData.getCourseId());
    File dirs = new File(filePath);
    if (!dirs.exists() && !dirs.mkdirs())
      throw new Exception("Unable to create missing directories");

    ForumXMLConverter forumXMLConverter = new ForumXMLConverter();
    String forumXML = forumXMLConverter.convertToXML(forumData);
    byte[] forum = forumXML.getBytes();

    FileOutputStream fileOutputStream = new FileOutputStream(filePath + "\\forum.xml");
    fileOutputStream.write(forum);
    fileOutputStream.close();
  }

  public List<ForumData> getUserAvailableForums(long userId) {
    List<ForumData> forums = new ArrayList<>();
    LectureServiceImpl lectureService = new LectureServiceImpl();
    List<Lecture> lectures = lectureService.getAllLecturesByStudent(userId);

    for (Lecture lecture : lectures) {
      forums.add(getForumByLectureId(lecture.getId()));
    }
    return forums;
  }

  private ForumData getForumByLectureId(long lectureId) {
    try {
      String forumPath = ServerUtil.getLectureForumsPath(lectureId) + "\\forum.xml";
      String forumStringXml;

      File f = new File(forumPath);
      if(f.exists() && !f.isDirectory()) {
        FileInputStream inputStream = new FileInputStream(forumPath);
        forumStringXml = IOUtils.toString(inputStream);
      } else {
        forumStringXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<FORUM id=\"" + lectureId + "\" courseId=\"" + lectureId + "\">\n" +
            "</FORUM>";
        byte[] forum = forumStringXml.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(forumPath);
        fileOutputStream.write(forum);
        fileOutputStream.close();
      }

      ForumXMLHandler forumXMLHandler = new ForumXMLHandler(forumStringXml);
      return forumXMLHandler.parse();
    } catch (Exception e) {
      throw new ELearningException(e);
    }
  }

  public void addCommentToForum(long userId, long lectureId, String comment) throws Exception {
    ForumData forumData = getForumByLectureId(lectureId);
    ForumCommentData forumCommentData = new ForumCommentData(userId, userId, comment);
    forumData.getCommentList().add(forumCommentData);
    persistForumToFile(forumData);
  }
}

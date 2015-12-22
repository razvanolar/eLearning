package com.google.gwt.sample.elearning.shared.model;

import java.util.List;

/**
 * Created by Ambrozie Paval on 12/22/2015.
 */
public class ForumData {
  private long id;
  private long courseId;
  private String topic;
  List<ForumCommentData> commentList;

  public ForumData() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }

  public List<ForumCommentData> getCommentList() {
    return commentList;
  }

  public void setCommentList(List<ForumCommentData> commentList) {
    this.commentList = commentList;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }
}

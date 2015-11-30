package com.google.gwt.sample.elearning.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/***
 * Created by razvanolar on 30.11.2015.
 */
public class VideoLinkData implements IsSerializable {

  private long id;
  private String title;
  private String url;
  private String description;
  private long lectureId;

  public VideoLinkData() {}

  public VideoLinkData(long id, String title, String url, String description, long lectureId) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.description = description;
    this.lectureId = lectureId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getLectureId() {
    return lectureId;
  }

  public void setLectureId(long lectureId) {
    this.lectureId = lectureId;
  }
}

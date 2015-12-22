package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;

import java.util.List;

/**
 *
 * Created by razvanolar on 01.12.2015.
 */
public interface VideoLinkDAO {
  List<VideoLinkData> getLectureVideos(long lectureId) throws RepositoryException;
  void saveVideoLinkData(long lectureId, VideoLinkData videoLinkData) throws RepositoryException;
}

package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 01.12.2015.
 */
public class JdbcVideoLinkDAO implements VideoLinkDAO {

  /**
   * Create a video_links table with the fallowing columns:
   *  id - P_K
   *  title
   *  url
   *  description
   *  lecture_id - F_K
   *
   * Also create the CRUD methods
   */

  @Override
  public List<VideoLinkData> getLectureVideos(long lectureId) throws RepositoryException {
    // TODO : implement method (see the description above)
    List<VideoLinkData> result = new ArrayList<VideoLinkData>();
    result.add(new VideoLinkData(1, "video link 1", "https://www.youtube.com/embed/bBIywvBizsY", "desc1", 0));
    result.add(new VideoLinkData(2, "video link 2", "https://www.youtube.com/embed/7uiv6tKtoKg", "desc2", 0));
    result.add(new VideoLinkData(3, "video link 3", "https://www.youtube.com/embed/-yrZpTHBEss", "desc3", 0));
    return result;
  }

  @Override
  public void saveVideoLinkData(long lectureId, VideoLinkData videoLinkData) throws RepositoryException {
    // TODO : implement method (see the description above)
  }

  @Override
  public void updateVideoLinkData(long lectureId, VideoLinkData videoLinkData) throws RepositoryException {
    // TODO : implement method (see the description above)
  }

  @Override
  public void deleteVideoLinkData(long lectureId, VideoLinkData videoLinkData) throws RepositoryException {
    // TODO : implement method (see the description above)
  }
}

package com.google.gwt.sample.elearning.server.repository.JDBC;

import com.google.gwt.sample.elearning.server.JDBC.JDBCUtil;
import com.google.gwt.sample.elearning.server.repository.RepositoryException;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.Professor;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    List<VideoLinkData> result = new ArrayList<VideoLinkData>();
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("select * from video_link where ref_curs = ?");
      pstmt.setLong(1, lectureId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        VideoLinkData videoLinkData = new VideoLinkData(rs.getLong(0),rs.getString(1),rs.getString(2),rs.getString(3), rs.getLong(4));
        result.add(videoLinkData);
      }

    } catch (SQLException e) {
      throw new RepositoryException("Eroare la comunicarea cu BD", e);

    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
    return result;
  }

  @Override
  public void saveVideoLinkData(long lectureId, VideoLinkData videoLinkData) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
              .prepareStatement("insert into video_link(titlu,url,descriere, ref_curs) values(?,?,?,?)");
      pstmt.setString(1, videoLinkData.getTitle());
      pstmt.setString(2, videoLinkData.getUrl());
      pstmt.setString(3, videoLinkData.getDescription());
      pstmt.setLong(4, videoLinkData.getLectureId());

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void updateVideoLinkData(long lectureId, VideoLinkData videoLinkData) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con
              .prepareStatement("update video_link set titlu=?, url=?, descriere=?, ref_curs=? where id = ?");
      pstmt.setString(1, videoLinkData.getTitle());
      pstmt.setString(2, videoLinkData.getUrl());
      pstmt.setString(3, videoLinkData.getDescription());
      pstmt.setLong(4, videoLinkData.getLectureId());
      pstmt.setLong(5, videoLinkData.getId());

      pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }

  @Override
  public void deleteVideoLinkData(long lectureId, VideoLinkData videoLinkData) throws RepositoryException {
    Connection con = null;
    try {
      con = JDBCUtil.getNewConnection();
      PreparedStatement pstmt = con.prepareStatement("delete from video_link where id = ?");
      pstmt.setLong(1, videoLinkData.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage(), e);
    } finally {
      if (con != null)
        JDBCUtil.closeConnection(con);
    }
  }
}

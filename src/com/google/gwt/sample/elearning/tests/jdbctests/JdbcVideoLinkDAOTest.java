package com.google.gwt.sample.elearning.tests.jdbctests;

import com.google.gwt.sample.elearning.server.repository.JDBC.*;
import com.google.gwt.sample.elearning.shared.model.VideoLinkData;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Valy on 1/2/2016.
 */
public class JdbcVideoLinkDAOTest {

  LectureDAO lectureDAO;
  VideoLinkDAO videoLinkDAO;

  @Before
  public void getDAOs(){

    lectureDAO = new JdbcLectureDAO();
    videoLinkDAO = new JdbcVideoLinkDAO();
  }

  @Test
  public void getLectureVideosTest(){
    assert videoLinkDAO.getLectureVideos(1).size()>0;
  }

  @Test
  public void saveVideoLinkDataTest(){
    VideoLinkData videoLinkData =  new VideoLinkData("titlu1","www.google.ro","desc",1);
    videoLinkDAO.saveVideoLinkData(videoLinkData.getLectureId(),videoLinkData);

  }

  @Test
  public void updateVideoLinkDataTest(){
    VideoLinkData videoLinkData = new VideoLinkData(1,"titlu2","www.google.ro","desc2",1);
    videoLinkDAO.updateVideoLinkData(1,videoLinkData);
  }


  @Test
  public void deleteVideoLinkDataTest(){
    VideoLinkData videoLinkData = new VideoLinkData(1,"titlu2","www.google.ro","desc2",1);
      videoLinkDAO.deleteVideoLinkData(1,videoLinkData);
  }

}

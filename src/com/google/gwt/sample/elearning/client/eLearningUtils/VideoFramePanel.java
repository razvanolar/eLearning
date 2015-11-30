package com.google.gwt.sample.elearning.client.eLearningUtils;

import com.google.gwt.sample.elearning.shared.model.VideoLinkData;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/***
 * Created by razvanolar on 01.12.2015.
 */
public class VideoFramePanel implements IsWidget {

  private VideoLinkData videoLinkData;
  private BorderLayoutContainer mainContainer;

  public VideoFramePanel(VideoLinkData videoLinkData) {
    this.videoLinkData = videoLinkData;
    initGUI();
  }

  private void initGUI() {
    Frame frame = new Frame(videoLinkData.getUrl());
    mainContainer = new BorderLayoutContainer();

    mainContainer.setCenterWidget(frame);
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

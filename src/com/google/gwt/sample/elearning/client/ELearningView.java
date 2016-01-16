package com.google.gwt.sample.elearning.client;

import com.google.gwt.sample.elearning.client.lecture_views.center_panel.LectureContentView;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.LectureDetailsView;
import com.google.gwt.sample.elearning.client.lecture_views.right_panel.LectureInfoView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;

/**
 *
 * Created by razvanolar on 30.12.2015.
 */
public class ELearningView {

  private BorderLayoutContainer mainContainer;
  private LectureDetailsView lectureDetailsView;
  private LectureInfoView lectureInfoView;
  private LectureContentView lectureContentView;

  public ELearningView() {
    initGUI();
  }

  private void initGUI() {
    lectureDetailsView = new LectureDetailsView();
    lectureInfoView = new LectureInfoView();
    lectureContentView = new LectureContentView();
    mainContainer = new BorderLayoutContainer();
    ContentPanel leftPanel = new ContentPanel();

    leftPanel.setHeaderVisible(false);
    leftPanel.setAnimCollapse(true);
    leftPanel.add(lectureDetailsView.asWidget());

    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(250);
    layoutData.setSplit(true);
    layoutData.setCollapsible(true);
    layoutData.setCollapseMini(true);
    layoutData.setMinSize(150);
    layoutData.setMaxSize(450);
    layoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setWestWidget(leftPanel, layoutData);
    mainContainer.setCenterWidget(lectureContentView.asWidget(), new MarginData(0));

    ContentPanel rightPanel = new ContentPanel();
    layoutData = new BorderLayoutContainer.BorderLayoutData(250);
    layoutData.setSplit(true);
    layoutData.setCollapsible(true);
    layoutData.setCollapseMini(true);
    layoutData.setMinSize(150);
    layoutData.setMaxSize(450);
    layoutData.setMargins(new Margins(0, 0, 0, 5));
    rightPanel.setHeaderVisible(false);
    rightPanel.setAnimCollapse(true);
    rightPanel.add(lectureInfoView.asWidget());

    mainContainer.setEastWidget(rightPanel, layoutData);
    mainContainer.setStyleName("mainELearningContainer");
  }

  public LectureDetailsView getLectureDetailsView() {
    return lectureDetailsView;
  }

  public LectureInfoView getLectureInfoView(){
    return lectureInfoView;
  }

  public LectureContentView getLectureContentView() {
    return lectureContentView;
  }

  public Widget asWidget() {
    return mainContainer;
  }
}

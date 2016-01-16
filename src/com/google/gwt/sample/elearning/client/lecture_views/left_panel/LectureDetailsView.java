package com.google.gwt.sample.elearning.client.lecture_views.left_panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.filesView.LectureFilesView;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.tests_view.LectureTestsView;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.videos_view.LectureVideosView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 *
 * Created by razvanolar on 30.12.2015.
 */
public class LectureDetailsView {

  private BorderLayoutContainer mainContainer;
  private LectureFilesView lectureFilesView;
  private LectureVideosView lectureVideosView;
  private LectureTestsView lectureTestsView;

  public LectureDetailsView() {
    initGUI();
  }

  private void initGUI() {
    AccordionLayoutContainer.AccordionLayoutAppearance appearance =
            GWT.<AccordionLayoutContainer.AccordionLayoutAppearance> create(
                    AccordionLayoutContainer.AccordionLayoutAppearance.class);

    lectureFilesView = new LectureFilesView();
    lectureVideosView = new LectureVideosView();
    lectureTestsView = new LectureTestsView();
    mainContainer = new BorderLayoutContainer();
    AccordionLayoutContainer accordionLayoutContainer = new AccordionLayoutContainer();
    ContentPanel lectureFilesPanel = new ContentPanel(appearance);
    ContentPanel lectureVideosPanel = new ContentPanel(appearance);
    ContentPanel lectureTestsPanel = new ContentPanel(appearance);

    lectureFilesPanel.setHeadingText("Files");
    lectureFilesPanel.add(lectureFilesView.asWidget());
    lectureVideosPanel.setHeadingText("Videos");
    lectureVideosPanel.add(lectureVideosView.asWidget());
    lectureTestsPanel.setHeadingText("Tests");
    lectureTestsPanel.add(lectureTestsView.asWidget());

    accordionLayoutContainer.add(lectureFilesPanel);
    accordionLayoutContainer.add(lectureVideosPanel);
    accordionLayoutContainer.add(lectureTestsPanel);
    accordionLayoutContainer.setExpandMode(AccordionLayoutContainer.ExpandMode.SINGLE_FILL);
    lectureFilesPanel.setExpanded(true);

    mainContainer.setCenterWidget(accordionLayoutContainer);
  }

  public LectureFilesView getLectureFilesView() {
    return lectureFilesView;
  }

  public LectureVideosView getLectureVideosView() {
    return lectureVideosView;
  }

  public LectureTestsView getLectureTestsView() {
    return lectureTestsView;
  }

  public Widget asWidget() {
    return mainContainer;
  }
}

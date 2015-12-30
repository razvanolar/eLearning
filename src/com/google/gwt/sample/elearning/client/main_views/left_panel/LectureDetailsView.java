package com.google.gwt.sample.elearning.client.main_views.left_panel;

import com.google.gwt.core.client.GWT;
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

  public LectureDetailsView() {
    initGUI();
  }

  private void initGUI() {
    AccordionLayoutContainer.AccordionLayoutAppearance appearance =
            GWT.<AccordionLayoutContainer.AccordionLayoutAppearance> create(
                    AccordionLayoutContainer.AccordionLayoutAppearance.class);

    lectureFilesView = new LectureFilesView();
    lectureVideosView = new LectureVideosView();
    mainContainer = new BorderLayoutContainer();
    AccordionLayoutContainer accordionLayoutContainer = new AccordionLayoutContainer();
    ContentPanel lectureFilesPanel = new ContentPanel(appearance);
    ContentPanel lectureVideosPanel = new ContentPanel(appearance);

    lectureFilesPanel.setHeadingText("Files");
    lectureFilesPanel.add(lectureFilesView.asWidget());
    lectureVideosPanel.setHeadingText("Videos");
    lectureVideosPanel.add(lectureVideosView.asWidget());

    accordionLayoutContainer.add(lectureFilesPanel);
    accordionLayoutContainer.add(lectureVideosPanel);
    accordionLayoutContainer.setExpandMode(AccordionLayoutContainer.ExpandMode.MULTI);

    mainContainer.setCenterWidget(accordionLayoutContainer);
  }

  public Widget asWidget() {
    return mainContainer;
  }
}

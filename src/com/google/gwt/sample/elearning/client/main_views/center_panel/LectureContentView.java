package com.google.gwt.sample.elearning.client.main_views.center_panel;

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 *
 * Created by razvanolar on 03.01.2016.
 */
public class LectureContentView {

  private BorderLayoutContainer mainContainer;
  private TabPanel tabPanel;

  public LectureContentView() {
    initGUI();
  }

  private void initGUI() {
    tabPanel = new TabPanel();
    mainContainer = new BorderLayoutContainer();

    TabItemConfig tab1 = new TabItemConfig("title");
//    tabPanel.add(new Frame("C:\\elearning\\lectures\\1\\files\\test\\dasdssad_dasd\\JavaFX_book.pdf"), tab1);
    Frame frame = new Frame("JavaFX_book.pdf");
    BorderLayoutContainer container = new BorderLayoutContainer();
    container.setCenterWidget(frame);
    tabPanel.add(container, tab1);
    tabPanel.setCloseContextMenu(true);
    mainContainer.setCenterWidget(tabPanel);
  }

  public Widget asWidget() {
    return mainContainer;
  }
}

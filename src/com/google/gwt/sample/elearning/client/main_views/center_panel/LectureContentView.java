package com.google.gwt.sample.elearning.client.main_views.center_panel;

import com.google.gwt.user.client.ui.Frame;
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

    tabPanel.setCloseContextMenu(true);
    mainContainer.setCenterWidget(tabPanel);
  }

  public void addPanel(String path, String title) {
    Widget widget = getActiveWidget(title);
    if (widget != null) {
      tabPanel.setActiveWidget(widget);
    } else {
      TabItemConfig tab1 = new TabItemConfig(title);
      tab1.setClosable(true);
      Frame frame = new Frame(path);
      BorderLayoutContainer container = new BorderLayoutContainer();
      container.setCenterWidget(frame);
      tabPanel.add(container, tab1);
      tabPanel.setActiveWidget(container);
      tabPanel.forceLayout();
      mainContainer.forceLayout();
    }
  }

  public void clear() {
    tabPanel = new TabPanel();
    tabPanel.setCloseContextMenu(true);
    tabPanel.setCloseContextMenu(true);
    mainContainer.setCenterWidget(tabPanel);
    mainContainer.forceLayout();
  }

  private Widget getActiveWidget(String title) {
    for (int i=0; i<tabPanel.getWidgetCount(); i++) {
      Widget widget = tabPanel.getWidget(i);
      TabItemConfig tab = tabPanel.getConfig(widget);
      if (tab.getText().equals(title))
        return widget;
    }
    return null;
  }

  public Widget asWidget() {
    return mainContainer;
  }
}

package com.google.gwt.sample.elearning.client;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class MasterWindow extends Window {
  private static final int DEFAULT_WIDTH = 1000;
  private static final int DEFAULT_HEIGHT = 500;
  private BorderLayoutContainer mainContainer;

  public MasterWindow() {
    this.setPixelSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    this.setModal(true);
    mainContainer = new BorderLayoutContainer();
    this.add(mainContainer);
  }

  public void setContent(Widget content, String title) {
    mainContainer.setCenterWidget(content);
    this.setHeadingHtml(title);
  }
}

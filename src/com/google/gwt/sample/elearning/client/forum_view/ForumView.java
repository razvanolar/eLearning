package com.google.gwt.sample.elearning.client.forum_view;

import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ForumView implements ForumController.IForumView, MaskableView {

  private BorderLayoutContainer mainContainer;

  public ForumView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();

    mainContainer.setStyleName("whiteBackground");
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public void mask() {
    mainContainer.mask();
  }

  @Override
  public void unmask() {
    mainContainer.unmask();
  }
}

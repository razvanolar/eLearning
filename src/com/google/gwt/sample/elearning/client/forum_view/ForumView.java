package com.google.gwt.sample.elearning.client.forum_view;

import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ForumView implements ForumController.IForumView, MaskableView {

  private BorderLayoutContainer mainContainer;

  public TextField getTextField() {
    return text;
  }

  TextField text;

  public ForumView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    text = new TextField();
    mainContainer.setStyleName("whiteBackground");
    mainContainer.add(text);
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

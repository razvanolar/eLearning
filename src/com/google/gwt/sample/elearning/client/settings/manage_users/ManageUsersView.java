package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ManageUsersView implements ManageUsersController.IManageUsersView {

  private BorderLayoutContainer mainContainer;
  private TextButton createButton

  public ManageUsersView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    ToolBar toolBar = createToolbar();
  }

  private ToolBar createToolbar() {
    ToolBar toolBar = new ToolBar();


    return toolBar;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

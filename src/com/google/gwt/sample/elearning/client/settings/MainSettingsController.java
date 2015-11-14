package com.google.gwt.sample.elearning.client.settings;

import com.google.gwt.sample.elearning.client.settings.manage_users.ManageUsersController;
import com.google.gwt.sample.elearning.client.settings.manage_users.ManageUsersView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class MainSettingsController {

  public interface IMainSettingsView {

    void addTab(Widget tabContent, String title);
    Widget asWidget();
  }

  private final IMainSettingsView view;

  public MainSettingsController(IMainSettingsView view) {
    this.view = view;
  }

  public void bind() {
    setContent();
    addListeners();
  }

  private void setContent() {
    view.addTab(new BorderLayoutContainer(), "Tab 1");
    view.addTab(new BorderLayoutContainer(), "Tab 2");

    /* Add manage users */
    ManageUsersController.IManageUsersView manageUsersView = new ManageUsersView();
    ManageUsersController manageUsersController = new ManageUsersController(manageUsersView);
    manageUsersController.bind();
    view.addTab(manageUsersView.asWidget(), "Manage Users");
  }

  private void addListeners() {

  }
}

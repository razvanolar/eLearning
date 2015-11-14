package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.user.client.ui.Widget;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ManageUsersController {

  public interface IManageUsersView {

    Widget asWidget();
  }

  private IManageUsersView view;

  public ManageUsersController(IManageUsersView view) {
    this.view = view;
  }

  public void bind() {
    addListeneres();
  }

  private void addListeneres() {

  }
}

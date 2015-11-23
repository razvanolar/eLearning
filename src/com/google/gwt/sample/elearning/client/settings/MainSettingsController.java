package com.google.gwt.sample.elearning.client.settings;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.ManageLecturesController;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.ManageLecturesView;
import com.google.gwt.sample.elearning.client.settings.manage_users.ManageUsersController;
import com.google.gwt.sample.elearning.client.settings.manage_users.ManageUsersView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

import java.util.logging.Logger;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class MainSettingsController {

  public interface IMainSettingsView {
    TabPanel getTabPanel();
    void addTab(Widget tabContent, String title);
    Widget asWidget();
  }

  private IMainSettingsView view;

  private static Logger log = Logger.getLogger(MainSettingsController.class.getName());

  public MainSettingsController(IMainSettingsView view) {
    this.view = view;
  }

  public void bind() {
    setContent();
    addListeners();
  }

  private void setContent() {
    view.addTab(new BorderLayoutContainer(), "Tab 1");

    /* Add manage lectures */
    ManageLecturesController.IManageLecturesView manageLecturesView = new ManageLecturesView();
    ManageLecturesController manageLecturesController = new ManageLecturesController(manageLecturesView);
    manageLecturesController.bind();
    view.addTab(manageLecturesView.asWidget(), "Manage Lectures");
    manageLecturesView.asWidget().setLayoutData(manageLecturesController);


    /* Add manage users */
    ManageUsersController.IManageUsersView manageUsersView = new ManageUsersView();
    ManageUsersController manageUsersController = new ManageUsersController(manageUsersView);
    manageUsersController.bind();

    manageUsersView.asWidget().setLayoutData(manageUsersController);
    view.addTab(manageUsersView.asWidget(), "Manage Users");
  }

  private void addListeners() {
    view.getTabPanel().addSelectionHandler(new SelectionHandler<Widget>() {
      @Override
      public void onSelection(SelectionEvent<Widget> event) {
        Widget selectedWidget = event.getSelectedItem();
        ISettingsController controller = (ISettingsController) selectedWidget.getLayoutData();
        controller.loadResources();
      }
    });
  }
}

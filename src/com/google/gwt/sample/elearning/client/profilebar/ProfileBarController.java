package com.google.gwt.sample.elearning.client.profilebar;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.login.LoginController;
import com.google.gwt.sample.elearning.client.logs.LogsController;
import com.google.gwt.sample.elearning.client.logs.LogsView;
import com.google.gwt.sample.elearning.client.settings.MainSettingsController;
import com.google.gwt.sample.elearning.client.settings.MainSettingsView;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ProfileBarController {

  public interface IProfileBarView {
    TextButton getSettingsButton();
    MenuItem getViewLogsMenuItem();
    MenuItem getLogoutMenuItem();
    Widget asWidget();
  }

  private IProfileBarView view;

  public ProfileBarController(IProfileBarView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {
    if (view.getSettingsButton() != null)
      view.getSettingsButton().addSelectHandler(new SelectEvent.SelectHandler() {
        public void onSelect(SelectEvent event) {
          doOnSettingsSelect();
        }
      });
    view.getViewLogsMenuItem().addSelectionHandler(new SelectionHandler<Item>() {
      @Override
      public void onSelection(SelectionEvent<Item> event) {
        doOnViewLogsSelect();
      }
    });
    view.getLogoutMenuItem().addSelectionHandler(new SelectionHandler<Item>() {
      @Override
      public void onSelection(SelectionEvent<Item> event) {
        doOnLogoutSelect();
      }
    });
  }

  private void doOnSettingsSelect() {
    MainSettingsController.IMainSettingsView settingsView = new MainSettingsView();

    MainSettingsController settingsController = new MainSettingsController(settingsView);
    settingsController.bind();

    MasterWindow window = new MasterWindow();
    window.setContent(settingsView.asWidget(), "Admin manager");
    window.show();
  }

  private void doOnViewLogsSelect() {
    LogsController.ILogsView logsView = new LogsView();
    LogsController logsController = new LogsController(logsView);
    MasterWindow window = new MasterWindow();
    window.setContent(logsView.asWidget(), "View Logs");
    window.show();
  }

  private void doOnLogoutSelect() {
    LoginController.logout();
  }
}

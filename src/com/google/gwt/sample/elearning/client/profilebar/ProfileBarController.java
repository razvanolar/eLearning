package com.google.gwt.sample.elearning.client.profilebar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.login.LoginController;
import com.google.gwt.sample.elearning.client.logs.LogsController;
import com.google.gwt.sample.elearning.client.logs.LogsView;
import com.google.gwt.sample.elearning.client.main_views.UserLecturesController;
import com.google.gwt.sample.elearning.client.main_views.UserLecturesView;
import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.client.settings.MainSettingsController;
import com.google.gwt.sample.elearning.client.settings.MainSettingsView;
import com.google.gwt.sample.elearning.shared.model.FilteredLecturesData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Popup;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ProfileBarController {

  public interface IProfileBarView {
    TextButton getSettingsButton();
    TextButton getLecturesButton();
    MenuItem getViewLogsMenuItem();
    MenuItem getLogoutMenuItem();
    Widget asWidget();
  }

  private IProfileBarView view;
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);

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
      public void onSelection(SelectionEvent<Item> event) {
        doOnViewLogsSelect();
      }
    });

    view.getLogoutMenuItem().addSelectionHandler(new SelectionHandler<Item>() {
      public void onSelection(SelectionEvent<Item> event) {
        doOnLogoutSelect();
      }
    });

    view.getLecturesButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        doOnLecturesSelect();
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

  private void doOnLecturesSelect() {
    lectureService.getLecturesEnrollementsListByUser(ELearningController.getInstance().getCurrentUser().getId(),
            new AsyncCallback<FilteredLecturesData>() {
              public void onFailure(Throwable caught) {
                (new MessageBox("Info", "Error")).show();
              }

              public void onSuccess(FilteredLecturesData result) {
                Popup popup = new Popup();

                UserLecturesController.IUserLecturesView userLecturesView = new UserLecturesView();
                UserLecturesController controller = new UserLecturesController(userLecturesView, lectureService, result);
                controller.bind();

                popup.add(userLecturesView.asWidget());
                popup.setWidth(250);
                popup.setHeight(400);
                popup.show(view.getLecturesButton());
              }
            });
  }

  private void doOnLogoutSelect() {
    LoginController.logout();
  }
}

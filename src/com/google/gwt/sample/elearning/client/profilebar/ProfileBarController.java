package com.google.gwt.sample.elearning.client.profilebar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.login.LoginController;
import com.google.gwt.sample.elearning.client.logs.LogsController;
import com.google.gwt.sample.elearning.client.logs.LogsView;
import com.google.gwt.sample.elearning.client.lecture_views.UserLecturesController;
import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.client.settings.MainSettingsController;
import com.google.gwt.sample.elearning.client.settings.MainSettingsView;
import com.google.gwt.sample.elearning.client.user_profile.change_password.ChangePasswordController;
import com.google.gwt.sample.elearning.client.user_profile.change_password.ChangePasswordView;
import com.google.gwt.sample.elearning.shared.model.FilteredLecturesData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import java.util.List;
import java.util.logging.Logger;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ProfileBarController {

  public interface IProfileBarView {
    TextButton getSettingsButton();
    TextButton getForumButton();
    TextButton getLecturesButton();
    MenuItem getViewLogsMenuItem();
    MenuItem getLogoutMenuItem();
    MenuItem getUnenrolledMenuItem();
    MenuItem getChangePasswordMenuItem();
    MenuItem getUserLecturesItem();
    Widget asWidget();
  }

  private Logger log = Logger.getLogger(ProfileBarController.class.getName());
  private IProfileBarView view;
  private UserLecturesController userLecturesController;
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);

  public ProfileBarController(IProfileBarView view, UserLecturesController userLecturesController) {
    this.view = view;
    this.userLecturesController = userLecturesController;
  }

  public void bind() {
    addListeners();
  }

  public void hideLectureMenu() {
    view.getLecturesButton().hideMenu();
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
        doOnLecturesSelection();
      }
    });

    view.getForumButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        ELearningController.getInstance().loadForumDetails();
      }
    });

    view.getChangePasswordMenuItem().addSelectionHandler(new SelectionHandler<Item>() {
      @Override
      public void onSelection(SelectionEvent<Item> event) {
        doOnChangePasswordSelection();
      }
    });
  }

  private void doOnLecturesSelection() {
    lectureService.getLecturesEnrollementsListByUser(ELearningController.getInstance().getCurrentUser().getId(),
        new AsyncCallback<FilteredLecturesData>() {
          public void onFailure(Throwable caught) {

          }

          public void onSuccess(FilteredLecturesData result) {
            createUserLecturesSubMenu(result.getEnrolledLectures());
            userLecturesController.setLecturesList(result.getUnenrolledLectures());
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

  private void doOnChangePasswordSelection() {
    ChangePasswordController.IChangePasswordView changePasswordView = new ChangePasswordView();
    ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordView);
    changePasswordController.bind();

    MasterWindow window = new MasterWindow();
    window.setContent(changePasswordView.asWidget(), "Change Password");
    window.setModal(true);
    window.setPixelSize(280, 150);
    window.setResizable(false);
    window.show();
    changePasswordController.setContentWindow(window);

  }

  private void doOnViewLogsSelect() {
    LogsController.ILogsView logsView = new LogsView();
    LogsController logsController = new LogsController(logsView);
    MasterWindow window = new MasterWindow();
    window.setContent(logsView.asWidget(), "View Logs");
    window.setModal(false);
    window.show();
  }

  private void createUserLecturesSubMenu(List<Lecture> lectures) {
    if (lectures == null || lectures.isEmpty()) {
      view.getUserLecturesItem().setSubMenu(null);
      return;
    }
    Menu menu = new Menu();
    for (Lecture lecture : lectures) {
      MenuItem menuItem = new MenuItem(lecture.getLectureName());
      menuItem.setLayoutData(lecture);
      menu.add(menuItem);
      menuItem.addSelectionHandler(new SelectionHandler<Item>() {
        public void onSelection(SelectionEvent<Item> event) {
          doOnLecutureItemSelection(event);
        }
      });
    }
    view.getUserLecturesItem().setSubMenu(menu);
  }

  private void doOnLecutureItemSelection(SelectionEvent<Item> event) {
    Item item = event.getSelectedItem();
    Lecture lecture = (Lecture) item.getLayoutData();
    ELearningController.getInstance().loadLectureDetails(lecture);
  }

  private void doOnLogoutSelect() {
    LoginController.logout();
  }
}

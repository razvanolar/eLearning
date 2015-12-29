package com.google.gwt.sample.elearning.client.profilebar;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ProfileBarView implements ProfileBarController.IProfileBarView {

  private ToolBar toolBar;
  private Menu userMenu;
  private MenuItem showProfileItem, logoutItem, viewLogsItem, changePasswordItem;
  private MenuItem userLecturesItem, unenrolledLecturesItem;

  private boolean addSettingsButton;
  private TextButton settingsButton;
  private TextButton lecturesButton;
  private Widget userLecturesView;

  public ProfileBarView(boolean addSettingsButton, Widget userLecturesView) {
    this.addSettingsButton = addSettingsButton;
    this.userLecturesView = userLecturesView;
    initGUI();
  }

  private void initGUI() {
    toolBar = new ToolBar();
    TextButton userButton = new TextButton("Profile");
    TextButton forumButton = new TextButton("Forum");
    TextButton chatButton = new TextButton("Chat");
    lecturesButton = new TextButton("Lectures", ELearningController.ICONS.lectures());
    showProfileItem = new MenuItem("Show profile");
    changePasswordItem = new MenuItem("Change Password");
    viewLogsItem = new MenuItem("View logs");
    logoutItem = new MenuItem("Logout");
    userMenu = new Menu();
    Menu lecturesMenu = new Menu();
    userLecturesItem = new MenuItem("My Lectures");
    unenrolledLecturesItem = new MenuItem("Unenrolled Lectures");

    unenrolledLecturesItem.setSubMenu(new GridMenu(userLecturesView));

    logoutItem.setIcon(ELearningController.ICONS.logout());
    userMenu.add(showProfileItem);
    userMenu.add(viewLogsItem);
    userMenu.add(changePasswordItem);
    userMenu.add(logoutItem);

    lecturesMenu.add(userLecturesItem);
    lecturesMenu.add(unenrolledLecturesItem);
    lecturesButton.setMenu(lecturesMenu);

    lecturesButton.setWidth(100);
    userButton.setWidth(100);
    userButton.setIcon(ELearningController.ICONS.profile());
    userButton.setMenu(userMenu);
    forumButton.setWidth(80);
    forumButton.setIcon(ELearningController.ICONS.forum());
    chatButton.setWidth(80);
    chatButton.setIcon(ELearningController.ICONS.chat());

    toolBar.setHorizontalSpacing(5);
    toolBar.add(lecturesButton);
    toolBar.add(userButton);
    toolBar.add(forumButton);
    toolBar.add(chatButton);

    if (addSettingsButton) {
      settingsButton = new TextButton("", ELearningController.ICONS.settings());
      toolBar.add(settingsButton);
    }
    toolBar.setWidth(455);
  }

  public TextButton getSettingsButton() {
    return settingsButton;
  }

  public Widget asWidget() {
    toolBar.forceLayout();
    return toolBar;
  }

  public MenuItem getViewLogsMenuItem() {
    return viewLogsItem;
  }

  public MenuItem getLogoutMenuItem() {
    return logoutItem;
  }

  public MenuItem getUserLecturesItem() {
    return userLecturesItem;
  }

  public MenuItem getUnenrolledMenuItem() {
    return unenrolledLecturesItem;
  }

  public TextButton getLecturesButton() {
    return lecturesButton;
  }
}

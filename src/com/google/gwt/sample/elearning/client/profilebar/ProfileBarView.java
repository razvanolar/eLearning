package com.google.gwt.sample.elearning.client.profilebar;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ProfileBarView implements ProfileBarController.IProfileBarView {

  private HBoxLayoutContainer mainContainer;
  private ToolBar toolBar;
  private Menu userMenu;
  private MenuItem showProfileItem, logoutItem;

  private boolean addSettingsButton;
  private TextButton settingsButton;

  public ProfileBarView(boolean addSettingsButton) {
    this.addSettingsButton = addSettingsButton;
    initGUI();
  }

  private void initGUI() {
    toolBar = new ToolBar();
    mainContainer = new HBoxLayoutContainer();
    TextButton userButton = new TextButton("Profile");
    TextButton forumButton = new TextButton("Forum");
    TextButton chatButton = new TextButton("Chat");
    showProfileItem = new MenuItem("Show profile");
    logoutItem = new MenuItem("Logout");
    userMenu = new Menu();

    logoutItem.setIcon(ELearningController.ICONS.logout());
    userMenu.add(showProfileItem);
    userMenu.add(logoutItem);

    userButton.setWidth(100);
    userButton.setIcon(ELearningController.ICONS.profile());
    userButton.setMenu(userMenu);
    forumButton.setWidth(100);
    forumButton.setIcon(ELearningController.ICONS.forum());
    chatButton.setWidth(100);
    chatButton.setIcon(ELearningController.ICONS.chat());

    toolBar.setHorizontalSpacing(5);
    toolBar.add(userButton);
    toolBar.add(forumButton);
    toolBar.add(chatButton);

    if (addSettingsButton) {
      settingsButton = new TextButton("", ELearningController.ICONS.settings());
      toolBar.add(settingsButton);
    }
    toolBar.setWidth(355);
  }

  @Override
  public TextButton getSettingsButton() {
    return settingsButton;
  }

  @Override
  public Widget asWidget() {
    toolBar.forceLayout();
    return toolBar;
  }
}

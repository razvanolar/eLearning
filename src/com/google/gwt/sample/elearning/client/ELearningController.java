package com.google.gwt.sample.elearning.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.icons.Icons;
import com.google.gwt.sample.elearning.client.login.LoginController;
import com.google.gwt.sample.elearning.client.login.LoginView;
import com.google.gwt.sample.elearning.client.profilebar.ProfileBarController;
import com.google.gwt.sample.elearning.client.profilebar.ProfileBarView;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;

/***
 * Created by razvanolar on 13.11.2015.
 */
public class ELearningController {

  private static ELearningController INSTANCE;
  public static final Icons ICONS = GWT.create(Icons.class);

  private BorderLayoutContainer mainELearningContainer;
  private HBoxLayoutContainer buttonsContainer;

  private ProfileBarController profileController;

  private ELearningController() {}

  public void run() {
    String sessionID = Cookies.getCookie("sid");
    if(sessionID == null)
      displayLoginWindow();
    else
      displayWindow();
  }

  private void displayLoginWindow() {
    LoginController.ILoginView loginView = new LoginView();
    LoginController loginController = new LoginController(loginView);
    mainELearningContainer.setCenterWidget(loginView.asWidget());
    loginController.bind();
  }

  private void displayWindow() {
    ProfileBarController.IProfileBarView profileBarView = new ProfileBarView(true);
    profileController = new ProfileBarController(profileBarView);
    profileController.bind();
    buttonsContainer.add(profileBarView.asWidget());
    buttonsContainer.forceLayout();
  }

  public void onSuccessLogin() {
    mainELearningContainer.setCenterWidget(null);
    (new MessageBox("INFO", "BRAVO")).show();

    displayWindow();
  }

  public static ELearningController getInstance() {
    if (INSTANCE == null)
      INSTANCE = new ELearningController();
    return INSTANCE;
  }

  public void setViews(BorderLayoutContainer mainELearningContainer, HBoxLayoutContainer buttonsContainer) {
    this.mainELearningContainer = mainELearningContainer;
    this.buttonsContainer = buttonsContainer;
  }
}

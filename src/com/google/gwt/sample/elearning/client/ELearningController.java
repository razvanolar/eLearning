package com.google.gwt.sample.elearning.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.icons.Icons;
import com.google.gwt.sample.elearning.client.login.LoginController;
import com.google.gwt.sample.elearning.client.login.LoginView;
import com.google.gwt.sample.elearning.client.logs.LogGridHandler;
import com.google.gwt.sample.elearning.client.profilebar.ProfileBarController;
import com.google.gwt.sample.elearning.client.profilebar.ProfileBarView;
import com.google.gwt.sample.elearning.client.service.LoginService;
import com.google.gwt.sample.elearning.client.service.LoginServiceAsync;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;

import java.util.logging.Logger;

/***
 * Created by razvanolar on 13.11.2015.
 */
public class ELearningController {

  private static ELearningController INSTANCE;
  public static final Icons ICONS = GWT.create(Icons.class);
  LogGridHandler logGridHandler;
  Logger logger = Logger.getLogger("com.google.gwt.sample.elearning.client");
  private BorderLayoutContainer mainELearningContainer;
  private HBoxLayoutContainer buttonsContainer;

  private static LoginServiceAsync loginService = GWT.create(LoginService.class);
  private UserData currentUser;

  private ELearningController() {}

  public void run() {
    logGridHandler = LogGridHandler.getInstance();
    logger.addHandler(logGridHandler);
    String sessionID = Cookies.getCookie("sid");
    if(sessionID == null)
      displayLoginWindow();
    else {
      loginService.loginFromSessionServer(new AsyncCallback<UserData>() {
        public void onFailure(Throwable caught) {
          displayLoginWindow();
        }

        public void onSuccess(UserData result) {
          currentUser = result;
          displayWindow();
          LoginController.initSessionTimeoutTimer();
        }
      });
    }
  }

  private void displayLoginWindow() {
    LoginController.ILoginView loginView = new LoginView();
    LoginController loginController = new LoginController(loginView, loginService);
    mainELearningContainer.setCenterWidget(loginView.asWidget());
    loginController.bind();
  }

  private void displayWindow() {
    ProfileBarController.IProfileBarView profileBarView = new ProfileBarView(currentUser.getRole() != UserRoleTypes.USER);
    ProfileBarController profileController = new ProfileBarController(profileBarView);
    profileController.bind();
    buttonsContainer.add(profileBarView.asWidget());
    buttonsContainer.forceLayout();
  }

  public void onSuccessLogin(UserData userData) {
    currentUser = userData;
    mainELearningContainer.setCenterWidget(null);
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

  public UserData getCurrentUser() {
    return currentUser;
  }
}

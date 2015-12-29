package com.google.gwt.sample.elearning.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextUtil;
import com.google.gwt.sample.elearning.client.service.LoginService;
import com.google.gwt.sample.elearning.client.service.LoginServiceAsync;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.Date;

/***
 * Created by razvanolar on 13.11.2015.
 */
public class LoginController {

  public interface ILoginView {
    TextField getNameField();
    PasswordField getPasswordField();
    TextButton getLoginButton();
    Label getForgotPasswordButton();
    FieldLabel getLoginFailedLabel();
    void setErrorLabelText(String val);
    Widget asWidget();
  }

  private static LoginServiceAsync loginService = GWT.create(LoginService.class);

  private ILoginView view;
  private static Timer sessionTimeoutTimer;
  private static final int DURATION = 1000 * 60 * 10;// * 60 * 24 * 1;

  public LoginController(ILoginView view, LoginServiceAsync loginService) {
    this.view = view;
    LoginController.loginService = loginService;
  }
  public void bind() {
    addListeners();
  }
  private void login(){
    String user = view.getNameField().getText();
    String password = view.getPasswordField().getText();
    if (TextUtil.isEmptyString(user) || TextUtil.isEmptyString(password)) {
      view.setErrorLabelText("Invalid input.");
      return;
    }
    loginService.loginServer(user, password, new AsyncCallback<UserData>() {
      public void onFailure(Throwable caught) {
        view.setErrorLabelText("Wrong username or password.");
      }

      public void onSuccess(UserData userData) {
        Date expires = new Date(System.currentTimeMillis() + DURATION);
        Cookies.setCookie("sid", userData.getSessionId(), expires, null, "/", false);
        initSessionTimeoutTimer();
        ELearningController.getInstance().onSuccessLogin(userData);
      }
    });
  }

  public static void initSessionTimeoutTimer(){
    sessionTimeoutTimer = new Timer() {
      @Override
      public void run() {
        loginService.isSessionAlive(new AsyncCallback<Boolean>() {
          @Override
          public void onFailure(Throwable caught) {

          }

          @Override
          public void onSuccess(Boolean result) {
            sessionTimeoutTimer.cancel();
            if (result) {
              sessionTimeoutTimer.scheduleRepeating(DURATION);
            } else {
              onSessionTimedOut();
            }
          }
        });
      }
    };
    sessionTimeoutTimer.schedule(DURATION);
  }

  private static void onSessionTimedOut() {
    Cookies.setCookie("sid", null, new Date(System.currentTimeMillis()), null, "/", false);
    MessageBox loginFailMessage = new MessageBox("Error","Your login session has expired please re-login!");
    loginFailMessage.addHideHandler(new HideEvent.HideHandler() {
      @Override
      public void onHide(HideEvent event) {
        Window.Location.reload();
      }
    });
    loginFailMessage.show();
  }

  public static void logout(){
    Cookies.setCookie("sid", null, new Date(System.currentTimeMillis()), null, "/", false);
    Window.Location.reload();
  }

  private void addListeners() {
    view.getLoginButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        login();
      }
    });

    view.getPasswordField().addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
          login();
      }
    });

    view.getNameField().addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          login();
        }
      }
    });

    view.getForgotPasswordButton().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        ForgotPasswordController.IForgotPasswordView forgotPasswordView = new ForgotPasswordView();
        ForgotPasswordController controller = new ForgotPasswordController(forgotPasswordView);
        controller.bind();
        MasterWindow window = new MasterWindow();
        window.setContent(forgotPasswordView.asWidget(), "Forgot Password");
        window.setModal(true);
        window.setPixelSize(300, 150);
        window.show();
        controller.setContentWindow(window);
      }
    });
  }
}

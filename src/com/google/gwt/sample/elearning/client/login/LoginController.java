package com.google.gwt.sample.elearning.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.client.services.LoginService;
import com.google.gwt.sample.elearning.client.services.LoginServiceAsync;
import com.google.gwt.sample.elearning.shared.UserData;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
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
    Widget asWidget();
  }

  private LoginServiceAsync loginService = GWT.create(LoginService.class);

  private ILoginView view;

  public LoginController(ILoginView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {
    view.getLoginButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        String user = view.getNameField().getText();
        String password = view.getPasswordField().getText();
        if (TextInputValidator.isEmptySring(user) || TextInputValidator.isEmptySring(password)) {
        /* TODO : show error */
        }
        loginService.loginServer(user, password, new AsyncCallback<UserData>() {
          @Override
          public void onFailure(Throwable caught) {
          /* TODO : show error */
          }

          @Override
          public void onSuccess(UserData userData) {
            final long DURATION = 1000 * 60;// * 60 * 24 * 1;
            Date expires = new Date(System.currentTimeMillis() + DURATION);
            Cookies.setCookie("sid", userData.getSessionId(), expires, null, "/", false);

            ELearningController.getInstance().onSuccessLogin();
          }
        });
      }
    });
  }
}

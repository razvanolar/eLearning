package com.google.gwt.sample.elearning.client.login;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

/***
 * Created by razvanolar on 13.11.2015.
 */
public class LoginView implements LoginController.ILoginView {

  private TextField nameField;
  private PasswordField passwordField;
  private TextButton loginButton;
  private FieldLabel loginFailedLabel;

  private BorderLayoutContainer mainContainer;

  public LoginView() {
    initGUI();
  }

  private void initGUI() {
    /* initialize fields */
    nameField = new TextField();
    passwordField = new PasswordField();
    loginButton = new TextButton("Login");
    mainContainer = new BorderLayoutContainer();
    loginFailedLabel = new FieldLabel(new Label("Wrong user or password"));
    loginFailedLabel.getElement().getStyle().setColor("red");
    loginFailedLabel.setVisible(false);
    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer();
    HBoxLayoutContainer buttonsContainer = new HBoxLayoutContainer();

    /* buttons container */
    buttonsContainer.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    buttonsContainer.setPack(BoxLayoutContainer.BoxLayoutPack.END);
    buttonsContainer.add(loginButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0)));

    /* form */
    BoxLayoutContainer.BoxLayoutData layoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 0));
    vBoxLayoutContainer.setVBoxLayoutAlign(VBoxLayoutContainer.VBoxLayoutAlign.STRETCH);
    vBoxLayoutContainer.add(new FieldLabel(nameField, "User"), layoutData);
    vBoxLayoutContainer.add(new FieldLabel(passwordField, "Password"), layoutData);
    vBoxLayoutContainer.add(buttonsContainer, layoutData);
    vBoxLayoutContainer.add(loginFailedLabel, layoutData);
    centerLayoutContainer.add(vBoxLayoutContainer);

    mainContainer.setCenterWidget(centerLayoutContainer);
    mainContainer.setStyleName("mainELearningContainer");
  }

  @Override
  public TextField getNameField() {
    return nameField;
  }

  @Override
  public PasswordField getPasswordField() {
    return passwordField;
  }

  @Override
  public TextButton getLoginButton() {
    return loginButton;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public FieldLabel getLoginFailedLabel() {
    return loginFailedLabel;
  }
}

package com.google.gwt.sample.elearning.client.user_profile.change_password;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 *
 * Created by Cristi on 1/16/2016.
 */
public class ChangePasswordView implements ChangePasswordController.IChangePasswordView{
  private BorderLayoutContainer mainContainer;
  private PasswordField oldPassword;
  private PasswordField newPassword;
  private PasswordField confirmPassword;
  private TextButton applyButton;

  public ChangePasswordView(){
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    oldPassword = new PasswordField();
    newPassword = new PasswordField();
    confirmPassword = new PasswordField();
    applyButton = new TextButton("Apply", ELearningController.ICONS.apply());

    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer();
    vBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    BoxLayoutContainer.BoxLayoutData layoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(3,5,0,5));
    vBoxLayoutContainer.add(new FieldLabel(oldPassword, "Old Password"), layoutData);
    vBoxLayoutContainer.add(new FieldLabel(newPassword, "New Password"), layoutData);
    vBoxLayoutContainer.add(new FieldLabel(confirmPassword, "Confirm Password"), layoutData);

    ToolBar toolBar = new ToolBar();
    toolBar.add(applyButton);
    BorderLayoutContainer.BorderLayoutData leftLayoutData = new BorderLayoutContainer.BorderLayoutData(30);
    leftLayoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setNorthWidget(toolBar, leftLayoutData);

    mainContainer.setCenterWidget(vBoxLayoutContainer);
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public void mask() {
    mainContainer.mask();
  }

  @Override
  public void unmask() {
    mainContainer.unmask();
  }

  @Override
  public PasswordField getOldPassword() {
    return oldPassword;
  }

  @Override
  public PasswordField getNewPassword() {
    return newPassword;
  }

  @Override
  public PasswordField getConfirmPassword() {
    return confirmPassword;
  }

  @Override
  public TextButton getApplyButton() {
    return applyButton;
  }
}

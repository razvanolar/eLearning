package com.google.gwt.sample.elearning.client.main_views.right_panel.usersView.sendEmailView;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 *
 * Created by Cristi on 1/16/2016.
 */
public class SendEmailView implements SendEmailController.ISendEmailView{

  private BorderLayoutContainer mainContainer;
  private TextButton sendButton;
  private TextField toField;
  private TextField subjectField;
  private TextArea bodyField;
  private PasswordField pwdField;

  public SendEmailView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    sendButton = new TextButton("Send", ELearningController.ICONS.apply());
    toField = new TextField();
    toField.setEnabled(false);
    subjectField = new TextField();
    bodyField = new TextArea();
    pwdField = new PasswordField();

    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
    VBoxLayoutContainer centerContainer = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCHMAX);
    bodyField.setPixelSize(300, 110);
    BoxLayoutContainer.BoxLayoutData formLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(3,3,0,3));
    centerContainer.add(new FieldLabel(toField, "To"), formLayoutData);
    centerContainer.add(new FieldLabel(subjectField, "Subject"), formLayoutData);
    centerContainer.add(new FieldLabel(new Label(), "Body"), formLayoutData);
    centerContainer.add(bodyField, formLayoutData);
    centerContainer.add(new FieldLabel(pwdField, "E-mail password"), formLayoutData);
    ToolBar toolBar = new ToolBar();
    toolBar.add(sendButton);
    BorderLayoutContainer.BorderLayoutData leftLayoutData = new BorderLayoutContainer.BorderLayoutData(30);
    leftLayoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setNorthWidget(toolBar, leftLayoutData);
    centerLayoutContainer.add(centerContainer);
    mainContainer.setCenterWidget(centerLayoutContainer);
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public TextButton getSendButton() {
    return sendButton;
  }

  @Override
  public TextField getToField() {
    return toField;
  }

  @Override
  public TextField getSubjectField() {
    return subjectField;
  }

  @Override
  public TextArea getBodyField() {
    return bodyField;
  }

  @Override
  public PasswordField getPasswordField() {
    return pwdField;
  }

  @Override
  public void mask() {
    mainContainer.mask();
  }

  @Override
  public void unmask() {
    mainContainer.unmask();
  }
}

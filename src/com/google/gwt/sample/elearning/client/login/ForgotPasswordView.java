package com.google.gwt.sample.elearning.client.login;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 *
 * Created by Cristi on 12/28/2015.
 */
public class ForgotPasswordView implements ForgotPasswordController.IForgotPasswordView {
  private TextButton sendButton = new TextButton("Send");
  private TextField emailField = new TextField();
  private BorderLayoutContainer mainContainer;

  public ForgotPasswordView(){
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    sendButton.setIcon(ELearningController.ICONS.apply());
    ToolBar toolBar = new ToolBar();
    toolBar.add(sendButton);
    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCHMAX);
    vBoxLayoutContainer.add(new FieldLabel(emailField, "Email"), new BoxLayoutContainer.BoxLayoutData(new Margins(5)));
    centerLayoutContainer.add(vBoxLayoutContainer);
    mainContainer.setCenterWidget(centerLayoutContainer);

  }

  @Override
  public TextField getMailField() {
    return emailField;
  }

  @Override
  public TextButton getSendButton() {
    return sendButton;
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
}

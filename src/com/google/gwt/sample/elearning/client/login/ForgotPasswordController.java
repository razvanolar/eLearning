package com.google.gwt.sample.elearning.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.service.EmailService;
import com.google.gwt.sample.elearning.client.service.EmailServiceAsync;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.logging.Logger;

/**
 *
 * Created by Cristi on 12/28/2015.
 */
public class ForgotPasswordController {
  public interface IForgotPasswordView extends MaskableView{
    TextField getMailField();
    TextButton getSendButton();
    Widget asWidget();
    void mask();
    void unmask();
  }
  private Window window;
  private IForgotPasswordView view;
  private EmailServiceAsync emailService = GWT.create(EmailService.class);
  private Logger logger = Logger.getLogger(ForgotPasswordController.class.getName());

  public ForgotPasswordController(IForgotPasswordView view){
    this.view = view;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {
    view.getSendButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnSendButtonSelection();
      }
    });
  }

  private void doOnSendButtonSelection() {
    String email = view.getMailField().getCurrentValue();
    emailService.sendForgetEmailPassword(email, new ELearningAsyncCallBack<Void>(view,logger) {
      @Override
      public void onSuccess(Void result) {
        new MessageBox("Info", "An Email has been sent with your user and password.").show();
        window.hide();
      }
    });
  }

  public void setContentWindow(MasterWindow contentWindow) {
    this.window = contentWindow;
  }

}

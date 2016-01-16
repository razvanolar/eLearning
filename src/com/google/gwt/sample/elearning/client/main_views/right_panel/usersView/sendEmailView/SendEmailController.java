package com.google.gwt.sample.elearning.client.main_views.right_panel.usersView.sendEmailView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextUtil;
import com.google.gwt.sample.elearning.client.service.EmailService;
import com.google.gwt.sample.elearning.client.service.EmailServiceAsync;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.logging.Logger;

/**
 *
 * Created by Cristi on 1/16/2016.
 */
public class SendEmailController {

  public interface ISendEmailView extends MaskableView{
    Widget asWidget();
    TextButton getSendButton();
    TextField getToField();
    TextField getSubjectField();
    TextArea getBodyField();
    PasswordField getPasswordField();
  }

  private ISendEmailView view;
  private String fromEmail;
  private String toEmail;
  private EmailServiceAsync emailService = GWT.create(EmailService.class);
  private Logger logger = Logger.getLogger(SendEmailController.class.getName());
  private Window window;

  public SendEmailController(ISendEmailView view, String fromEmail, String toEmail) {
    this.view = view;
    this.fromEmail = fromEmail;
    this.toEmail = toEmail;
  }

  public void bind() {
    view.getToField().setValue(toEmail);
    addListeners();
  }

  private void addListeners() {
    view.getSendButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnSendButton();
      }
    });
  }

  private void doOnSendButton() {
    String subject = view.getSubjectField().getValue();
    String body = view.getBodyField().getValue();
    String password = view.getPasswordField().getValue();
    if (body == null) {
      body = "";
    }
    if (isValid(subject, password)) {
      view.mask();
      emailService.sendCustomEmail(toEmail, fromEmail, password, subject, body, new ELearningAsyncCallBack<Void>(view, logger) {
        @Override
        public void onSuccess(Void result) {
          new MessageBox("Info", "Mail sent successfully").show();
          if (window != null)
            window.hide();
        }
      });
    }
  }

  private boolean isValid(String subject, String password) {
    if (TextUtil.isEmptyString(subject)) {
      new MessageBox("Info", "Subject field cannot be empty").show();
      return false;
    }

    if (TextUtil.isEmptyString(password)) {
      new MessageBox("Info", "Password field cannot be empty").show();
      return false;
    }
    return true;
  }

  public void setContentWindow(MasterWindow contentWindow) {
    this.window = contentWindow;
  }

}

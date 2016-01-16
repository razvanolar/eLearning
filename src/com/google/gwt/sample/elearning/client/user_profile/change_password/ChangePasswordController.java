package com.google.gwt.sample.elearning.client.user_profile.change_password;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextUtil;
import com.google.gwt.sample.elearning.client.service.UserService;
import com.google.gwt.sample.elearning.client.service.UserServiceAsync;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.logging.Logger;

/**
 *
 * Created by Cristi on 1/16/2016.
 */
public class ChangePasswordController {

  public interface IChangePasswordView extends MaskableView {
    PasswordField getOldPassword();
    PasswordField getNewPassword();
    PasswordField getConfirmPassword();
    TextButton getApplyButton();
    Widget asWidget();
  }

  private IChangePasswordView view;
  private UserServiceAsync userService = GWT.create(UserService.class);
  private Logger logger = Logger.getLogger(ChangePasswordController.class.getName());
  private Window window;

  public ChangePasswordController(IChangePasswordView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {
    view.getApplyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnApplyButtonSelection();
      }
    });
  }

  private void doOnApplyButtonSelection() {
    String oldPwd = view.getOldPassword().getValue();
    String newPwd = view.getNewPassword().getValue();
    String confPwd = view.getConfirmPassword().getValue();
    if (isValid(oldPwd, newPwd, confPwd)) {
      userService.changePassword(ELearningController.getInstance().getCurrentUser().getId(), newPwd, new ELearningAsyncCallBack<Void>(view, logger) {
        @Override
        public void onSuccess(Void result) {
          new MessageBox("Info", "Password changed successfully.").show();
          if (window != null)
            window.hide();
        }
      });
    }
  }

  private boolean isValid(String oldPwd, String newPwd, String confPwd) {
    if (TextUtil.isEmptyString(oldPwd)) {
      new MessageBox("Info", "Old Password field cannot be empty.").show();
      return false;
    }
    if (TextUtil.isEmptyString(newPwd)) {
      new MessageBox("Info", "New Password field cannot be empty.").show();
      return false;
    }
    if (TextUtil.isEmptyString(confPwd)) {
      new MessageBox("Info", "Confirm Password field cannot be empty.").show();
      return false;
    }
    if(!ELearningController.getInstance().getCurrentUser().getPassword().equals(oldPwd)){
      new MessageBox("Info", "Wrong old password.").show();
      return false;
    }
    if(!newPwd.equals(confPwd)){
      new MessageBox("Info", "New Password doesn't match the Confirmation Password").show();
      return false;
    }
    return true;
  }
  public void setContentWindow(MasterWindow contentWindow) {
    this.window = contentWindow;
  }
}

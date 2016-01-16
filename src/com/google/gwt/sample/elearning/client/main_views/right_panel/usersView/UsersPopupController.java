package com.google.gwt.sample.elearning.client.main_views.right_panel.usersView;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.main_views.right_panel.usersView.sendEmailView.SendEmailController;
import com.google.gwt.sample.elearning.client.main_views.right_panel.usersView.sendEmailView.SendEmailView;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 *
 * Created by Cristi on 1/16/2016.
 */
public class UsersPopupController {
  public interface IUsersPopupView {
    Widget asWidget();
    HtmlLayoutContainer getHtmlLayoutContainer();
    Label getNameLabel();
    Label getEmailLabel();
    TextButton getSendEmailButton();
  }

  IUsersPopupView view;
  UserData user;

  public UsersPopupController(IUsersPopupView view, UserData userData) {
    this.view = view;
    this.user = userData;

  }

  public void bind(){
    view.getEmailLabel().setText(user.getEmail());
    view.getNameLabel().setText(user.getFirstName() + " " + user.getLastName());
    view.getHtmlLayoutContainer().setHTML("<img src='" + user.getImagePath() + "'/>");
    addListeners();
  }

  private void addListeners() {
    view.getSendEmailButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        SendEmailView sendEmailView = new SendEmailView();
        SendEmailController controller = new SendEmailController(sendEmailView, ELearningController.getInstance().getCurrentUser().getEmail(), user.getEmail());
        controller.bind();
        MasterWindow window = new MasterWindow();
        window.setContent(sendEmailView.asWidget(), "Send Email");
        window.setModal(true);
        window.setMinHeight(285);
        window.setMinWidth(335);
        window.setPixelSize(335, 285);
        window.setResizable(false);
        window.show();
        controller.setContentWindow(window);
      }
    });
  }

}

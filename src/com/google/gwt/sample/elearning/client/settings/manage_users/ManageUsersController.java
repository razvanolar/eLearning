package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.shared.UserData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.List;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ManageUsersController {

  public enum UserViewState {
    ADD, EDIT, ADDING, NONE
  }

  public interface IManageUsersView {
    TextButton getAddButton();
    TextButton getEditButton();
    TextButton getDeleteButton();
    TextField getFirstNameField();
    TextField getLastNameField();
    TextField getEmailField();
    Grid<UserData> getGrid();
    void setState(UserViewState state);
    void loadUserData(UserData userData);
    Widget asWidget();
  }

  private IManageUsersView view;

  public ManageUsersController(IManageUsersView view) {
    this.view = view;
  }

  public void bind() {
    addListeneres();
  }

  private void addListeneres() {
    Grid<UserData> grid = view.getGrid();
    if (grid != null && grid.getSelectionModel() != null) {
      grid.getSelectionModel().addSelectionHandler(new SelectionHandler<UserData>() {
        public void onSelection(SelectionEvent<UserData> event) {
//          doGridSelectionEvent(event);
        }
      });
      grid.getSelectionModel()
          .addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<UserData>() {
            public void onSelectionChanged(SelectionChangedEvent<UserData> event) {
              doGridSelectionEvent(event);
            }
          });
    }

//    ValueChangeHandler<String> textFieldsValidator = new ValueChangeHandler<String>() {
//      public void onValueChange(ValueChangeEvent<String> event) {
//        if (!TextInputValidator.isEmptyString(view.getFirstNameField().getText()) &&
//            !TextInputValidator.isEmptyString(view.getLastNameField().getText()) &&
//            !TextInputValidator.isEmptyString(view.getEmailField().getText()) &&
//            getSelectedUser() == null)
//          view.setState(UserViewState.ADD);
//      }
//    };

    KeyUpHandler textFieldsValidator = new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        if (isUserSelected()) {
          view.setState(UserViewState.EDIT);
        } else {
          if (!TextInputValidator.isEmptyString(view.getFirstNameField().getText()) &&
              !TextInputValidator.isEmptyString(view.getLastNameField().getText()) &&
              !TextInputValidator.isEmptyString(view.getEmailField().getText()))
            view.setState(UserViewState.ADD);
          else
            view.setState(UserViewState.ADDING);
        }
      }
    };

    view.getFirstNameField().addKeyUpHandler(textFieldsValidator);
    view.getLastNameField().addKeyUpHandler(textFieldsValidator);
    view.getEmailField().addKeyUpHandler(textFieldsValidator);
  }

  private void doGridSelectionEvent(SelectionChangedEvent<UserData> event) {
    List<UserData> selection = event.getSelection();
    if (selection == null || selection.isEmpty())
      view.setState(UserViewState.NONE);
    else {
      view.setState(UserViewState.EDIT);
      view.loadUserData(selection.get(0));
    }
  }

  private UserData getSelectedUser() {
    if (view.getGrid() != null && view.getGrid().getSelectionModel() != null)
      return view.getGrid().getSelectionModel().getSelectedItem();
    return null;
  }

  private boolean isUserSelected() {
    return getSelectedUser() != null;
  }
}

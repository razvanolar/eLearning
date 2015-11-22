package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.client.service.UserService;
import com.google.gwt.sample.elearning.client.service.UserServiceAsync;
import com.google.gwt.sample.elearning.client.settings.ISettingsController;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ManageUsersController implements ISettingsController {

  public enum UserViewState {
    ADD, EDIT, ADDING, NONE
  }

  public interface IManageUsersView {
    TextButton getAddButton();
    TextButton getEditButton();
    TextButton getDeleteButton();
    TextField getUserNameField();
    TextField getFirstNameField();
    TextField getLastNameField();
    TextField getEmailField();
    SimpleComboBox<UserRoleTypes> getRoleCombo();
    Grid<UserData> getGrid();
    void setState(UserViewState state);
    void loadUserData(UserData userData);
    void clearFields();
    void mask();
    void unmask();
    Widget asWidget();
  }

  private IManageUsersView view;

  private UserServiceAsync userService = GWT.create(UserService.class);
  private UserDataProperties userProperties = GWT.create(UserDataProperties.class);

  private static Logger log = Logger.getLogger(ManageUsersController.class.getName());

  public ManageUsersController(IManageUsersView view) {
    this.view = view;
    log.info("ManageUsersController - constructor");
  }

  public void bind() {
    addListeneres();
  }

  private void addListeneres() {
    Grid<UserData> grid = view.getGrid();
    if (grid != null && grid.getSelectionModel() != null) {
      grid.getSelectionModel()
          .addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<UserData>() {
            public void onSelectionChanged(SelectionChangedEvent<UserData> event) {
              doGridSelectionEvent(event);
            }
          });
    }

    KeyUpHandler textFieldsValidator = new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        if (isUserSelected()) {
          view.setState(UserViewState.EDIT);
        } else {
          if (!TextInputValidator.isEmptyString(view.getUserNameField().getText()) &&
              !TextInputValidator.isEmptyString(view.getFirstNameField().getText()) &&
              !TextInputValidator.isEmptyString(view.getLastNameField().getText()) &&
              !TextInputValidator.isEmptyString(view.getEmailField().getText()))
            view.setState(UserViewState.ADD);
          else
            view.setState(UserViewState.ADDING);
        }
      }
    };

    view.getUserNameField().addKeyUpHandler(textFieldsValidator);
    view.getFirstNameField().addKeyUpHandler(textFieldsValidator);
    view.getLastNameField().addKeyUpHandler(textFieldsValidator);
    view.getEmailField().addKeyUpHandler(textFieldsValidator);

    view.getAddButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onnAddButtonSelection();
      }
    });

    view.getEditButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onEditButtonSelection();
      }
    });

    view.getDeleteButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onDeleteButtonSelection();
      }
    });
  }

  private void onnAddButtonSelection() {
    String fName = view.getFirstNameField().getText();
    String lName = view.getLastNameField().getText();
    String uName = view.getUserNameField().getText();
    String eMail = view.getEmailField().getText();
    //TODO collect user role
    if(TextInputValidator.isEmptyString(fName) || TextInputValidator.isEmptyString(lName) || TextInputValidator.isEmptyString(uName) || TextInputValidator.isEmptyString(eMail)){
      new MessageBox("","Invalid input").show();
      return;
    }

    userService.createUser(new UserData(uName, fName, lName, eMail, UserRoleTypes.USER), new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        new MessageBox("", "Could not create User").show();
      }

      @Override
      public void onSuccess(Void result) {
        new MessageBox("", "User created").show();
        view.clearFields();
        loadUsers();
      }
    });
  }

  private void onEditButtonSelection() {
    long id = view.getGrid().getSelectionModel().getSelectedItem().getId();
    String fName = view.getFirstNameField().getText();
    String lName = view.getLastNameField().getText();
    String uName = view.getUserNameField().getText();
    String eMail = view.getEmailField().getText();
    if(TextInputValidator.isEmptyString(fName) || TextInputValidator.isEmptyString(lName) || TextInputValidator.isEmptyString(uName) || TextInputValidator.isEmptyString(eMail)){
      new MessageBox("","Invalid input").show();
      return;
    }
    //TODO collect user role
    UserData user = new UserData(uName, fName, lName, eMail, UserRoleTypes.USER);
    user.setId(id);
    userService.updateUser(user, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        new MessageBox("", "Could not update User").show();
      }

      @Override
      public void onSuccess(Void result) {
        new MessageBox("", "User updated").show();
        view.clearFields();
        loadUsers();
      }
    });
  }

  private void onDeleteButtonSelection() {
    List<UserData> userDatas = view.getGrid().getSelectionModel().getSelectedItems();
    List<Long> ids = new ArrayList<Long>();
    for(UserData userData : userDatas)
      ids.add(userData.getId());
    userService.removeUser(ids, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable caught) {
        new MessageBox("","Could not delete users").show();
      }

      @Override
      public void onSuccess(Void result) {
        new MessageBox("","Users deleted").show();
        loadUsers();
      }
    });
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

  @Override
  public void loadResources() {
    ListStore<UserData> store = getListStore();
    if (store == null || store.getAll().isEmpty()) {
      loadUsers();
    }
  }

  private void loadUsers() {
    view.mask();
    userService.getAllUsers(new AsyncCallback<List<UserData>>() {
      @Override
      public void onFailure(Throwable caught) {
        view.unmask();
        (new MessageBox("Error", "An error occurred while retrieving users.")).show();
      }

      @Override
      public void onSuccess(List<UserData> result) {
        ListStore<UserData> store = getListStore();
        if (result != null) {
          store.clear();
          store.addAll(result);
        }
        view.unmask();
        view.setState(UserViewState.NONE);
      }
    });
  }

  private ListStore<UserData> getListStore() {
    return view.getGrid().getStore();
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

package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextUtil;
import com.google.gwt.sample.elearning.client.eLearningUtils.models_properties.UserDataProperties;
import com.google.gwt.sample.elearning.client.service.UserService;
import com.google.gwt.sample.elearning.client.service.UserServiceAsync;
import com.google.gwt.sample.elearning.client.settings.ISettingsController;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.FileExtensionTypes;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
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
    ADD, EDIT, ADDING, DOWNLOAD, NONE
  }

  public interface IManageUsersView extends MaskableView{
    TextButton getAddButton();
    TextButton getEditButton();
    TextButton getDeleteButton();
    TextButton getDownloadUsersButton();
    TextButton getUploadUsersButton();
    TextButton getRefreshButton();
    FormPanel getFileFormPanel();
    FileUpload getFileUpload();
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

  private Logger log = Logger.getLogger(ManageUsersController.class.getName());

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
          if (!TextUtil.isEmptyString(view.getUserNameField().getText()) &&
              !TextUtil.isEmptyString(view.getFirstNameField().getText()) &&
              !TextUtil.isEmptyString(view.getLastNameField().getText()) &&
              !TextUtil.isEmptyString(view.getEmailField().getText()))
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
      public void onSelect(SelectEvent event) {
        onAddButtonSelection();
      }
    });

    view.getEditButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onEditButtonSelection();
      }
    });

    view.getDeleteButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onDeleteButtonSelection();
      }
    });

    view.getFileFormPanel().addSubmitHandler(new FormPanel.SubmitHandler() {
      public void onSubmit(FormPanel.SubmitEvent event) {
        String fileName = view.getFileUpload().getFilename();
        log.info("fileName: " + fileName);
        if (fileName == null || fileName.isEmpty()) {
          (new MessageBox("Warning", "Please select a file!")).show();
          event.cancel();
        } else if (FileExtensionTypes.getFileExtensionByValue(TextUtil.getFileExtentsion(fileName)) == FileExtensionTypes.XML) {
          log.info("Uploading file");
        } else {
          (new MessageBox("Warning", "Only xml files can be uploaded!")).show();
          event.cancel();
        }
      }
    });

    view.getFileUpload().addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        String fileName = view.getFileUpload().getFilename();
        if (fileName == null || fileName.isEmpty()) {
          view.getUploadUsersButton().setEnabled(false);
        } else
          view.getUploadUsersButton().setEnabled(true);
      }
    });

    view.getFileFormPanel().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        log.info("Users were uploaded");
        loadUsers();
      }
    });

    view.getDownloadUsersButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onDownloadUsersSelection();
      }
    });

    view.getUploadUsersButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onUploadUsersSelection();
      }
    });

    view.getRefreshButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        loadResources();
      }
    });
  }

  private void onAddButtonSelection() {
    String fName = view.getFirstNameField().getText();
    String lName = view.getLastNameField().getText();
    String uName = view.getUserNameField().getText();
    String eMail = view.getEmailField().getText();
    //TODO collect user role
    if(TextUtil.isEmptyString(fName) ||
            TextUtil.isEmptyString(lName) ||
            TextUtil.isEmptyString(uName) ||
            TextUtil.isEmptyString(eMail) ||
            view.getRoleCombo().getValue() == null){
      new MessageBox("","Invalid input").show();
      return;
    }

    userService.createUser(new UserData(uName, uName, fName, lName, eMail, view.getRoleCombo().getValue()), new ELearningAsyncCallBack<Void>(view, log) {
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
    if(TextUtil.isEmptyString(fName) ||
            TextUtil.isEmptyString(lName) ||
            TextUtil.isEmptyString(uName) ||
            TextUtil.isEmptyString(eMail) ||
            view.getRoleCombo().getValue() == null){
      new MessageBox("","Invalid input").show();
      return;
    }
    //TODO collect user role
    UserData user = new UserData(uName, uName, fName, lName, eMail, view.getRoleCombo().getValue());
    user.setId(id);
    userService.updateUser(user, new ELearningAsyncCallBack<Void>(view, log) {
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
    userService.removeUser(ids, new ELearningAsyncCallBack<Void>(view, log) {
      @Override
      public void onSuccess(Void result) {
        new MessageBox("", "Users deleted").show();
        loadUsers();
      }
    });
  }

  private void onDownloadUsersSelection() {
    String url = GWT.getModuleBaseURL() + "usersDownloadService?users=";
    List<UserData> selectedUsers = getSelectedUsers();
    String ids = "";
    if (selectedUsers != null && !selectedUsers.isEmpty())
      for (int i=0; i<selectedUsers.size(); i++) {
        ids += selectedUsers.get(i).getId();
        if (i != selectedUsers.size()-1)
          ids += "$";
      }
    url += ids;
    Window.open(url, "_self", "");
  }

  private void onUploadUsersSelection() {
    view.getFileFormPanel().setAction(GWT.getModuleBaseURL() + "usersUploadService?");
    view.getFileFormPanel().submit();
  }

  private void doGridSelectionEvent(SelectionChangedEvent<UserData> event) {
    List<UserData> selection = event.getSelection();
    if (selection == null || selection.isEmpty())
      view.setState(UserViewState.NONE);
    else {
      if (selection.size() == 1) {
        view.setState(UserViewState.EDIT);
        view.loadUserData(selection.get(0));
      } else {
        view.setState(UserViewState.DOWNLOAD);
      }
    }
  }

  @Override
  public void loadResources() {
    ListStore<UserData> store = getListStore();
    log.info("ManageUsersController - loadResources");
    if (store.getAll() == null || store.getAll().isEmpty()) {
      log.info("start loading users ----");
      loadUsers();
    }
  }

  private void loadUsers() {
    view.mask();
    userService.getAllUsers(new ELearningAsyncCallBack<List<UserData>>(view, log) {
      @Override
      public void onFailure(Throwable caught) {
        super.onFailure(caught);
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

  private List<UserData> getSelectedUsers() {
    if (view.getGrid() != null && view.getGrid().getSelectionModel() != null) {
      List<UserData> selectedItems = view.getGrid().getSelectionModel().getSelectedItems();
      if (selectedItems == null || selectedItems.isEmpty())
        return null;
      return selectedItems;
    }
    return null;
  }

  private boolean isUserSelected() {
    return getSelectedUser() != null;
  }

  @Override
  public String getControllerName() {
    return "ManageUsersController";
  }
}

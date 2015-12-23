package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ManageUsersView implements ManageUsersController.IManageUsersView {

  private static UserDataProperties userProperties = GWT.create(UserDataProperties.class);
  private UserRoleTypes defaultRole = UserRoleTypes.USER;

  private BorderLayoutContainer mainContainer;
  private TextButton addButton, editButton, deleteButton;
  private Grid<UserData> userDataGridView;

  private ManageUsersController.UserViewState state;
  private TextField userNameField;
  private TextField firstNameField;
  private TextField lastNameField;
  private TextField emailField;
  private SimpleComboBox<UserRoleTypes> roleComboBox;
  private TextButton downloadUsersButton;
  private TextButton uploadUsersButton;
  private FormPanel fileFormPanel;
  private FileUpload fileUpload;

  private Logger log = Logger.getLogger(ManageUsersView.class.getName());

  public ManageUsersView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    addButton = new TextButton("Add", ELearningController.ICONS.add());
    editButton = new TextButton("Edit", ELearningController.ICONS.edit());
    deleteButton = new TextButton("Delete", ELearningController.ICONS.delete());
    userNameField = new TextField();
    firstNameField = new TextField();
    lastNameField = new TextField();
    emailField = new TextField();
    roleComboBox = new SimpleComboBox<UserRoleTypes>(new LabelProvider<UserRoleTypes>() {
      public String getLabel(UserRoleTypes item) {
        return item.name();
      }
    });
    ToolBar toolBar = new ToolBar();
    downloadUsersButton = new TextButton("Download users", ELearningController.ICONS.download());
    uploadUsersButton = new TextButton("Upload users", ELearningController.ICONS.upload());
    fileFormPanel = new FormPanel();
    fileUpload = new FileUpload();
    HorizontalPanel fileUploadContainer = new HorizontalPanel();
    CenterLayoutContainer formContainer = new CenterLayoutContainer();
    VerticalLayoutContainer formPanel = new VerticalLayoutContainer();
    BorderLayoutContainer gridContainer = new BorderLayoutContainer();
    HBoxLayoutContainer buttonsContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    userDataGridView = createGrid();

    fileFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
    fileFormPanel.setMethod(FormPanel.METHOD_POST);
    fileFormPanel.setWidget(fileUploadContainer);
    fileUpload.setName("fileUpload");
    fileUploadContainer.add(fileUpload);

    toolBar.add(downloadUsersButton);
    toolBar.add(uploadUsersButton);
//    toolBar.add(fileUploadContainer);
    toolBar.add(fileFormPanel);

    BoxLayoutContainer.BoxLayoutData hBoxLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(5));
    hBoxLayoutData.setFlex(1);
    buttonsContainer.add(addButton, hBoxLayoutData);
    buttonsContainer.add(editButton, hBoxLayoutData);
    buttonsContainer.add(deleteButton, hBoxLayoutData);
    buttonsContainer.setStyleName("buttonsBar");

    roleComboBox.setEditable(false);
    roleComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
    for (UserRoleTypes role : UserRoleTypes.values())
      roleComboBox.add(role);
    roleComboBox.setValue(defaultRole);

    VerticalLayoutContainer.VerticalLayoutData verticalLayoutData = new VerticalLayoutContainer.VerticalLayoutData(1, -1);
    formPanel.add(new FieldLabel(userNameField, "User Name"), verticalLayoutData);
    formPanel.add(new FieldLabel(firstNameField, "First Name"), verticalLayoutData);
    formPanel.add(new FieldLabel(lastNameField, "Last Name"), verticalLayoutData);
    formPanel.add(new FieldLabel(emailField, "Email"), verticalLayoutData);
    formPanel.add(new FieldLabel(roleComboBox, "Role"), verticalLayoutData);
    formPanel.add(buttonsContainer, verticalLayoutData);

    formContainer.add(formPanel);
    formContainer.setStyleName("whiteBackground");

    gridContainer.setSouthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    gridContainer.setCenterWidget(userDataGridView);

    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(.65);
    layoutData.setSplit(true);
    layoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setWestWidget(gridContainer, layoutData);
    mainContainer.setCenterWidget(formContainer);

    uploadUsersButton.setEnabled(false);
    setState(ManageUsersController.UserViewState.NONE);
  }

  private Grid<UserData> createGrid() {
    IdentityValueProvider<UserData> identityValueProvider = new IdentityValueProvider<UserData>("sm");
    CheckBoxSelectionModel<UserData> selectionModel = new CheckBoxSelectionModel<UserData>(identityValueProvider);
    selectionModel.setSelectionMode(Style.SelectionMode.MULTI);

    List<ColumnConfig<UserData, ?>> columnConfigList = new ArrayList<ColumnConfig<UserData, ?>>();

    columnConfigList.add(selectionModel.getColumn());
    columnConfigList.add(new ColumnConfig<UserData, Long>(userProperties.id(), 20, "ID"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.username(), 100, "Username"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.firstName(), 100, "First Name"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.lastName(), 100, "Last Name"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.email(), 100, "Email"));
    columnConfigList.add(new ColumnConfig<UserData, UserRoleTypes>(userProperties.role(), 100, "Role"));

    ColumnModel<UserData> columnModel = new ColumnModel<UserData>(columnConfigList);
    ListStore<UserData> store = new ListStore<UserData>(userProperties.key());

    Grid<UserData> userDataGrid = new Grid<UserData>(store, columnModel);

    userDataGrid.setLoadMask(true);

    userDataGrid.setHideHeaders(false);
    userDataGrid.getView().setAutoFill(true);
    userDataGrid.setSelectionModel(selectionModel);

    userDataGrid.getView().setViewConfig(new GridViewConfig<UserData>() {
      @Override
      public String getColStyle(UserData model, ValueProvider<? super UserData, ?> valueProvider, int rowIndex,
          int colIndex) {
        log.info("row: " + rowIndex + " col: " + colIndex);
        log.info("role: " + model.getRole());
        if (model.getRole() == UserRoleTypes.PROFESSOR) {
          log.info("id: " + model.getId() + " | role: " + model.getRole());
          return "adminRoleBackground";
        } else
          return null;
      }

      @Override
      public String getRowStyle(UserData model, int rowIndex) {
        return "adminRoleBackground";
      }
    });
    return userDataGrid;
  }

  @Override
  public void setState(ManageUsersController.UserViewState state) {
    if (this.state == state)
      return;
    this.state = state;
    switch (state) {
    case ADD:
      addButton.setEnabled(true);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      downloadUsersButton.setEnabled(false);
      setEnableFields(true);
      break;
    case EDIT:
      addButton.setEnabled(false);
      editButton.setEnabled(true);
      deleteButton.setEnabled(true);
      downloadUsersButton.setEnabled(true);
      setEnableFields(true);
      break;
    case ADDING:
      addButton.setEnabled(false);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      setEnableFields(true);
      break;
    case DOWNLOAD:
      addButton.setEnabled(false);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      downloadUsersButton.setEnabled(true);
      clearFields();
      setEnableFields(false);
      break;
    case NONE:
      addButton.setEnabled(false);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      downloadUsersButton.setEnabled(false);
//      setEnableFields(false);
      clearFields();
      break;
    }
  }

  @Override
  public void loadUserData(UserData userData) {
    userNameField.setText(userData.getUsername());
    firstNameField.setText(userData.getFirstName());
    lastNameField.setText(userData.getLastName());
    emailField.setText(userData.getEmail());
    roleComboBox.setValue(userData.getRole());
  }

  @Override
  public void clearFields() {
    userNameField.setText("");
    firstNameField.setText("");
    lastNameField.setText("");
    emailField.setText("");
    roleComboBox.setValue(defaultRole);
  }

  private void setEnableFields(boolean value) {
    userNameField.setEnabled(value);
    firstNameField.setEnabled(value);
    lastNameField.setEnabled(value);
    emailField.setEnabled(value);
    roleComboBox.setEnabled(value);
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
  public TextButton getAddButton() {
    return addButton;
  }

  @Override
  public TextButton getEditButton() {
    return editButton;
  }

  @Override
  public TextButton getDeleteButton() {
    return deleteButton;
  }

  @Override
  public TextField getUserNameField() {
    return userNameField;
  }

  @Override
  public TextField getFirstNameField() {
    return firstNameField;
  }

  @Override
  public TextField getLastNameField() {
    return lastNameField;
  }

  @Override
  public TextField getEmailField() {
    return emailField;
  }

  @Override
  public SimpleComboBox<UserRoleTypes> getRoleCombo() {
    return roleComboBox;
  }

  @Override
  public Grid<UserData> getGrid() {
    return userDataGridView;
  }

  @Override
  public TextButton getDownloadUsersButton() {
    return downloadUsersButton;
  }

  @Override
  public TextButton getUploadUsersButton() {
    return uploadUsersButton;
  }

  @Override
  public FormPanel getFileFormPanel() {
    return fileFormPanel;
  }

  @Override
  public FileUpload getFileUpload() {
    return fileUpload;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ManageUsersView implements ManageUsersController.IManageUsersView {

  private static UserDataProperties userProperties = GWT.create(UserDataProperties.class);
  private final ListStore<UserData> listStore;
  private final PagingLoader<PagingLoadConfig, PagingLoadResult<UserData>> loader;
  private final PagingToolBar toolBar;

  private BorderLayoutContainer mainContainer;
  private TextButton addButton, editButton, deleteButton;
  private Grid<UserData> userDataGridView;

  private ManageUsersController.UserViewState state = ManageUsersController.UserViewState.NONE;
  private TextField firstNameField;
  private TextField lastNameField;
  private TextField emailField;

  public ManageUsersView(ListStore<UserData> listStore,
      PagingLoader<PagingLoadConfig, PagingLoadResult<UserData>> loader, PagingToolBar toolBar) {
    this.listStore = listStore;
    this.loader = loader;
    this.toolBar = toolBar;
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    addButton = new TextButton("Add", ELearningController.ICONS.add());
    editButton = new TextButton("Edit", ELearningController.ICONS.edit());
    deleteButton = new TextButton("Delete", ELearningController.ICONS.delete());
    firstNameField = new TextField();
    lastNameField = new TextField();
    emailField = new TextField();
    CenterLayoutContainer formContainer = new CenterLayoutContainer();
    VerticalLayoutContainer formPanel = new VerticalLayoutContainer();
    BorderLayoutContainer gridContainer = new BorderLayoutContainer();
    HBoxLayoutContainer buttonsContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    userDataGridView = createGrid();

    BoxLayoutContainer.BoxLayoutData hBoxLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(5));
    hBoxLayoutData.setFlex(1);
    buttonsContainer.add(addButton, hBoxLayoutData);
    buttonsContainer.add(editButton, hBoxLayoutData);
    buttonsContainer.add(deleteButton, hBoxLayoutData);
    buttonsContainer.setStyleName("buttonsBar");

    VerticalLayoutContainer.VerticalLayoutData verticalLayoutData = new VerticalLayoutContainer.VerticalLayoutData(1, -1);
    formPanel.add(new FieldLabel(firstNameField, "First Name"), verticalLayoutData);
    formPanel.add(new FieldLabel(lastNameField, "Last Name"), verticalLayoutData);
    formPanel.add(new FieldLabel(emailField, "Email"), verticalLayoutData);
    formPanel.add(buttonsContainer, verticalLayoutData);

    formContainer.add(formPanel);
    formContainer.setStyleName("whiteBackground");

    gridContainer.setSouthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    gridContainer.setCenterWidget(userDataGridView);

    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(.6);
    layoutData.setSplit(true);
    layoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setWestWidget(gridContainer, layoutData);
    mainContainer.setCenterWidget(formContainer);

    setState(state);
  }

  private Grid<UserData> createGrid() {
    IdentityValueProvider<UserData> identityValueProvider = new IdentityValueProvider<UserData>("sm");
    CheckBoxSelectionModel<UserData> selectionModel = new CheckBoxSelectionModel<UserData>(identityValueProvider);
    selectionModel.setSelectionMode(Style.SelectionMode.SINGLE);

    List<ColumnConfig<UserData, ?>> columnConfigList = new ArrayList<ColumnConfig<UserData, ?>>();

    columnConfigList.add(selectionModel.getColumn());
    columnConfigList.add(new ColumnConfig<UserData, Long>(userProperties.id(), 20, "ID"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.firstName(), 100, "First Name"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.lastName(), 100, "Last Name"));
    columnConfigList.add(new ColumnConfig<UserData, String>(userProperties.email(), 100, "Email"));

    ColumnModel<UserData> columnModel = new ColumnModel<UserData>(columnConfigList);
    ListStore<UserData> store = new ListStore<UserData>(userProperties.key());

    /* ADD TEST DATA */
    store.add(new UserData(0,"username", "", "firstName", "lastName", "email@admin"));
    store.add(new UserData(1,"username1", "", "firstName1", "lastName1", "email1@admin"));
    store.add(new UserData(2,"test", "", "test", "test", "test@admin"));
    store.add(new UserData(3,"test1", "", "test1", "test1", "test1@admin"));
    store.add(new UserData(4, "test2", "", "test2", "test2", "test2@admin"));

    Grid<UserData> userDataGrid = new Grid<UserData>(store, columnModel);

    userDataGrid.setLoadMask(true);
    userDataGrid.setLoader(loader);

    userDataGrid.setHideHeaders(false);
    userDataGrid.getView().setAutoFill(true);
    userDataGrid.setSelectionModel(selectionModel);

    return userDataGrid;
  }

  @Override
  public void setState(ManageUsersController.UserViewState state) {
    switch (state) {
    case ADD:
      addButton.setEnabled(true);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      break;
    case EDIT:
      addButton.setEnabled(false);
      editButton.setEnabled(true);
      deleteButton.setEnabled(true);
      break;
    case ADDING:
      addButton.setEnabled(false);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      break;
    case NONE:
      addButton.setEnabled(false);
      editButton.setEnabled(false);
      deleteButton.setEnabled(false);
      clearFields();
      break;
    }
  }

  @Override
  public void loadUserData(UserData userData) {
    firstNameField.setText(userData.getFirstName());
    lastNameField.setText(userData.getLastName());
    emailField.setText(userData.getEmail());
  }

  private void clearFields() {
    firstNameField.setText("");
    lastNameField.setText("");
    emailField.setText("");
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
  public Grid<UserData> getGrid() {
    return userDataGridView;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

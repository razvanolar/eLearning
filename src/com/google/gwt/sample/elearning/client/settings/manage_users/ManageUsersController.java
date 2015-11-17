package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.client.services.UserService;
import com.google.gwt.sample.elearning.client.services.UserServiceAsync;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import java.util.List;
import java.util.logging.Logger;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ManageUsersController {

  private ListStore<UserData> listStore;
  private PagingToolBar toolBar;

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

  private UserServiceAsync userService = GWT.create(UserService.class);
  private UserDataProperties userProperties = GWT.create(UserDataProperties.class);
  private RpcProxy<PagingLoadConfig, PagingLoadResult<UserData>> proxy;
  private PagingLoader<PagingLoadConfig, PagingLoadResult<UserData>> loader;

  private static Logger log = Logger.getLogger(ManageUsersController.class.getName());

  public ManageUsersController() {
    log.info("ManageUsersController - constructor");
    createProxyAndLoaders();
  }

  public void bind() {
    addListeneres();
    loadUsers();
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

  public void loadUsers() {
    loader.load();
  }

  private void createProxyAndLoaders() {
    listStore = new ListStore<UserData>(userProperties.key());

    proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<UserData>>() {
      @Override
      public void load(PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<UserData>> callback) {
        userService.getAllUsers(loadConfig, new AsyncCallback<PagingLoadResult<UserData>>() {
          public void onFailure(Throwable caught) {
            (new MessageBox("Error", "load failed")).show();
          }

          public void onSuccess(PagingLoadResult<UserData> result) {
            (new MessageBox("Info", "loaded")).show();
          }
        });
      }
    };

    loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<UserData>>(proxy);

    loader.setRemoteSort(true);
    loader.addLoadHandler(
        new LoadResultListStoreBinding<PagingLoadConfig, UserData, PagingLoadResult<UserData>>(listStore));

    toolBar = new PagingToolBar(50);
    toolBar.bind(loader);

    log.info("created");
  }

  private UserData getSelectedUser() {
    if (view.getGrid() != null && view.getGrid().getSelectionModel() != null)
      return view.getGrid().getSelectionModel().getSelectedItem();
    return null;
  }

  private boolean isUserSelected() {
    return getSelectedUser() != null;
  }

  public void setView(IManageUsersView view) {
    this.view = view;
  }

  public ListStore<UserData> getListStore() {
    return listStore;
  }

  public PagingToolBar getToolBar() {
    return toolBar;
  }

  public PagingLoader<PagingLoadConfig, PagingLoadResult<UserData>> getLoader() {
    return loader;
  }
}

package com.google.gwt.sample.elearning.client.settings.manage_users;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ManageUsersView implements ManageUsersController.IManageUsersView {

  private static UserDataProperties userProperties = GWT.create(UserDataProperties.class);

  private BorderLayoutContainer mainContainer;
  private Grid<UserData> userDataGridView;

  public ManageUsersView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    userDataGridView = createGrid();

    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(.5);
    layoutData.setMargins(new Margins(10));
    mainContainer.setWestWidget(userDataGridView, layoutData);

    TreeStore<UserData> treeStore = new TreeStore<UserData>(userProperties.key());
    Tree<UserData, String> tree = new Tree<UserData, String>(treeStore, userProperties.firstName());
    tree.setIconProvider(new IconProvider<UserData>() {
      @Override
      public ImageResource getIcon(UserData model) {
        return ELearningController.ICONS.chat();
      }
    });

    UserData root = new UserData(0, "Root");
    treeStore.add(root);
    treeStore.add(root, new UserData(1, "Nume 1"));

    mainContainer.setEastWidget(tree);
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

    Grid<UserData> userDataGrid = new Grid<UserData>(store, columnModel);

    userDataGrid.setHideHeaders(false);
    userDataGrid.getView().setAutoFill(true);
    userDataGrid.setSelectionModel(selectionModel);

    return userDataGrid;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

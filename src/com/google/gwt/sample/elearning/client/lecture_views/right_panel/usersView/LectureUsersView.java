package com.google.gwt.sample.elearning.client.lecture_views.right_panel.usersView;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;

/**
 *
 * Created by Cristi on 1/3/2016.
 */
public class LectureUsersView implements LectureUsersController.ILectureUsersView {
  private BorderLayoutContainer mainContainer;
  private CellList<UserData> cellList;
  private SingleSelectionModel<UserData> selectionModel;

  public LectureUsersView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();

    ModelKeyProvider<UserData> keyProvider = new ModelKeyProvider<UserData>() {
      @Override
      public String getKey(UserData item) {
        return (item == null ? null : item.getId() + "");
      }
    };

    cellList = new CellList<UserData>(new AbstractCell<UserData>() {
      @Override
      public void render(Context context, UserData value, SafeHtmlBuilder sb) {
        if (value != null) {
          sb.appendHtmlConstant("<div style='padding: 3px 0px 3px 5px; padding-bottom: 3px'>" +
                  "<img src='" + value.getImagePath() + "' width='20px' hight='20px' align='top'/>  "
                  + value.getFirstName() + " " + value.getLastName() + "</div>");
        }
      }
    }, keyProvider);

    selectionModel = new SingleSelectionModel<UserData>(keyProvider);
    cellList.setSelectionModel(selectionModel);
    ScrollPanel scrollPanel = new ScrollPanel(cellList);
    mainContainer.setCenterWidget(scrollPanel, new MarginData(0, 0, 0, 0));
  }

  @Override
  public CellList<UserData> getCellList() {
    return cellList;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public SingleSelectionModel<UserData> getSelectionModel() {
    return selectionModel;
  }

  @Override
  public void mask() {
    mainContainer.mask();
  }

  @Override
  public void unmask() {
    mainContainer.unmask();
  }
}

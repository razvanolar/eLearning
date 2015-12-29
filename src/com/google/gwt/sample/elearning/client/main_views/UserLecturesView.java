package com.google.gwt.sample.elearning.client.main_views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.LectureDataProperties;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 29.12.2015.
 */
public class UserLecturesView implements UserLecturesController.IUserLecturesView {

  private LectureDataProperties lectureProperties = GWT.create(LectureDataProperties.class);
  private BorderLayoutContainer mainContainer;
  private Grid<Lecture> unenrolledGrid;

  private TextField enrollmentKeyField;
  private TextButton sendKeyButton;

  public UserLecturesView() {
    initGUI();
  }

  private void initGUI() {
    unenrolledGrid = createUnenrolledGrid();
    mainContainer = new BorderLayoutContainer();
    enrollmentKeyField = new TextField();
    sendKeyButton = new TextButton("Send", ELearningController.ICONS.send());
    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
    VBoxLayoutContainer formPanel = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.LEFT);
    HBoxLayoutContainer fieldsPanel = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);

    fieldsPanel.add(enrollmentKeyField);
    fieldsPanel.add(sendKeyButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 0, 5)));

    formPanel.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    formPanel.add(new Label("Enrolment key :"));
    formPanel.add(fieldsPanel);

    centerLayoutContainer.add(formPanel);
    mainContainer.setStyleName("buttonsToolbar");
    mainContainer.setCenterWidget(unenrolledGrid);
    mainContainer.setSouthWidget(centerLayoutContainer, new BorderLayoutContainer.BorderLayoutData(50));
    mainContainer.setHeight(300);
    mainContainer.setWidth(250);

    setState(UserLecturesController.UsersLecturesViewState.NONE);
  }

  public void setState(UserLecturesController.UsersLecturesViewState state) {
    switch (state) {
      case SELECTED:
        enrollmentKeyField.setEnabled(true);
        sendKeyButton.setEnabled(true);
        break;
      case NONE:
        enrollmentKeyField.setText("");
        enrollmentKeyField.setEnabled(false);
        sendKeyButton.setEnabled(false);
        break;
    }
  }

  private Grid<Lecture> createUnenrolledGrid() {
    ListStore<Lecture> store = new ListStore<Lecture>(lectureProperties.key());

    List<ColumnConfig<Lecture, ?>> columns = new ArrayList<ColumnConfig<Lecture, ?>>();
    columns.add(new ColumnConfig<Lecture, String>(lectureProperties.lectureName(), 250, "Unenrolled Lecture Name"));

    Grid<Lecture> grid = new Grid<Lecture>(store, new ColumnModel<Lecture>(columns));
    return grid;
  }

  public void mask() {
    mainContainer.mask();
  }

  public void unmask() {
    mainContainer.unmask();
  }

  public Grid<Lecture> getUnenrolledGrid() {
    return unenrolledGrid;
  }

  public TextField getEnrollmentKeyField() {
    return enrollmentKeyField;
  }

  public TextButton getSendKeyButton() {
    return sendKeyButton;
  }

  public Widget asWidget() {
    return mainContainer;
  }
}

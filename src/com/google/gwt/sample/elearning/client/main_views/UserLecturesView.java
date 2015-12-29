package com.google.gwt.sample.elearning.client.main_views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.settings.manage_lectures.LectureDataProperties;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridViewConfig;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 29.12.2015.
 */
public class UserLecturesView implements UserLecturesController.IUserLecturesView {

  private LectureDataProperties lectureProperties = GWT.create(LectureDataProperties.class);
  private BorderLayoutContainer mainContainer;
  private Grid<Lecture> enrolledGrid;
  private Grid<Lecture> unenrolledGrid;

  private TextField enrollmentKeyField;
  private TextButton sendKeyButton;

  public UserLecturesView() {
    initGUI();
  }

  private void initGUI() {
    enrolledGrid = createEnrolledGrid();
    unenrolledGrid = createUnenrolledGrid();
    mainContainer = new BorderLayoutContainer();
    enrollmentKeyField = new TextField();
    sendKeyButton = new TextButton("Send");
    BorderLayoutContainer gridsContainer = new BorderLayoutContainer();
    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
    VBoxLayoutContainer formPanel = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.LEFT);
    HBoxLayoutContainer fieldsPanel = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);

    gridsContainer.setNorthWidget(enrolledGrid, new BorderLayoutContainer.BorderLayoutData(150));
    gridsContainer.setSouthWidget(unenrolledGrid, new BorderLayoutContainer.BorderLayoutData(200));

    fieldsPanel.add(enrollmentKeyField);
    fieldsPanel.add(sendKeyButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 0, 5)));

    formPanel.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    formPanel.add(new Label("Enrolment key :"));
    formPanel.add(fieldsPanel);

    centerLayoutContainer.add(formPanel);
    mainContainer.setStyleName("buttonsToolbar");
    mainContainer.setCenterWidget(gridsContainer);
    mainContainer.setSouthWidget(centerLayoutContainer, new BorderLayoutContainer.BorderLayoutData(50));
  }

  private Grid<Lecture> createEnrolledGrid() {
    ListStore<Lecture> store = new ListStore<Lecture>(lectureProperties.key());

    List<ColumnConfig<Lecture, ?>> columns = new ArrayList<ColumnConfig<Lecture, ?>>();
    columns.add(new ColumnConfig<Lecture, String>(lectureProperties.lectureName(), 250, "Enrolled Lecture Name"));

    Grid<Lecture> grid = new Grid<Lecture>(store, new ColumnModel<Lecture>(columns));
    grid.getView().setViewConfig(new GridViewConfig<Lecture>() {
      @Override
      public String getColStyle(Lecture model, ValueProvider<? super Lecture, ?> valueProvider, int rowIndex, int colIndex) {
        return "enrolledLectureBackground";
      }

      @Override
      public String getRowStyle(Lecture model, int rowIndex) {
        return "enrolledLectureBackground";
      }
    });
    return grid;
  }

  private Grid<Lecture> createUnenrolledGrid() {
    ListStore<Lecture> store = new ListStore<Lecture>(lectureProperties.key());

    List<ColumnConfig<Lecture, ?>> columns = new ArrayList<ColumnConfig<Lecture, ?>>();
    columns.add(new ColumnConfig<Lecture, String>(lectureProperties.lectureName(), 250, "Unenrolled Lecture Name"));

    Grid<Lecture> grid = new Grid<Lecture>(store, new ColumnModel<Lecture>(columns));
    return grid;
  }

  public Grid<Lecture> getEnrolledGrid() {
    return enrolledGrid;
  }

  public Grid<Lecture> getUnenrolledGrid() {
    return unenrolledGrid;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

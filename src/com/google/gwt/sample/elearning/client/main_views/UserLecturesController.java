package com.google.gwt.sample.elearning.client.main_views;

import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.FilteredLecturesData;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.grid.Grid;

/**
 *
 * Created by razvanolar on 29.12.2015.
 */
public class UserLecturesController {

  public interface IUserLecturesView {
    Grid<Lecture> getEnrolledGrid();
    Grid<Lecture> getUnenrolledGrid();
    Widget asWidget();
  }

  private LectureServiceAsync lectureService;
  private IUserLecturesView view;
  private FilteredLecturesData filteredLecturesData;

  public UserLecturesController(IUserLecturesView view, LectureServiceAsync lectureService,
                                FilteredLecturesData filteredLecturesData) {
    this.view = view;
    this.lectureService = lectureService;
    this.filteredLecturesData = filteredLecturesData;
  }

  public void bind() {
    loadGrid();
    addListeners();
  }

  private void addListeners() {

  }

  private void loadGrid() {
    view.getEnrolledGrid().getStore().clear();
    view.getEnrolledGrid().getStore().addAll(filteredLecturesData.getEnrolledLectures());

    view.getUnenrolledGrid().getStore().clear();
    view.getUnenrolledGrid().getStore().addAll(filteredLecturesData.getUnenrolledLectures());
  }
}

package com.google.gwt.sample.elearning.client.main_views;

import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 29.12.2015.
 */
public class UserLecturesController {

  public interface IUserLecturesView extends MaskableView {
    Grid<Lecture> getUnenrolledGrid();
    Widget asWidget();
  }

  private Logger log = Logger.getLogger(UserLecturesController.class.getName());
  private LectureServiceAsync lectureService;
  private IUserLecturesView view;
  private List<Lecture> lectures;

  public UserLecturesController(IUserLecturesView view, LectureServiceAsync lectureService) {
    this.view = view;
    this.lectureService = lectureService;
  }

  public void bind() {
    loadGrid();
    addListeners();
  }

  private void addListeners() {

  }

  public void setLecturesList(List<Lecture> lectures) {
    this.lectures = lectures;
    loadGrid();
  }

  public void maskView() {
    view.mask();
  }

  public void unmaskView() {
    view.unmask();
  }

  private void loadGrid() {
    if (lectures != null) {
      log.info(lectures.toString());
      view.getUnenrolledGrid().getStore().clear();
      view.getUnenrolledGrid().getStore().addAll(lectures);
    }
  }
}

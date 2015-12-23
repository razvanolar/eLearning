package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_homework;

import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.logging.Logger;

/**
 * Created by Cristi on 12/23/2015.
 */
public class CreateHomeworkController {

  public interface ICreateHomeworkView extends MaskableView{
    TextButton getSaveButton();
    TextField getTitleField();
    TextField getScoreField();
    TextArea getDescriptionTextArea();
    Widget asWidget();
  }
  private ICreateHomeworkView view;

  private LectureServiceAsync lectureService;
  private final ManageLecturesHomeworkController parentController;
  private Window window;
  private HomeworkData homeworkData;
  private Logger logger = Logger.getLogger(CreateHomeworkController.class.getName());

  public CreateHomeworkController(ICreateHomeworkView view, LectureServiceAsync lectureService,
      ManageLecturesHomeworkController parentController) {
    this.view = view;
    this.lectureService = lectureService;
    this.parentController = parentController;
  }

  public CreateHomeworkController(ICreateHomeworkView view, LectureServiceAsync lectureService,
      ManageLecturesHomeworkController parentController, HomeworkData homeworkData) {
    this(view, lectureService, parentController);
    this.homeworkData = homeworkData;
    if (homeworkData != null) {
      view.getTitleField().setText(homeworkData.getTitle());
      view.getScoreField().setText(homeworkData.getScore() + "");
      view.getDescriptionTextArea().setText(homeworkData.getText());
    }
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {
    view.getSaveButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        onSaveSelection();
      }
    });
  }

  private void onSaveSelection() {
    if (TextInputValidator.isEmptyString(view.getTitleField().getText())) {
      new MessageBox("Error", "Please provide a title").show();
      return;
    }
    if (TextInputValidator.isEmptyString(view.getScoreField().getText())) {
      new MessageBox("Error", "Score field is empty").show();
      return;
    }
    int score = 0;
    try {
      score = Integer.parseInt(view.getScoreField().getText());
    } catch (Exception ex) {
      new MessageBox("Error", "Score field is not a number").show();
      return;
    }
    if (TextInputValidator.isEmptyString(view.getDescriptionTextArea().getText())) {
      new MessageBox("Error", "Description area is empty").show();
      return;
    }

    if (homeworkData == null) {
      homeworkData = new HomeworkData(-1, view.getTitleField().getText(), score,
          view.getDescriptionTextArea().getText());
      lectureService.saveHomeworkData(parentController.getSelectedLecture().getId(), homeworkData, new ELearningAsyncCallBack<Void>(view, logger) {
        @Override
        public void onSuccess(Void result) {
          if(window != null)
            window.hide();
        }
      });
    } else {
      homeworkData.setTitle(view.getTitleField().getText());
      homeworkData.setScore(score);
      homeworkData.setText(view.getDescriptionTextArea().getText());
      lectureService.updateHomeworkData(parentController.getSelectedLecture().getId(), homeworkData, new ELearningAsyncCallBack<Void>(view, logger) {
        @Override
        public void onSuccess(Void result) {
          if(window != null)
            window.hide();
        }
      });
    }
  }

  public void setContentWindow(MasterWindow contentWindow) {
    this.window = contentWindow;
  }
}

package com.google.gwt.sample.elearning.client.lecture_views.left_panel.homeworks_view.resolve_homework;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextUtil;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.HomeworkData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;

import java.util.logging.Logger;

/**
 * Created by Cristi on 1/16/2016.
 */
public class ResolveHomeworkController {
  public interface IResolveHomeworkView extends MaskableView{
    Widget asWidget();
    HtmlLayoutContainer getHtmlLayoutContainer();
    TextArea getTextArea();
    TextButton getResolveButton();
  }

  IResolveHomeworkView view;
  HomeworkData homeworkData;
  LectureServiceAsync lectureService;
  Window window;
  Logger logger = Logger.getLogger(ResolveHomeworkController.class.getName());
  public ResolveHomeworkController(IResolveHomeworkView view, HomeworkData homework, LectureServiceAsync lectureService) {
    this.view = view;
    this.homeworkData = homework;
    this.lectureService = lectureService;
  }

  public void bind(){
    view.getHtmlLayoutContainer().setHTML("<div style='padding: 3px 5px 3px 5px;'><p>&nbsp;&nbsp;" + homeworkData.getText() + "</p></div>");
    addListeners();
  }

  private void addListeners() {
    view.getResolveButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnResolveButton();
      }
    });
  }

  private void doOnResolveButton() {
    String solution = view.getTextArea().getValue();
    if(TextUtil.isEmptyString(solution)){
      new MessageBox("Info", "Empty solution.").show();
      return;
    }
//    lectureService.addHomeworkSolution(ELearningController.getInstance().getCurrentLecture().getId(),
//        ELearningController.getInstance().getCurrentLecture().getProfessor().getId(),
//        ELearningController.getInstance().getCurrentUser().getId(), homeworkData.getTitle(), solution, new ELearningAsyncCallBack<Void>(view,logger) {
//          @Override
//          public void onSuccess(Void result) {
//            new MessageBox("Info", "Solution added.").show();
//            if(window != null)
//              window.hide();
//          }
//        });
  }

  public void setContentWindow(MasterWindow window) {
    this.window = window;
  }
}

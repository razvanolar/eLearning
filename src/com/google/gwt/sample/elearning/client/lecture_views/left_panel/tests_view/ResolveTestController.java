package com.google.gwt.sample.elearning.client.lecture_views.left_panel.tests_view;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.eLearningUtils.MaskableView;
import com.google.gwt.sample.elearning.shared.model.AnswerData;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;
import com.google.gwt.sample.elearning.shared.model.QuestionData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ResolveTestController {

  public enum IResolveTestArrowsState {
    LEFT, RIGHT, BOTH
  }

  public interface IResolveTestView extends MaskableView {
    TextButton getPreviousQuestionButton();
    TextButton getNextQuestionButton();
    TextButton getApplyButton();
    void setQuestion(String text);
    void setAnswers(List<CheckBox> answers);
    void setArrowState(IResolveTestArrowsState state);
    void displayScore(long score);
    Widget asWidget();
  }

  private IResolveTestView view;
  private LectureTestData testData;
  private List<QuestionData> questions;
  private Map<QuestionData, List<CheckBox>> questionDataMap;
  private IResolveTestListener testListener;
  private int currentIndex;
  private Logger log = Logger.getLogger(ResolveTestController.class.getName());

  public ResolveTestController(IResolveTestView view, LectureTestData testData, IResolveTestListener testListener) {
    this.view = view;
    this.testData = testData;
    this.testListener = testListener;
  }

  public void bind() {
    questions = testData.getQuestions();
    createAnswersBox();
    addListeners();
    displayQuestion();
    setArrowsState();
  }

  private void addListeners() {
    view.getPreviousQuestionButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        prev();
      }
    });

    view.getNextQuestionButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        next();
      }
    });

    view.getApplyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onApplyButton();
      }
    });
  }

  private void onApplyButton() {
    long userId = ELearningController.getInstance().getCurrentUser().getId();
    testListener.resolveTest(userId,testData, view);
  }

  private void displayQuestion() {
    if (currentIndex < questions.size() && currentIndex >= 0) {
      QuestionData questionData = questions.get(currentIndex);
      view.setQuestion(questionData.getQuestion());
      view.setAnswers(questionDataMap.get(questionData));
    }
  }

  private void next() {
    if (currentIndex >= 0 && currentIndex < questions.size() - 1) {
      currentIndex ++;
      displayQuestion();
    }
    setArrowsState();
  }

  private void prev() {
    if (currentIndex > 0 && currentIndex < questions.size()) {
      currentIndex --;
      displayQuestion();
    }
    setArrowsState();
  }

  private void setArrowsState() {
    if (currentIndex > 0 && currentIndex < questions.size() - 1)
      view.setArrowState(IResolveTestArrowsState.BOTH);
    else if (currentIndex == 0)
      view.setArrowState(IResolveTestArrowsState.RIGHT);
    else if (currentIndex == questions.size() - 1)
      view.setArrowState(IResolveTestArrowsState.LEFT);
  }

  private void createAnswersBox() {
    if (questionDataMap == null)
      questionDataMap = new HashMap<QuestionData, List<CheckBox>>();

    ChangeHandler handler = new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        AnswerData answerData = (AnswerData) checkBox.getLayoutData();
        answerData.setIsSelected(checkBox.getValue());
      }
    };

    for (QuestionData questionData : questions) {
      List<CheckBox> list = new ArrayList<CheckBox>();
      for (AnswerData answerData : questionData.getAnswers()) {
        CheckBox checkBox = new CheckBox();
        checkBox.setLayoutData(answerData);
        checkBox.addChangeHandler(handler);
        checkBox.setBoxLabel(answerData.getValue());
        list.add(checkBox);
      }
      questionDataMap.put(questionData, list);
    }
  }
}

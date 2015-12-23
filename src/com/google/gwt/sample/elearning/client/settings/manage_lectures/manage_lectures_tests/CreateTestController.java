package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_tests;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.client.eLearningUtils.ELearningAsyncCallBack;
import com.google.gwt.sample.elearning.client.eLearningUtils.TextInputValidator;
import com.google.gwt.sample.elearning.client.service.LectureService;
import com.google.gwt.sample.elearning.client.service.LectureServiceAsync;
import com.google.gwt.sample.elearning.shared.model.AnswerData;
import com.google.gwt.sample.elearning.shared.model.LectureTestData;
import com.google.gwt.sample.elearning.shared.model.QuestionData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by razvanolar on 29.11.2015.
 */
public class CreateTestController {

  public enum ButtonsBarViewState {
    ADD, EDIT, NONE
  }

  public enum NextPrevViewState {
    ONLY_NEXT, ONLY_PREV, BOTH, NONE
  }

  public enum DeleteButtonViewState {
    CAN_DELETE, NONE
  }

  public interface ICreateTestView {
    TextButton getAddAnswerButton();
    TextButton getEditAnswerButton();
    TextButton getDeleteAnswerButton();
    TextButton getNextButton();
    TextButton getPrevButton();
    TextButton getAddQuestionButton();
    TextButton getDeleteQuestionButton();
    TextButton getApplyButton();
    CheckBox getIsTrueCheckBox();
    TextField getAnswerField();
    TextField getQuestionField();
    TextField getScoreField();
    TextField getTitleField();
    Label getPagingLabel();
    Grid<AnswerData> getAnswersGrid();
    void setButtonsBarState(ButtonsBarViewState state);
    void setNextPrevViewState(NextPrevViewState state);
    void setDeleteButtonViewState(DeleteButtonViewState state);
    void clearFields();
    void clearAnswersFields();
    Widget asWidget();
  }

  private ICreateTestView view;
  private ListStore<AnswerData> store;
  private LectureTestData test;
  private List<QuestionData> questions;
  private int currentIndex;
  private long lectureId;
  private LectureServiceAsync lectureService = GWT.create(LectureService.class);
  private Window window;
  private ICreateTestListener listener;

  public CreateTestController(ICreateTestView view, long lectureId, ICreateTestListener listener) {
    this.view = view;
    questions = new ArrayList<QuestionData>();
    questions.add(new QuestionData());
    currentIndex = 0;
    this.lectureId = lectureId;
    test = new LectureTestData();
    this.listener = listener;
  }

  public CreateTestController(ICreateTestView view, LectureTestData test, long lectureId, ICreateTestListener listener) {
    this.view = view;
    questions = test.getQuestions();
    currentIndex = 0;
    this.test = test;
    this.lectureId = lectureId;
    this.listener = listener;
  }

  public void bind() {
    store = view.getAnswersGrid().getStore();
    updatePagingLabel();
    loadQuestion(questions.get(currentIndex));
    setBottomToolbarState();
    if (test != null) {
      view.getTitleField().setText(test.getName());
    }
    addListeners();
  }

  private void addListeners() {
    view.getAnswersGrid().getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<AnswerData>() {
      public void onSelectionChanged(SelectionChangedEvent<AnswerData> event) {
        List<AnswerData> selectedAnswers = event.getSelection();
        if (selectedAnswers == null || selectedAnswers.isEmpty()) {
          view.clearAnswersFields();
          view.setButtonsBarState(ButtonsBarViewState.NONE);
        } else {
          loadAnswer(selectedAnswers.get(0));
          view.setButtonsBarState(ButtonsBarViewState.EDIT);
        }
      }
    });

    view.getAddAnswerButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onAddAnswerSelection();
      }
    });

    view.getEditAnswerButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onEditAnswerSelection();
      }
    });

    view.getDeleteAnswerButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onDeleteAnswerSelection();
      }
    });

    view.getNextButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onNextButtonSelection();
      }
    });

    view.getPrevButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onPrevButtonSelection();
      }
    });

    view.getAddQuestionButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onAddQuestionSelection();
      }
    });

    view.getDeleteQuestionButton().addSelectHandler(new SelectEvent.SelectHandler() {
      public void onSelect(SelectEvent event) {
        onDeleteQuestionSelection();
      }
    });

    view.getQuestionField().addKeyUpHandler(new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        updateCurrentQuestionValue(view.getQuestionField().getText());
      }
    });

    view.getAnswerField().addKeyUpHandler(new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        if (isAnswerSelected()) {
          view.setButtonsBarState(ButtonsBarViewState.EDIT);
        } else {
          if (!TextInputValidator.isEmptyString(view.getAnswerField().getText()))
            view.setButtonsBarState(ButtonsBarViewState.ADD);
          else
            view.setButtonsBarState(ButtonsBarViewState.NONE);
        }
      }
    });

    view.getScoreField().addValidator(new Validator<String>() {
      public List<EditorError> validate(Editor<String> editor, String value) {
        if (value != null && !value.matches("[1-9][0-9]*")) {
          List<EditorError> result = new ArrayList<EditorError>();
          result.add(new DefaultEditorError(editor, "Score value is not a positive integer.", value));
          return result;
        }
        return null;
      }
    });

    view.getScoreField().addKeyUpHandler(new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        if (view.getScoreField().isValid())
          updateCurrentQuestionScore(Long.parseLong(view.getScoreField().getText()));
      }
    });

    view.getApplyButton().addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        doOnApplyButtonSelection();
      }
    });
  }

  private void doOnApplyButtonSelection() {
    if (view.getTitleField().getText() == null || view.getTitleField().getText().isEmpty()) {
      (new MessageBox("Error", "Please provide a title")).show();
      return;
    }

    LectureTestData lectureTestData = new LectureTestData(lectureId, view.getTitleField().getText(), 0, questions);
    lectureService.createTest(lectureTestData, new AsyncCallback<Void>() {
      public void onFailure(Throwable caught) {
        new MessageBox("Error",caught.getMessage()).show();
      }

      public void onSuccess(Void result) {
        if (window != null)
          window.hide();
        if (listener != null)
          listener.testCreated();
      }
    });
  }

  private void onAddAnswerSelection() {
    String text = view.getAnswerField().getText();
    if (!TextInputValidator.isEmptyString(text)) {
      if (!checkIfAnswerExists(text)) {
        questions.get(currentIndex).addAnswer(new AnswerData(text, view.getIsTrueCheckBox().getValue()));
        loadQuestion(questions.get(currentIndex));
        view.clearAnswersFields();
        view.setButtonsBarState(ButtonsBarViewState.NONE);
      } else {
        (new MessageBox("Error", "An answer with the specified value already exists")).show();
      }
    } else {
      (new MessageBox("Error", "Answer value can not be empty")).show();
    }
  }

  private void onEditAnswerSelection() {
    String text = view.getAnswerField().getText();
    if (!TextInputValidator.isEmptyString(text)) {
      AnswerData selectedAnswer = getSelectedAnswer();
      if (selectedAnswer == null) {
        (new MessageBox("Error", "Cannot edit the specified answer of null!")).show();
        return;
      }

      /* the value was changed */
      if (!text.equals(selectedAnswer.getValue())) {
        if (!checkIfAnswerExists(text)) {
          selectedAnswer.setIsTrue(view.getIsTrueCheckBox().getValue());
          selectedAnswer.setValue(text);
          view.getAnswersGrid().getView().refresh(true);
          view.getAnswersGrid().getView().refresh(true);
        } else {
          (new MessageBox("Error", "An answer with the specified value already exists!")).show();
        }
      }
      /* the value remains the same */
      else {
        selectedAnswer.setIsTrue(view.getIsTrueCheckBox().getValue());
        view.getAnswersGrid().getView().refresh(true);
      }

    } else {
      (new MessageBox("Error", "Answer value can not be empty")).show();
    }
  }

  private void onDeleteAnswerSelection() {
    AnswerData selectedAnswer = getSelectedAnswer();
    if (selectedAnswer != null) {
      questions.get(currentIndex).getAnswers().remove(selectedAnswer);
      store.remove(selectedAnswer);
      view.setButtonsBarState(ButtonsBarViewState.NONE);
    }
  }

  private void onNextButtonSelection() {
    if (currentIndex < questions.size() - 1) {
      currentIndex ++;
      loadQuestion(questions.get(currentIndex));
      setBottomToolbarState();
      updatePagingLabel();
    }
  }

  private void onPrevButtonSelection() {
    if (currentIndex > 0) {
      currentIndex --;
      loadQuestion(questions.get(currentIndex));
      setBottomToolbarState();
      updatePagingLabel();
    }
  }

  private void onAddQuestionSelection() {
    currentIndex ++;
    questions.add(currentIndex, new QuestionData());
    view.clearFields();
    setBottomToolbarState();
    updatePagingLabel();
  }

  private void onDeleteQuestionSelection() {
    if (questions.size() > 1) {
      questions.remove(currentIndex);
      currentIndex --;
      if (currentIndex < 0)
        currentIndex = 0;
      loadQuestion(questions.get(currentIndex));
      setBottomToolbarState();
      updatePagingLabel();
    }
  }

  private void loadQuestion(QuestionData questionData) {
    store.clear();
    if (questionData.getAnswers() != null)
      store.addAll(questionData.getAnswers());
    view.getQuestionField().setText(questionData.getQuestion());
    view.getScoreField().setText(questionData.getScore() + "");
  }

  private void loadAnswer(AnswerData answer) {
    if (answer != null) {
      view.getAnswerField().setText(answer.getValue());
      view.getIsTrueCheckBox().setValue(answer.isTrue());
    }
  }

  private void setBottomToolbarState() {
    int size = questions.size();
    if (size == 1) {
      view.setNextPrevViewState(NextPrevViewState.NONE);
      view.setDeleteButtonViewState(DeleteButtonViewState.NONE);
      return;
    }

    if (size > 1) {
      view.setDeleteButtonViewState(DeleteButtonViewState.CAN_DELETE);
      if (currentIndex == 0)
        view.setNextPrevViewState(NextPrevViewState.ONLY_NEXT);
      else if (currentIndex == size - 1)
        view.setNextPrevViewState(NextPrevViewState.ONLY_PREV);
      else
        view.setNextPrevViewState(NextPrevViewState.BOTH);
    }
  }

  private void updateCurrentQuestionValue(String question) {
    questions.get(currentIndex).setQuestion(question);
  }

  private void updateCurrentQuestionScore(long score) {
    questions.get(currentIndex).setScore(score);
  }

  private void updatePagingLabel() {
    view.getPagingLabel().setText((currentIndex + 1) + "/" + questions.size());
  }

  private boolean isAnswerSelected() {
    return view.getAnswersGrid().getSelectionModel() != null &&
            !view.getAnswersGrid().getSelectionModel().getSelectedItems().isEmpty();
  }

  private boolean checkIfAnswerExists(String value) {
    List<AnswerData> answers = store.getAll();
    if (answers != null && !answers.isEmpty())
      for (AnswerData answer : answers)
        if (value.equals(answer.getValue()))
          return true;
    return false;
  }

  private AnswerData getSelectedAnswer() {
    if (view.getAnswersGrid().getSelectionModel() != null) {
      List<AnswerData> selectedItems = view.getAnswersGrid().getSelectionModel().getSelectedItems();
      if (selectedItems != null && !selectedItems.isEmpty())
        return selectedItems.get(0);
    }
    return null;
  }

  public void setContentWindow(Window window) {
    this.window = window;
  }
}

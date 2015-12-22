package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_tests;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.MasterWindow;
import com.google.gwt.sample.elearning.shared.model.AnswerData;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Created by razvanolar on 29.11.2015.
 */
public class CreateTestView implements CreateTestController.ICreateTestView {

  private static AnswerDataProperties answerDataProperties = GWT.create(AnswerDataProperties.class);

  private BorderLayoutContainer mainContainer;
  private TextButton applyButton;
  private TextButton prevTextButton;
  private TextButton nextTextButton;
  private TextButton addAnswerButton;
  private TextButton editAnswerButton;
  private TextButton deleteAnswerButton;
  private TextButton addQuestionButton;
  private TextButton deleteQuestionButton;
  private TextField titleField;
  private TextField questionField;
  private TextField answerField;
  private TextField scoreField;
  private Label pagingLabel;
  private CheckBox isTrueCheckBox;
  private Grid<AnswerData> answersGrid;

  public CreateTestView() {
    initGUI();
  }

  private void initGUI() {
    applyButton = new TextButton("Create", ELearningController.ICONS.apply());
    prevTextButton = new TextButton("", ELearningController.ICONS.arrowleft());
    nextTextButton = new TextButton("", ELearningController.ICONS.arrowright());
    addAnswerButton = new TextButton("Add", ELearningController.ICONS.add());
    editAnswerButton = new TextButton("Edit", ELearningController.ICONS.edit());
    deleteAnswerButton = new TextButton("Delete", ELearningController.ICONS.delete());
    addQuestionButton = new TextButton("New Question", ELearningController.ICONS.addquestion());
    deleteQuestionButton = new TextButton("Delete Question", ELearningController.ICONS.deletequestion());
    titleField = new TextField();
    questionField = new TextField();
    answerField = new TextField();
    scoreField = new TextField();
    pagingLabel = new Label("");
    answersGrid = createAnswersGrid();
    isTrueCheckBox = new CheckBox();
    ToolBar topToolBar = new ToolBar();
    ToolBar bottomToolBar = new ToolBar();
    BorderLayoutContainer questionsContainer = new BorderLayoutContainer();
    CenterLayoutContainer leftPanel = new CenterLayoutContainer();
    VerticalLayoutContainer answerPanel = new VerticalLayoutContainer();
    HBoxLayoutContainer questionTopContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    HBoxLayoutContainer buttonsContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    VBoxLayoutContainer QAPanel = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCH);
    mainContainer = new BorderLayoutContainer();

    topToolBar.add(applyButton);
    topToolBar.add(new SeparatorToolItem());
    topToolBar.add(new FieldLabel(titleField, "Test title"));

    bottomToolBar.add(prevTextButton);
    bottomToolBar.add(nextTextButton);
    bottomToolBar.add(new SeparatorToolItem());
    bottomToolBar.add(addQuestionButton);
    bottomToolBar.add(deleteQuestionButton);
    bottomToolBar.add(new FillToolItem());
    bottomToolBar.add(pagingLabel);

    scoreField.setWidth(80);
    BoxLayoutContainer.BoxLayoutData topLayoutData1 = new BoxLayoutContainer.BoxLayoutData(new Margins(2));
    BoxLayoutContainer.BoxLayoutData topLayoutData2 = new BoxLayoutContainer.BoxLayoutData(new Margins(0));
    topLayoutData1.setFlex(1);
    topLayoutData2.setFlex(6);
    topLayoutData2.setMargins(new Margins(0, 5, 0, 0));
    questionTopContainer.add(new Label("Question : "), topLayoutData1);
    questionTopContainer.add(questionField, topLayoutData2);
    questionTopContainer.add(new Label("Score : "), topLayoutData1);
    questionTopContainer.add(scoreField, topLayoutData1);
    questionTopContainer.setStyleName("questionBarBackground");

    BoxLayoutContainer.BoxLayoutData buttonsLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(3));
    buttonsLayoutData.setFlex(1);
    buttonsContainer.add(addAnswerButton, buttonsLayoutData);
    buttonsContainer.add(editAnswerButton, buttonsLayoutData);
    buttonsContainer.add(deleteAnswerButton, buttonsLayoutData);
    buttonsContainer.setStyleName("buttonsBar");

    HBoxLayoutContainer checkBoxContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    checkBoxContainer.setPack(BoxLayoutContainer.BoxLayoutPack.START);
    checkBoxContainer.add(isTrueCheckBox, new BoxLayoutContainer.BoxLayoutData(new Margins(0)));

    VerticalLayoutContainer.VerticalLayoutData verticalLayoutData = new VerticalLayoutContainer.VerticalLayoutData(1, -1);
    answerPanel.add(new FieldLabel(answerField, "Answer"), verticalLayoutData);
    answerPanel.add(new FieldLabel(checkBoxContainer, "Is true"), verticalLayoutData);
    answerPanel.add(buttonsContainer, verticalLayoutData);
    leftPanel.add(answerPanel);

    ContentPanel leftPanelContainer = new ContentPanel();
    leftPanelContainer.setHeaderVisible(false);
    leftPanelContainer.add(leftPanel);

    QAPanel.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    QAPanel.add(questionTopContainer, new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 5, 0)));
    QAPanel.setStyleName("questionBarBackground");

    BorderLayoutContainer.BorderLayoutData northLayoutData = new BorderLayoutContainer.BorderLayoutData(30);
    northLayoutData.setMargins(new Margins(0, 0, 0, 0));
    northLayoutData.setSplit(true);
    questionsContainer.setNorthWidget(QAPanel, northLayoutData);
    BorderLayoutContainer.BorderLayoutData westLayoutData = new BorderLayoutContainer.BorderLayoutData(300);
    westLayoutData.setSplit(true);
    westLayoutData.setMargins(new Margins(0, 5, 0, 0));
    questionsContainer.setWestWidget(answersGrid, westLayoutData);
    questionsContainer.setCenterWidget(leftPanelContainer);
//    questionsContainer.setStyleName("questionBarBackground");

    BorderLayoutContainer.BorderLayoutData toolbarLayoutData = new BorderLayoutContainer.BorderLayoutData(30);
    mainContainer.setNorthWidget(topToolBar, toolbarLayoutData);
    mainContainer.setSouthWidget(bottomToolBar, toolbarLayoutData);
    mainContainer.setCenterWidget(questionsContainer);

    setButtonsBarState(CreateTestController.ButtonsBarViewState.NONE);
    setNextPrevViewState(CreateTestController.NextPrevViewState.NONE);
    setDeleteButtonViewState(CreateTestController.DeleteButtonViewState.NONE);
  }

  private Grid<AnswerData> createAnswersGrid() {
    IdentityValueProvider<AnswerData> identityValueProvider = new IdentityValueProvider<AnswerData>("sm");
    CheckBoxSelectionModel<AnswerData> selectionModel = new CheckBoxSelectionModel<AnswerData>(identityValueProvider);
    selectionModel.setSelectionMode(Style.SelectionMode.SINGLE);

    ListStore<AnswerData> store = new ListStore<AnswerData>(answerDataProperties.key());

    List<ColumnConfig<AnswerData, ?>> columns = new ArrayList<ColumnConfig<AnswerData, ?>>();
    columns.add(selectionModel.getColumn());
    columns.add(new ColumnConfig<AnswerData, String>(answerDataProperties.value(), 100, "Answers"));

    Grid<AnswerData> grid = new Grid<AnswerData>(store, new ColumnModel<AnswerData>(columns));
    grid.setSelectionModel(selectionModel);
    grid.getView().setAutoFill(true);
    grid.getView().setViewConfig(new GridViewConfig<AnswerData>() {
      @Override
      public String getColStyle(AnswerData model, ValueProvider<? super AnswerData, ?> valueProvider, int rowIndex, int colIndex) {
        if (model != null && model.isTrue())
          return "correctAnswerBackground";
        return null;
      }

      @Override
      public String getRowStyle(AnswerData model, int rowIndex) {
        return "correctAnswerBackground";
      }
    });

    return grid;
  }

  @Override
  public void setButtonsBarState(CreateTestController.ButtonsBarViewState state) {
    switch (state) {
      case ADD:
        addAnswerButton.setEnabled(true);
        editAnswerButton.setEnabled(false);
        deleteAnswerButton.setEnabled(false);
        break;
      case EDIT:
        addAnswerButton.setEnabled(false);
        editAnswerButton.setEnabled(true);
        deleteAnswerButton.setEnabled(true);
        break;
      case NONE:
        addAnswerButton.setEnabled(false);
        editAnswerButton.setEnabled(false);
        deleteAnswerButton.setEnabled(false);
        break;
    }
  }

  @Override
  public void setNextPrevViewState(CreateTestController.NextPrevViewState state) {
    switch (state) {
      case BOTH:
        nextTextButton.setEnabled(true);
        prevTextButton.setEnabled(true);
        break;
      case ONLY_NEXT:
        nextTextButton.setEnabled(true);
        prevTextButton.setEnabled(false);
        break;
      case ONLY_PREV:
        nextTextButton.setEnabled(false);
        prevTextButton.setEnabled(true);
        break;
      case NONE:
        nextTextButton.setEnabled(false);
        prevTextButton.setEnabled(false);
        break;
    }
  }

  @Override
  public void setDeleteButtonViewState(CreateTestController.DeleteButtonViewState state) {
    switch (state) {
      case CAN_DELETE:
        deleteQuestionButton.setEnabled(true);
        break;
      case NONE:
        deleteQuestionButton.setEnabled(false);
        break;
    }
  }

  @Override
  public void clearFields() {
    clearAnswersFields();
    questionField.setText("");
    answersGrid.getStore().clear();
  }

  @Override
  public void clearAnswersFields() {
    answerField.setText("");
    isTrueCheckBox.setValue(false);
  }

  public TextButton getAddAnswerButton() {
    return addAnswerButton;
  }

  public TextButton getEditAnswerButton() {
    return editAnswerButton;
  }

  public TextButton getDeleteAnswerButton() {
    return deleteAnswerButton;
  }

  public TextButton getNextButton() {
    return nextTextButton;
  }

  public TextButton getPrevButton() {
    return prevTextButton;
  }

  public TextButton getAddQuestionButton() {
    return addQuestionButton;
  }

  public TextButton getDeleteQuestionButton() {
    return deleteQuestionButton;
  }

  @Override
  public TextButton getApplyButton() {
    return applyButton;
  }

  public CheckBox getIsTrueCheckBox() {
    return isTrueCheckBox;
  }

  public TextField getAnswerField() {
    return answerField;
  }

  public TextField getQuestionField() {
    return questionField;
  }

  public TextField getScoreField() {
    return scoreField;
  }

  @Override
  public TextField getTitleField() {
    return titleField;
  }

  public Label getPagingLabel() {
    return pagingLabel;
  }

  public Grid<AnswerData> getAnswersGrid() {
    return answersGrid;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

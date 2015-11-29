package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_tests;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.shared.model.AnswerData;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;

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
  private TextField titleField;
  private TextField questionField;
  private TextField answerField;
  private CheckBox isTrueCheckBox;
  private Grid<AnswerData> answersGrid;

  public CreateTestView() {
    initGUI();
  }

  private void initGUI() {
    applyButton = new TextButton("Create", ELearningController.ICONS.apply());
    prevTextButton = new TextButton("", ELearningController.ICONS.arrowleft());
    nextTextButton = new TextButton("", ELearningController.ICONS.arrowright());
    addAnswerButton = new TextButton("", ELearningController.ICONS.add());
    editAnswerButton = new TextButton("", ELearningController.ICONS.edit());
    deleteAnswerButton = new TextButton("", ELearningController.ICONS.delete());
    titleField = new TextField();
    questionField = new TextField();
    answerField = new TextField();
    answersGrid = createAnswersGrid();
    isTrueCheckBox = new CheckBox();
    ToolBar topToolBar = new ToolBar();
    ToolBar bottomToolBar = new ToolBar();
    BorderLayoutContainer questionsContainer = new BorderLayoutContainer();
    HBoxLayoutContainer questionPanel = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    HBoxLayoutContainer answerPanel = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    VBoxLayoutContainer QAPanel = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCH);
    mainContainer = new BorderLayoutContainer();

    topToolBar.add(applyButton);
    topToolBar.add(new SeparatorToolItem());
    topToolBar.add(new FieldLabel(titleField, "Test title"));

    bottomToolBar.add(prevTextButton);
    bottomToolBar.add(nextTextButton);
    bottomToolBar.add(new SeparatorToolItem());

    questionField.setWidth(350);
    questionPanel.setPack(BoxLayoutContainer.BoxLayoutPack.START);
    questionPanel.add(new FieldLabel(questionField, "Question"), new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0)));

    isTrueCheckBox.setBoxLabel("Is true");
    answerField.setWidth(150);
    answerPanel.setPack(BoxLayoutContainer.BoxLayoutPack.START);
    answerPanel.add(new FieldLabel(answerField, "Answer"), new BoxLayoutContainer.BoxLayoutData());
    BoxLayoutContainer.BoxLayoutData answerLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 0, 10));
    answerPanel.add(isTrueCheckBox, answerLayoutData);
    answerPanel.add(addAnswerButton, answerLayoutData);
    answerPanel.add(editAnswerButton, answerLayoutData);
    answerPanel.add(deleteAnswerButton, answerLayoutData);

    QAPanel.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    QAPanel.add(questionPanel, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));
    QAPanel.add(answerPanel, new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 5, 5)));

    questionsContainer.setNorthWidget(QAPanel, new BorderLayoutContainer.BorderLayoutData(60));
    questionsContainer.setCenterWidget(answersGrid);

    BorderLayoutContainer.BorderLayoutData toolbarLayoutData = new BorderLayoutContainer.BorderLayoutData(30);
    mainContainer.setNorthWidget(topToolBar, toolbarLayoutData);
    mainContainer.setSouthWidget(bottomToolBar, toolbarLayoutData);
    mainContainer.setCenterWidget(questionsContainer);
  }

  private Grid<AnswerData> createAnswersGrid() {
    ListStore<AnswerData> store = new ListStore<AnswerData>(answerDataProperties.key());

    List<ColumnConfig<AnswerData, ?>> columns = new ArrayList<ColumnConfig<AnswerData, ?>>();
    columns.add(new ColumnConfig<AnswerData, String>(answerDataProperties.value(), 100, "Answers"));

    Grid<AnswerData> grid = new Grid<AnswerData>(store, new ColumnModel<AnswerData>(columns));
    grid.getView().setAutoFill(true);

    return grid;
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

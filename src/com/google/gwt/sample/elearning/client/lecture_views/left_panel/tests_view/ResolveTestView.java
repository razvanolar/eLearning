package com.google.gwt.sample.elearning.client.lecture_views.left_panel.tests_view;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.List;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ResolveTestView implements ResolveTestController.IResolveTestView {

  private BorderLayoutContainer mainContainer;
  private HtmlLayoutContainer htmlLayoutContainer;
  private TextButton applyButton, previousQuestionButton, nextQuestionButton;
  private VBoxLayoutContainer answersContainer;

  public ResolveTestView() {
    initGUI();
  }

  private void initGUI() {
    ToolBar toolBar = new ToolBar();
    ToolBar bottomToolbar = new ToolBar();
    applyButton = new TextButton("Apply", ELearningController.ICONS.apply());
    previousQuestionButton = new TextButton("", ELearningController.ICONS.arrowleft());
    nextQuestionButton = new TextButton("", ELearningController.ICONS.arrowright());
    VBoxLayoutContainer questionContainer = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);
    VBoxLayoutContainer centerPanel = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);
    answersContainer = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);
    htmlLayoutContainer = new HtmlLayoutContainer("");
    mainContainer = new BorderLayoutContainer();
    BorderLayoutContainer secondLayoutContainer = new BorderLayoutContainer();

    toolBar.add(applyButton);

    bottomToolbar.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    bottomToolbar.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    bottomToolbar.add(previousQuestionButton);
    bottomToolbar.add(nextQuestionButton);

    questionContainer.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    centerPanel.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    answersContainer.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);

    questionContainer.add(htmlLayoutContainer);

    centerPanel.add(answersContainer);

    secondLayoutContainer.setNorthWidget(questionContainer, new BorderLayoutContainer.BorderLayoutData(30));
    secondLayoutContainer.setCenterWidget(centerPanel);

    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setSouthWidget(bottomToolbar, new BorderLayoutContainer.BorderLayoutData(30));
    mainContainer.setCenterWidget(secondLayoutContainer);
  }

  @Override
  public void displayScore(long score) {
    mainContainer.clear();
    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
    centerLayoutContainer.add(new Label("Your score : " + score));
    mainContainer.setCenterWidget(centerLayoutContainer);
    mainContainer.forceLayout();
  }

  public TextButton getPreviousQuestionButton() {
    return previousQuestionButton;
  }

  public TextButton getNextQuestionButton() {
    return nextQuestionButton;
  }

  public void setQuestion(String text) {
    htmlLayoutContainer.setHTML("<p>" + text + "</p>");
  }

  public void setAnswers(List<CheckBox> answers) {
    answersContainer.clear();
    for (CheckBox checkBox : answers)
      answersContainer.add(checkBox);
  }

  public TextButton getApplyButton() {
    return applyButton;
  }

  @Override
  public void setArrowState(ResolveTestController.IResolveTestArrowsState state) {
    switch (state) {
      case LEFT:
        previousQuestionButton.setEnabled(true);
        nextQuestionButton.setEnabled(false);
        break;
      case RIGHT:
        previousQuestionButton.setEnabled(false);
        nextQuestionButton.setEnabled(true);
        break;
      case BOTH:
        previousQuestionButton.setEnabled(true);
        nextQuestionButton.setEnabled(true);
        break;
    }
  }

  @Override
  public void mask() {
    mainContainer.mask();
  }

  @Override
  public void unmask() {
    mainContainer.unmask();
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

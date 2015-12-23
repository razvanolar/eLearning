package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_homework;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 *
 * Created by Cristi on 12/23/2015.
 */
public class CreateHomeworkView implements CreateHomeworkController.ICreateHomeworkView {
  private TextButton saveButton;
  private TextField titleField;
  private TextField scoreField;
  private TextArea descriptionTextArea;
  private BorderLayoutContainer mainContainer;


  public CreateHomeworkView() {
    initGUI();
  }

  private void initGUI() {
    saveButton = new TextButton("Save", ELearningController.ICONS.apply());
    titleField = new TextField();
    scoreField = new TextField();
    descriptionTextArea = new TextArea();
    mainContainer = new BorderLayoutContainer();
    CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
    VBoxLayoutContainer centerContainer = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCHMAX);
    descriptionTextArea.setPixelSize(300, 160);
    BoxLayoutContainer.BoxLayoutData formLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(5));
    centerContainer.add(new FieldLabel(titleField, "Title"), formLayoutData);
    centerContainer.add(new FieldLabel(scoreField, "Score"), formLayoutData);
    centerContainer.add(new FieldLabel(new Label(), "Description"), formLayoutData);
    centerContainer.add(descriptionTextArea, formLayoutData);
    ToolBar toolBar = new ToolBar();
    toolBar.add(saveButton);
    BorderLayoutContainer.BorderLayoutData leftLayoutData = new BorderLayoutContainer.BorderLayoutData(30);
    leftLayoutData.setSplit(true);
    leftLayoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setNorthWidget(toolBar, leftLayoutData);
    centerLayoutContainer.add(centerContainer);
    mainContainer.setCenterWidget(centerLayoutContainer);
  }

  @Override
  public TextButton getSaveButton() {
    return saveButton;
  }

  @Override
  public TextField getTitleField() {
    return titleField;
  }

  @Override
  public TextField getScoreField() {
    return scoreField;
  }

  @Override
  public TextArea getDescriptionTextArea() {
    return descriptionTextArea;
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

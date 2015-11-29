package com.google.gwt.sample.elearning.client.eLearningUtils;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.logging.Logger;

/***
 * Created by razvanolar on 22.11.2015.
 */
public class HtmlEditorView implements HtmlEditorController.IHtmlEditorView {

  private static String SUCCES_MESSAGE = "Saved";
  private static String FAIL_MESSAGE = "Action failed";

  private HtmlEditor editor;
  private BorderLayoutContainer mainContainer;
  private TextField titleField;
  private TextButton saveButton;
  private Image saveStatusImage;

  private Logger log = Logger.getLogger(HtmlEditorView.class.getName());

  public HtmlEditorView() {
    initGUI();
  }

  private void initGUI() {
    editor = new HtmlEditor();
    mainContainer = new BorderLayoutContainer();
    titleField = new TextField();
    saveButton = new TextButton("", ELearningController.ICONS.save());
    saveStatusImage = new Image(ELearningController.ICONS.succes());
    ToolBar toolBar = new ToolBar();

    toolBar.add(saveButton);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(new Label("Title : "));
    toolBar.add(titleField);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(saveStatusImage);

    saveStatusImage.setVisible(false);

    mainContainer.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(25));
    mainContainer.setCenterWidget(editor);
  }

  @Override
  public void setSaveStatus(HtmlEditorController.SaveStatus status) {
    Timer timer = new Timer() {
      public void run() {
        saveStatusImage.setVisible(false);
        cancel();
      }
    };
    timer.schedule(2000);
    saveStatusImage.setVisible(true);
    switch (status) {
    case SAVED:
      saveStatusImage.setResource(ELearningController.ICONS.succes());
      break;
    case FAILED:
      saveStatusImage.setResource(ELearningController.ICONS.fail());
      break;
    }
  }

  @Override
  public TextField getTitleField() {
    return titleField;
  }

  @Override
  public TextButton getSaveButton() {
    return saveButton;
  }

  @Override
  public String getEditorValue() {
    return editor.getValue();
  }

  @Override
  public void setEditorValue(String value) {
    editor.setValue(value);
    editor.setEnableFormat(true);
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }
}

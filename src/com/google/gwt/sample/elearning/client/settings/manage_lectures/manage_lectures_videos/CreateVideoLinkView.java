package com.google.gwt.sample.elearning.client.settings.manage_lectures.manage_lectures_videos;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 *
 * Created by razvanolar on 01.12.2015.
 */
public class CreateVideoLinkView implements CreateVideoLinkController.ICreateVideoLinkView {

  private TextButton saveButton;
  private TextButton testLinkButton;
  private TextField titleField;
  private TextField linkField;
  private TextArea descriptionTextArea;
  private Frame videoFrame;
  private BorderLayoutContainer mainContainer;

  public CreateVideoLinkView() {
    initGUI();
  }

  private void initGUI() {
    saveButton = new TextButton("Save", ELearningController.ICONS.apply());
    testLinkButton = new TextButton("Test link");
    titleField = new TextField();
    linkField = new TextField();
    descriptionTextArea = new TextArea();
    videoFrame = new Frame();
    mainContainer = new BorderLayoutContainer();
    ToolBar toolBar = new ToolBar();
    CenterLayoutContainer northContainer = new CenterLayoutContainer();
    VBoxLayoutContainer formPanel = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCHMAX);
    BorderLayoutContainer topPanel = new BorderLayoutContainer();

    descriptionTextArea.setPixelSize(300, 100);
    formPanel.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    BoxLayoutContainer.BoxLayoutData formLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(5, 0, 0, 0));
    formPanel.add(new FieldLabel(titleField, "Title"), formLayoutData);
    formPanel.add(new FieldLabel(linkField, "Url"), formLayoutData);
    formPanel.add(new FieldLabel(new Label(), "Description"), formLayoutData);
    formPanel.add(descriptionTextArea, formLayoutData);

    northContainer.add(formPanel);

    toolBar.add(saveButton);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(testLinkButton);

    topPanel.setNorthWidget(toolBar, new BorderLayoutContainer.BorderLayoutData(30));
    topPanel.setCenterWidget(northContainer);

    BorderLayoutContainer.BorderLayoutData leftLayoutData = new BorderLayoutContainer.BorderLayoutData(250);
    leftLayoutData.setSplit(true);
    leftLayoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setNorthWidget(topPanel, leftLayoutData);
    mainContainer.setCenterWidget(videoFrame);
  }

  public void testLink(String link) {
    videoFrame.setUrl(link);
  }

  public TextButton getSaveButton() {
    return saveButton;
  }

  public TextButton getTestLinkButton() {
    return testLinkButton;
  }

  public TextField getLinkField() {
    return linkField;
  }

  public TextField getTitleField() {
    return titleField;
  }

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

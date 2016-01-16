package com.google.gwt.sample.elearning.client.lecture_views.left_panel.homeworks_view.resolve_homework;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 *
 * Created by Cristi on 1/16/2016.
 */
public class ResolveHomeworkView implements ResolveHomeworkController.IResolveHomeworkView {
  private BorderLayoutContainer mainContainer;
  private HtmlLayoutContainer htmlLayoutContainer;
  private TextArea textArea;
  private TextButton resolveButton;

  public ResolveHomeworkView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();
    htmlLayoutContainer = new HtmlLayoutContainer("");
    textArea = new TextArea();
    textArea.setPixelSize(250, 160);
    resolveButton = new TextButton("Resolve", ELearningController.ICONS.apply());

    ToolBar toolBar = new ToolBar();
    toolBar.add(resolveButton);

    BorderLayoutContainer.BorderLayoutData layoutData = new BorderLayoutContainer.BorderLayoutData(30);
    layoutData.setMargins(new Margins(0, 5, 0, 0));
    mainContainer.setNorthWidget(toolBar, layoutData);
    BoxLayoutContainer.BoxLayoutData formLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(3,3,0,3));
    formLayoutData.setFlex(1);
    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);
    vBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    vBoxLayoutContainer.add(htmlLayoutContainer, formLayoutData);

    VBoxLayoutContainer solutionContainer = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCHMAX);
    vBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);

    BoxLayoutContainer.BoxLayoutData solutionLayoutData = new BoxLayoutContainer.BoxLayoutData(new Margins(3,3,0,3));
    solutionLayoutData.setFlex(3);
    solutionContainer.add(new FieldLabel(new Label(), "Solution"), solutionLayoutData);
    solutionContainer.add(textArea, solutionLayoutData);

    VBoxLayoutContainer mainVbox = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCHMAX);
//    mainVbox.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);

    mainVbox.add(vBoxLayoutContainer);
    mainVbox.add(solutionContainer);

    mainContainer.setCenterWidget(mainVbox);
  }

  @Override
  public Widget asWidget() {
    return mainContainer;
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
  public HtmlLayoutContainer getHtmlLayoutContainer() {
    return htmlLayoutContainer;
  }

  @Override
  public TextArea getTextArea() {
    return textArea;
  }

  @Override
  public TextButton getResolveButton() {
    return resolveButton;
  }
}

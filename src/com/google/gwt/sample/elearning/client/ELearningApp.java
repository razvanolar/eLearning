package com.google.gwt.sample.elearning.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.container.*;

public class ELearningApp implements EntryPoint {

  /*Entry point method*/
  public void onModuleLoad() {
    Viewport viewport = new Viewport();
    RootPanel.get("eLearning").add(viewport);

    CenterLayoutContainer centerPanel = new CenterLayoutContainer();

    BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
    BoxLayoutContainer.BoxLayoutData flex2 = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
    flex.setFlex(1);
    flex2.setFlex(3);
    HBoxLayoutContainer northPanel = new HBoxLayoutContainer();
    HBoxLayoutContainer buttonsPanel = new HBoxLayoutContainer();
    northPanel.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    northPanel.setStyleName("headerPanel");
    buttonsPanel.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    buttonsPanel.setPack(BoxLayoutContainer.BoxLayoutPack.END);
    buttonsPanel.setHeight(40);
    buttonsPanel.setPadding(new Padding(0, 10, 0, 0));

    northPanel.add(new Image(ELearningController.ICONS.logo()), flex);
    northPanel.add(buttonsPanel, flex2);

    BorderLayoutContainer mainELearningContainer = new BorderLayoutContainer();
    mainELearningContainer.setNorthWidget(northPanel, new BorderLayoutContainer.BorderLayoutData(50));
    mainELearningContainer.setCenterWidget(centerPanel);

    mainELearningContainer.setStyleName("mainELearningContainer");
    viewport.add(mainELearningContainer);
    ELearningController.getInstance().setViews(mainELearningContainer, buttonsPanel);
    ELearningController.getInstance().run();
  }
}

package com.google.gwt.sample.elearning.client.main_views.right_panel.usersView;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;

/**
 *
 * Created by Cristi on 1/16/2016.
 */
public class UsersPopupView implements UsersPopupController.IUsersPopupView{

  private BorderLayoutContainer mainContainer;
  private HtmlLayoutContainer htmlLayoutContainer;
  private Label nameLabel;
  private Label emailLabel;
  private TextButton sendEmailButton;

  public UsersPopupView() {
    initGUI();
  }

  private void initGUI() {
    mainContainer = new BorderLayoutContainer();

    htmlLayoutContainer = new HtmlLayoutContainer("");
    nameLabel = new Label("name");
    emailLabel = new Label("email");
    sendEmailButton = new TextButton();
    sendEmailButton.setIcon(ELearningController.ICONS.mail());

    BorderLayoutContainer.BorderLayoutData westData = new BorderLayoutContainer.BorderLayoutData(50);
    westData.setMargins(new Margins(3));
    mainContainer.setWestWidget(htmlLayoutContainer, westData);

    HBoxLayoutContainer hBoxLayoutContainer = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);
    hBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.END);
    BoxLayoutContainer.BoxLayoutData layoutData = new BoxLayoutContainer.BoxLayoutData();
    layoutData.setMargins(new Margins(3));
    hBoxLayoutContainer.add(sendEmailButton, layoutData);

    mainContainer.setSouthWidget(hBoxLayoutContainer, new BorderLayoutContainer.BorderLayoutData(27));

    VBoxLayoutContainer vBoxLayoutContainer = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.STRETCHMAX);
    vBoxLayoutContainer.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    vBoxLayoutContainer.add(nameLabel);
    vBoxLayoutContainer.add(emailLabel);
    mainContainer.setCenterWidget(vBoxLayoutContainer);

  }

  @Override
  public Widget asWidget() {
    return mainContainer;
  }

  @Override
  public HtmlLayoutContainer getHtmlLayoutContainer() {
    return htmlLayoutContainer;
  }

  @Override
  public Label getNameLabel() {
    return nameLabel;
  }

  @Override
  public Label getEmailLabel() {
    return emailLabel;
  }

  @Override
  public TextButton getSendEmailButton() {
    return sendEmailButton;
  }
}

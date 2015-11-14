package com.google.gwt.sample.elearning.client.profilebar;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/***
 * Created by razvanolar on 14.11.2015.
 */
public class ProfileBarController {

  public interface IProfileBarView {
    TextButton getSettingsButton();
    Widget asWidget();
  }

  private IProfileBarView view;

  public ProfileBarController(IProfileBarView view) {
    this.view = view;
  }

  public void bind() {
    addListeners();
  }

  private void addListeners() {
    if (view.getSettingsButton() != null)
      view.getSettingsButton().addSelectHandler(new SelectEvent.SelectHandler() {
        public void onSelect(SelectEvent event) {
          doOnSettingsSelect();
        }
      });
  }

  private void doOnSettingsSelect() {

  }
}

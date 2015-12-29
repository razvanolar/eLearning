package com.google.gwt.sample.elearning.client.profilebar;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.menu.Menu;

/**
 *
 * Created by razvanolar on 29.12.2015.
 */
public class GridMenu extends Menu {

  public GridMenu(Widget grid) {
    add(grid);
    appearance.applyDateMenu(getElement());
    plain = true;
    showSeparator = false;
    setEnableScrolling(false);
  }
}

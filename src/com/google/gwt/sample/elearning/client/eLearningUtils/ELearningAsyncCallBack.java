package com.google.gwt.sample.elearning.client.eLearningUtils;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.box.MessageBox;

import java.util.logging.Logger;

/**
 *
 * Created by Cristi on 11/29/2015.
 */
public abstract class ELearningAsyncCallBack<T> implements AsyncCallback<T> {
  private MaskableView view;
  private Logger logger;

  public ELearningAsyncCallBack(MaskableView view, Logger logger) {
    this.view = view;
    this.logger = logger;
  }

  @Override
  public void onFailure(Throwable caught) {
    String message = caught.getMessage();

    if (view != null) {
      view.unmask();
    }

    new MessageBox("Error", message).show();
    logger.warning(message);
  }

  @Override
  public abstract void onSuccess(T result);
}

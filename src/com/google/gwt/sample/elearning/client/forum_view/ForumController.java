package com.google.gwt.sample.elearning.client.forum_view;

import com.google.gwt.sample.elearning.client.ELearningController;
import com.google.gwt.sample.elearning.client.service.ForumServiceAsync;
import com.google.gwt.sample.elearning.shared.model.ForumCommentData;
import com.google.gwt.sample.elearning.shared.model.ForumData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.List;

/**
 *
 * Created by razvanolar on 16.01.2016.
 */
public class ForumController {
  private ForumServiceAsync forumService;
  private IForumView view;

  public interface IForumView {
    Widget asWidget();
    TextField getTextField();

  }

  public ForumController(IForumView view, ForumServiceAsync forumService) {
    this.view = view;
    this.forumService = forumService;
  }

  public void bind() {
    addListeners();
    loadResources();
  }

  private void addListeners() {

  }

  public void loadResources() {
    forumService.getUserAvailableForums(ELearningController.getInstance().getCurrentUser().getId(), new AsyncCallback<List<ForumData>>() {
      @Override
      public void onFailure(Throwable caught) {
        (new MessageBox("Error", "Failed to load forum <br>Message: " + caught.getMessage())).show();
      }

      @Override
      public void onSuccess(List<ForumData> result) {
        String rez = "";
        for(ForumData fd : result) {
          for(ForumCommentData forumCommentData : fd.getCommentList()) {
            rez += forumCommentData.getUserId() + ": " + forumCommentData.getCommentText() + "\n";
          }
        }
        view.getTextField().setText(rez);
      }
    });
  }
}

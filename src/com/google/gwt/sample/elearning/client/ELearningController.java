package com.google.gwt.sample.elearning.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.elearning.client.forum_view.ForumController;
import com.google.gwt.sample.elearning.client.forum_view.ForumView;
import com.google.gwt.sample.elearning.client.icons.Icons;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.homeworks_view.LectureHomeworksController;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.tests_view.LectureTestsController;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.videos_view.LectureVideosController;
import com.google.gwt.sample.elearning.client.login.LoginController;
import com.google.gwt.sample.elearning.client.login.LoginView;
import com.google.gwt.sample.elearning.client.logs.LogGridHandler;
import com.google.gwt.sample.elearning.client.lecture_views.UserLecturesController;
import com.google.gwt.sample.elearning.client.lecture_views.UserLecturesView;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.LectureDetailsView;
import com.google.gwt.sample.elearning.client.lecture_views.left_panel.filesView.LectureFilesController;
import com.google.gwt.sample.elearning.client.lecture_views.right_panel.LectureInfoView;
import com.google.gwt.sample.elearning.client.lecture_views.right_panel.calendarView.CalendarController;
import com.google.gwt.sample.elearning.client.lecture_views.right_panel.usersView.LectureUsersController;
import com.google.gwt.sample.elearning.client.profilebar.ProfileBarController;
import com.google.gwt.sample.elearning.client.profilebar.ProfileBarView;
import com.google.gwt.sample.elearning.client.service.*;
import com.google.gwt.sample.elearning.shared.model.Lecture;
import com.google.gwt.sample.elearning.shared.model.UserData;
import com.google.gwt.sample.elearning.shared.types.UserRoleTypes;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;

import java.util.logging.Logger;

/***
 * Created by razvanolar on 13.11.2015.
 */
public class ELearningController {

  private static ELearningController INSTANCE;
  public static final Icons ICONS = GWT.create(Icons.class);
  LogGridHandler logGridHandler;
  Logger log = Logger.getLogger("com.google.gwt.sample.elearning.client");
  private BorderLayoutContainer mainELearningContainer;
  private HBoxLayoutContainer buttonsContainer;
  private ELearningView eLearningView;

  private static LoginServiceAsync loginService = GWT.create(LoginService.class);
  private static LectureServiceAsync lectureService = GWT.create(LectureService.class);
  private static ForumServiceAsync forumService = GWT.create(ForumService.class);

  private LectureFilesController lectureFilesController;
  private LectureVideosController lectureVideosController;
  private LectureUsersController lectureUsersController;
  private LectureTestsController lectureTestsController;
  private LectureHomeworksController lectureHomeworksController;
  private CalendarController calendarController;
  private UserData currentUser;
  private Lecture currentLecture;

  private ELearningController() {}

  public void run() {
    logGridHandler = LogGridHandler.getInstance();
    log.addHandler(logGridHandler);
    String sessionID = Cookies.getCookie("sid");
    if(sessionID == null)
      displayLoginWindow();
    else {
      loginService.loginFromSessionServer(new AsyncCallback<UserData>() {
        public void onFailure(Throwable caught) {
          displayLoginWindow();
        }

        public void onSuccess(UserData result) {
          currentUser = result;
          displayWindow();
          LoginController.initSessionTimeoutTimer();
        }
      });
    }
  }

  private void displayLoginWindow() {
    LoginController.ILoginView loginView = new LoginView();
    LoginController loginController = new LoginController(loginView, loginService);
    mainELearningContainer.setCenterWidget(loginView.asWidget());
    loginController.bind();
  }

  public void onSuccessLogin(UserData userData) {
    currentUser = userData;
    mainELearningContainer.setCenterWidget(null);
    displayWindow();
  }

  private void displayWindow() {
    UserLecturesController.IUserLecturesView userLecturesView = new UserLecturesView();
    UserLecturesController controller = new UserLecturesController(userLecturesView, lectureService);
    controller.bind();
    ProfileBarController.IProfileBarView profileBarView = new ProfileBarView(currentUser.getRole() != UserRoleTypes.USER,
            userLecturesView.asWidget());
    ProfileBarController profileController = new ProfileBarController(profileBarView, controller);
    profileController.bind();

    controller.setParentController(profileController);

    buttonsContainer.add(profileBarView.asWidget());
    buttonsContainer.forceLayout();
  }

  public void loadLectureDetails(Lecture lecture) {
    log.info("Lecture selected: " + lecture.getLectureName());

    currentLecture = lecture;
    if (eLearningView == null) {
      eLearningView = new ELearningView();
      log.info("Initialize ELearningView widget");
    } else {
      if (eLearningView.getLectureContentView() != null)
        eLearningView.getLectureContentView().clear();
    }
    mainELearningContainer.setCenterWidget(eLearningView.asWidget());
    mainELearningContainer.forceLayout();

    /* create files controller */
    if (lectureFilesController == null) {
      LectureDetailsView lectureDetailsView = eLearningView.getLectureDetailsView();
      lectureFilesController = new LectureFilesController(lectureDetailsView.getLectureFilesView(), lectureService);
      lectureFilesController.bind();
    }
    lectureFilesController.loadFiles();

    /* create videos controller */
    if (lectureVideosController == null) {
      LectureDetailsView lectureDetailsView = eLearningView.getLectureDetailsView();
      lectureVideosController = new LectureVideosController(lectureDetailsView.getLectureVideosView(), lectureService);
      lectureVideosController.bind();
    }
    lectureVideosController.loadVideos();

    /* create tests controller */
    if (lectureTestsController == null) {
      LectureDetailsView lectureDetailsView = eLearningView.getLectureDetailsView();
      lectureTestsController = new LectureTestsController(lectureDetailsView.getLectureTestsView(), lectureService);
      lectureTestsController.bind();
    }
    lectureTestsController.loadTests();

    /* create homework controller*/
    if(lectureHomeworksController == null) {
      LectureDetailsView lectureDetailsView = eLearningView.getLectureDetailsView();
      lectureHomeworksController = new LectureHomeworksController(lectureDetailsView.getLectureHomeworkView(), lectureService);
      lectureHomeworksController.bind();
    }
    lectureHomeworksController.loadHomeworks();

    LectureInfoView lectureInfoView = eLearningView.getLectureInfoView();

    if (lectureUsersController == null) {
      lectureUsersController = new LectureUsersController(lectureInfoView.getLectureUsersView());
      lectureUsersController.bind();
    }

    lectureUsersController.loadUsers();

    if (calendarController == null) {
      calendarController = new CalendarController(lectureInfoView.getCalendarView());
    }
  }

  public void loadForumDetails() {
    ForumController.IForumView forumView = new ForumView();
    ForumController forumController = new ForumController(forumView, forumService);
    forumController.bind();
    mainELearningContainer.setCenterWidget(forumView.asWidget());
    mainELearningContainer.forceLayout();
  }

  public void loadLectureFileInCenterPanel(String path, String title) {
    eLearningView.getLectureContentView().addPanel(path, title);
  }

  public static ELearningController getInstance() {
    if (INSTANCE == null)
      INSTANCE = new ELearningController();
    return INSTANCE;
  }

  public void setViews(BorderLayoutContainer mainELearningContainer, HBoxLayoutContainer buttonsContainer) {
    this.mainELearningContainer = mainELearningContainer;
    this.buttonsContainer = buttonsContainer;
  }

  public UserData getCurrentUser() {
    return currentUser;
  }

  public Lecture getCurrentLecture() {
    return currentLecture;
  }
}

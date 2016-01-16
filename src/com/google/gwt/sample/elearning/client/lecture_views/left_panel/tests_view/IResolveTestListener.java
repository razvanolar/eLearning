package com.google.gwt.sample.elearning.client.lecture_views.left_panel.tests_view;

import com.google.gwt.sample.elearning.shared.model.LectureTestData;

/**
 *
 * Created by razvanolar on 17.01.2016.
 */
public interface IResolveTestListener {

  void resolveTest(long userId, LectureTestData testData, ResolveTestController.IResolveTestView testView);
}

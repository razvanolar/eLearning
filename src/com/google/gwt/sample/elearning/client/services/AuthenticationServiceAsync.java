package com.google.gwt.sample.elearning.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by Horea on 01/11/2015.
 */
public interface AuthenticationServiceAsync {
    void authenticate(String userId, String password, AsyncCallback<Boolean> result);
}

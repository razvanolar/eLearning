package com.google.gwt.sample.elearning.client.services;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created by Horea on 01/11/2015.
 */
@RemoteServiceRelativePath("AuthenticationService")
public interface AuthenticationService {
    boolean authenticate(String userId, String password);
}

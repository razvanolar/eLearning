package com.google.gwt.sample.elearning.shared.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Horea on 09/11/2015.
 */
public class IncorrectLoginException extends Exception implements IsSerializable{
    public IncorrectLoginException(String message) {
        super(message);
    }

    public IncorrectLoginException() {
    }
}

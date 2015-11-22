package com.google.gwt.sample.elearning.server.repository;

/**
 * Created by Valy on 11/21/2015.
 */
public class RepositoryException extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    public RepositoryException(){
        super();
    }

    public RepositoryException(final String message){
        super(message);
    }

    public RepositoryException(final String message, final Throwable cause){
        super(message,cause);
    }
}

package com.google.gwt.sample.elearning.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;

public class Resources {

	public static Bundle instance = GWT.create(Bundle.class);
	
	static{
		
		StyleInjector.inject(instance.css().getText());
	}
}

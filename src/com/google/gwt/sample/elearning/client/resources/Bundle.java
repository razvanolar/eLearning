package com.google.gwt.sample.elearning.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.sample.elearning.client.resources.css.StylesCss;
import com.google.gwt.sample.elearning.client.resources.images.Images;

public interface Bundle extends ClientBundle{
	
	@Source("css/StylesCss.css")
	public StylesCss css();
	
	public Images images();
	

}

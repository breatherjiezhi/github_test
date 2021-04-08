package com.dhc.rad.modules.sys.listener;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

import com.dhc.rad.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		try {
			if (!SystemService.printKeyLoadMessage()){
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.initWebApplicationContext(servletContext);
	}
}

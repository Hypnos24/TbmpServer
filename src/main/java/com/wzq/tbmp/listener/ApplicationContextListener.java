package com.wzq.tbmp.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wzq.tbmp.service.akka.AkkaService;
import com.wzq.tbmp.service.asyn.AsynTaskService;
import com.wzq.tbmp.service.data.ElasticDataService;
import com.wzq.tbmp.service.jms.BaseJmsService.Channel;

public class ApplicationContextListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		ServiceLocator.setApplicationContext(context);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		ElasticDataService.getInstance().refreshCacheToES();
		AsynTaskService.getInstance().dispose();
		AkkaService.getInstance().dispose();
		ElasticDataService.getInstance().stop();
		this.stopJmsService();
	}
	
	private void stopJmsService() {
		for (Channel channel : Channel.values()) {
			channel.getService().stop();
		}
	}

}

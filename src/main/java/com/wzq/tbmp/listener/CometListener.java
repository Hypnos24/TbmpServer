package com.wzq.tbmp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometContext;

import com.wzq.tbmp.service.data.ElasticDataService;


public class CometListener implements ServletContextListener {
	public static final String CHANNEL_LOGOUT_PUSH = "logout_push"; 
	
	public void contextDestroyed(ServletContextEvent event) {
		ElasticDataService.getInstance().refreshCacheToES();
		
	}

	public void contextInitialized(ServletContextEvent event) {
		CometContext cc = CometContext.getInstance();
        cc.registChannel(CHANNEL_LOGOUT_PUSH);// 注册应用的channel
        //CometEngine engine = cc.getEngine();
        //engine.addConnectListener(new JoinListener());
        //engine.addDropListener(new LeftListener());
	}

}

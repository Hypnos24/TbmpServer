package com.wzq.tbmp.servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.listener.ServiceLocator;
import com.wzq.tbmp.service.IGlobalConfigService;
import com.wzq.tbmp.service.akka.AkkaService;
import com.wzq.tbmp.service.asyn.AsynTaskService;
import com.wzq.tbmp.service.data.ElasticDataService;
import com.wzq.tbmp.service.jms.BaseJmsService.Channel;

public class StartUpServlet extends HttpServlet {
	private final Logger logger = Logger.getLogger(StartUpServlet.class);

	private static final long serialVersionUID = 6487993737859386918L;
	
	public static final String VersionName = "20151219b1";//版本名称
	
	private static ServletContext context;

	public void destroy() {
		ElasticDataService.getInstance().refreshCacheToES();
		
	}
	
	public static Object getApplicationValue(String name) {
		return context.getAttribute(name);
	}

	public void init() throws ServletException {
		context = this.getServletContext();
		
		Constant.PRO_CTX_VALUE = this.getServletContext().getContextPath();
		this.getServletContext().setAttribute(Constant.PRO_CTX_KEY, Constant.PRO_CTX_VALUE);
		
		Constant.TOMCAT_SERVICE_ADDRESS = this.getServletContext().getRealPath("");
		
		Constant.DATA_LOGO_SAVE_PATH_VALUE = ServiceLocator.getMessage("logo.real.address");
		Constant.DATA_LOGO_WEB_PATH_VALUE = ServiceLocator.getMessage("logo.web.url");
		Constant.KEY_SIGN = ServiceLocator.getMessage("key.sign");
		this.getServletContext().setAttribute(Constant.DATA_LOGO_WEB_PATH_KEY, Constant.DATA_LOGO_WEB_PATH_VALUE);
		
//		Constant.LOCAL_AKKA_PORT = NumberUtils.toInt(ServiceLocator.getMessage("local.akka.port"));
//		Constant.JMS_PASSWORD = ServiceLocator.getMessage("jms.password");
//		Constant.JMS_URL = ServiceLocator.getMessage("jms.url");
//		Constant.JMS_USER = ServiceLocator.getMessage("jms.user");
		
		Constant.ELASTICSEARCH_CLUSTER_NAME = ServiceLocator.getMessage("elasticsearch.cluster.name");
		Constant.ELASTICSEARCH_HOST = ServiceLocator.getMessage("elasticsearch.host");
		Constant.ELASTICSEARCH_PORT = NumberUtils.toInt(ServiceLocator.getMessage("elasticsearch.port"));
		Constant.ELASTICSEARCH_CACHE_SIZE = NumberUtils.toInt(ServiceLocator.getMessage("elasticsearch.cache.size"));
		
		IGlobalConfigService globalConfigService = ServiceLocator.getBean(IGlobalConfigService.class);
		// 执行增量脚本
		globalConfigService.runDbMigrate();
		globalConfigService.initSystemParam();
//		globalConfigService.initQuartzJob();
		
//		AkkaService.getInstance().init();
//		AsynTaskService.getInstance().init();
//		ElasticDataService.getInstance().start();
//		
//		globalConfigService.fixData();
		
//		this.startJmsService();
		
		logger.info("启动成功...");
		
	}
	
	private void startJmsService() {
		for (Channel channel : Channel.values()) {
			channel.getService().start();
		}
	}
	
}

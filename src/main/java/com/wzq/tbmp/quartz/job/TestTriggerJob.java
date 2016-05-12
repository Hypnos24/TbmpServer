package com.wzq.tbmp.quartz.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestTriggerJob implements Job {

	private Logger logger = Logger.getLogger(TestTriggerJob.class);
	
	@Override
	public void execute(JobExecutionContext data) throws JobExecutionException {
//		logger.error("我是Quartz动态触发器");
	}

}

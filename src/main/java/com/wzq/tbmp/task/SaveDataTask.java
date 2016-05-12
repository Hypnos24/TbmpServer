package com.wzq.tbmp.task;

import org.apache.log4j.Logger;

import com.wzq.tbmp.service.data.ElasticDataService;


public class SaveDataTask {

	private Logger logger = Logger.getLogger(SaveDataTask.class);

	public void save() {
		ElasticDataService.getInstance().refreshCacheToES();
	}

}

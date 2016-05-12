package com.wzq.tbmp.service;

import com.wzq.tbmp.pojo.SystemParam;

public interface IGlobalConfigService {
	public void runDbMigrate();
	public void initSystemParam();
	public void fixData();
	public boolean addFixFlag(String fixId, String fixDesc);
	public void initQuartzJob();
	public SystemParam getSystemParam(String paramId);
}

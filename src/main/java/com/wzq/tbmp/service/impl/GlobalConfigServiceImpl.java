package com.wzq.tbmp.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.dao.DaoSupport;
import com.wzq.tbmp.pojo.FixFlag;
import com.wzq.tbmp.pojo.SystemParam;
import com.wzq.tbmp.quartz.QuartzManager;
import com.wzq.tbmp.service.IGlobalConfigService;
import com.wzq.tbmp.service.data.fix.Fix20160223Service;
import com.wzq.tbmp.service.data.fix.FixDataService;
import com.wzq.tbmp.util.DateUtil;
import com.googlecode.flyway.core.Flyway;

@Service(value = "globalConfigService")
public class GlobalConfigServiceImpl extends DaoSupport implements IGlobalConfigService {
	private final Logger logger = Logger.getLogger(GlobalConfigServiceImpl.class);
	
	public void runDbMigrate() {
		File file = new File(Constant.TOMCAT_SERVICE_ADDRESS + "/WEB-INF/classes/config/sql");
		if(!file.exists() && !file.isDirectory()){
			file.mkdirs();
		}
		Flyway flyway = new Flyway();
		flyway.setDataSource(this.jdbcTemplate.getDataSource());
		flyway.setInitOnMigrate(true);
		flyway.setLocations("filesystem:"+file.getAbsolutePath());
		flyway.setValidateOnMigrate(false);
		flyway.setOutOfOrder(false);
		flyway.migrate();
	}

	public void initSystemParam() {
		Constant.INIT_PASSWORD = this.getSystemParamValue(Constant.INIT_PASSWORD_KEY);
		Constant.TIME_ZONE = NumberUtils.toInt(this.getSystemParamValue(Constant.TIME_ZONE_KEY));
		DateUtil.setSdfTimeZone();
	}
	
	private String getSystemParamValue(String paramId) {
		return this.getSystemParam(paramId).getValue();
	}
	
	public SystemParam getSystemParam(String paramId) {
		return this.hibernateDao.get(SystemParam.class, paramId);
	}
	
	public void fixData(){
		int count = 0;
		List<FixDataService> fixDataList = new ArrayList<FixDataService>();
		fixDataList.add(new Fix20160223Service());
		for(FixDataService fixData : fixDataList){
			boolean result = fixData.fix();
			if (result) {
				count++;
			}
		}
		logger.info("成功运行"+count+"个补丁");
	}

	@Override
	public boolean addFixFlag(String fixId, String fixDesc) {
		FixFlag fix = this.hibernateDao.get(FixFlag.class, fixId);
		if (fix == null) {
			fix = new FixFlag();
			fix.setFixDesc(fixDesc);
			fix.setFixId(fixId);
			fix.setFixTime(DateUtil.getNow());
			this.hibernateDao.save(fix);
			return false;
		}
		return true;
	}

	@Override
	public void initQuartzJob() {
		QuartzManager.start();
	}
	
}

/**
 * 
 */
package com.wzq.tbmp.service.data.fix;

import org.apache.log4j.Logger;

import com.wzq.tbmp.listener.ServiceLocator;
import com.wzq.tbmp.service.IGlobalConfigService;

/**
 * @author jackyli
 *
 */
public abstract class FixDataService {
	
	protected static final Logger logger = Logger.getLogger(FixDataService.class);
	
	public boolean fix() {
		IGlobalConfigService globalConfigService = ServiceLocator.getBean(IGlobalConfigService.class);
		boolean exist = globalConfigService.addFixFlag(this.getFixId(), this.getFixDesc());
		if (!exist) {
			fixData();
			logger.info("成功执行补丁【"+this.getFixId()+"】");
			return true;
		}
		return false;
	}
	
	protected abstract void fixData();
	
	protected abstract String getFixId();
	
	protected abstract String getFixDesc();
	
}

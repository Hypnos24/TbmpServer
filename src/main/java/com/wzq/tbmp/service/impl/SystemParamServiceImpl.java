package com.wzq.tbmp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzq.tbmp.bean.SystemParamBean;
import com.wzq.tbmp.dao.DaoSupport;
import com.wzq.tbmp.pojo.SystemParam;
import com.wzq.tbmp.service.IGlobalConfigService;
import com.wzq.tbmp.service.ISystemParamService;
import com.wzq.tbmp.util.DateUtil;

@Service(value = "systemParamService")
public class SystemParamServiceImpl extends DaoSupport implements ISystemParamService {

	@Autowired
	private IGlobalConfigService globalConfigService;
	
	public List<SystemParam> querySystemParam() {
		String queryHql = "from SystemParam as t order by t.createTime desc";
		return hibernateDao.queryList(queryHql);
	}

	public void updateSystemParam(SystemParamBean paramBean) {
		SystemParam param = this.hibernateDao.get(SystemParam.class, paramBean.getParamId());
		param.setValue(paramBean.getValue());
		param.setModifyTime(DateUtil.getNow());
		this.hibernateDao.update(param);
		globalConfigService.initSystemParam();
	}

}

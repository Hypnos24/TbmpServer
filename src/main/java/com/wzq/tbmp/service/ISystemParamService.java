package com.wzq.tbmp.service;

import java.util.List;

import com.wzq.tbmp.bean.SystemParamBean;
import com.wzq.tbmp.pojo.SystemParam;

public interface ISystemParamService {
	public List<SystemParam> querySystemParam();
	public void updateSystemParam(SystemParamBean paramBean);
}
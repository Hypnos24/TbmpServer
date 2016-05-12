package com.wzq.tbmp.service;

import com.wzq.tbmp.bean.OperateLogBean;
import com.wzq.tbmp.bean.PageBean;
import com.wzq.tbmp.pojo.ServerUser;

public interface ISystemOperateLogService {
	public void addOperateLog(ServerUser operator, String operateContent, int module);
	public PageBean queryOperateLog(OperateLogBean logBean);
}

package com.wzq.tbmp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wzq.tbmp.bean.OperateLogBean;
import com.wzq.tbmp.bean.PageBean;
import com.wzq.tbmp.dao.DaoSupport;
import com.wzq.tbmp.pojo.OperateLog;
import com.wzq.tbmp.pojo.ServerUser;
import com.wzq.tbmp.service.ISystemOperateLogService;
import com.wzq.tbmp.util.DateUtil;
import com.wzq.tbmp.util.StringUtil;

@Service(value = "systemLogService")
public class SystemOperateLogServiceImpl extends DaoSupport implements ISystemOperateLogService {

	public void addOperateLog(ServerUser operator, String operateContent, int module) {
		OperateLog log = new OperateLog();
		if (operator != null) {
			log.setOperator(operator);
			log.setLogIp(operator.getLastLoginIp());
		}
		log.setOperateTime(DateUtil.getNow());
		log.setOperateContent(operateContent);
		log.setModule(module);
		this.hibernateDao.save(log);
	}

	public PageBean queryOperateLog(OperateLogBean logBean) {
		String queryHql = "from OperateLog as s where 1 = 1";
		Map paramMap = new HashMap();
		if (!StringUtil.isEmpty(logBean.getModule())) {
			queryHql += " and s.module = :module";
			paramMap.put("module", logBean.getModule());
		}
		if(!StringUtil.isEmpty(logBean.getBeginTimeStr())){
			queryHql += " and date_format(s.operateTime,'%Y-%m-%d') >= :beginTime";
			paramMap.put("beginTime", logBean.getBeginTimeStr());
		}
		if(!StringUtil.isEmpty(logBean.getEndTimeStr())){
			queryHql += " and date_format(s.operateTime,'%Y-%m-%d') <= :endTime";
			paramMap.put("endTime", logBean.getEndTimeStr());
		}
		int allRow = this.hibernateDao.getAllRow("select count(*) " + queryHql, paramMap);
		queryHql += " order by s.operateTime desc";
		List list = this.hibernateDao.queryList(queryHql, logBean.getPage(), logBean.getPageSize(), paramMap);
		return new PageBean(list, allRow, logBean.getPage(), logBean.getPageSize());
	}
}

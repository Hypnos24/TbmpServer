package com.wzq.tbmp.service.http.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wzq.tbmp.dao.DaoSupport;
import com.wzq.tbmp.pojo.Project;
import com.wzq.tbmp.service.http.IProjectService;

@Service(value = "projectService")
public class ProjectServiceImpl extends DaoSupport implements IProjectService{

	@Override
	public List<Project> queryAllProject() {
		String queryHql = "from Project where 1 = 1";
		List list = this.hibernateDao.queryList(queryHql);
		return list;
	}

}

package com.wzq.tbmp.service.http.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wzq.tbmp.dao.DaoSupport;
import com.wzq.tbmp.pojo.ServerUser;
import com.wzq.tbmp.service.http.IUserService;
import com.wzq.tbmp.util.StringUtil;

@Service(value = "userService")
public class UserServiceImpl extends DaoSupport implements IUserService{

	@Override
	public ServerUser checkUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerUser login(String username, String password,StringBuffer sb) {
		String queryHql = "from ServerUser as t where t.username = ?";
		List list = this.hibernateDao.queryList(queryHql, username);
		if (list.isEmpty()) {
			sb.append("用户名不存在");
			return null;
		}
		ServerUser user = (ServerUser)list.get(0);
		if (!StringUtil.checkPassword(password, user.getEncryptedPassword(), user.getAssistantPassword())) {
			sb.append("密码错误");
			return null;
		}
		sb.append("登陆成功");
		return user;
	}

}

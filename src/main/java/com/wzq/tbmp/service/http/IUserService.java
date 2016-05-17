package com.wzq.tbmp.service.http;

import com.wzq.tbmp.pojo.ServerUser;

public interface IUserService {
	public ServerUser checkUser(String username, String password);
	public ServerUser login(String username, String password, StringBuffer sb);
	

}

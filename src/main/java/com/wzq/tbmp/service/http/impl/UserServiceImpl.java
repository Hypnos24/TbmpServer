package com.wzq.tbmp.service.http.impl;

import org.springframework.stereotype.Service;


import com.wzq.tbmp.controller.response.ResponseInfo;
import com.wzq.tbmp.service.http.IUserService;

@Service(value = "userService")
public class UserServiceImpl implements IUserService{

	@Override
	public ResponseInfo checkUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}

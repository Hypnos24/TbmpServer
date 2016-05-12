package com.wzq.tbmp.service.http;

import com.wzq.tbmp.controller.response.ResponseInfo;

public interface IUserService {
	public ResponseInfo checkUser(String username, String password);

}

package com.wzq.tbmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.controller.response.ResponseInfo;
import com.wzq.tbmp.controller.response.UserResponse;
import com.wzq.tbmp.pojo.ServerUser;
import com.wzq.tbmp.service.http.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseInfo login(@RequestParam String username, @RequestParam String password) {
		StringBuffer sb = new StringBuffer();
		ServerUser user = userService.login(username, password, sb);
		if(user != null){
			return new UserResponse(Constant.SUCCESS, sb.toString(), user);
		}
		return new ResponseInfo(Constant.FAIL, sb.toString());
	}
	
	@ResponseBody
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseInfo register(@RequestParam String username, @RequestParam String password) {
		ResponseInfo response = new ResponseInfo(username, password);
		return response;
	}
}

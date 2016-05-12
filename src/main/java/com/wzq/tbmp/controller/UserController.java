package com.wzq.tbmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzq.tbmp.controller.response.ResponseInfo;
import com.wzq.tbmp.service.http.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@ResponseBody
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public ResponseInfo login(@RequestParam String username, @RequestParam String password) {
		ResponseInfo response = new ResponseInfo(username, password);
		return response;
	}
}

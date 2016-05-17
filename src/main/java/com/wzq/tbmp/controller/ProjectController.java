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
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 项目摘要
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/summary",method=RequestMethod.POST)
	public ResponseInfo summary() {
		return null;
	}
	
	/**
	 * 项目汇总，包括代办事件数量和通知公告数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/all_details",method=RequestMethod.POST)
	public ResponseInfo allDetails() {
		return null;
	}
	
	/**
	 * 公告通知
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/notices",method=RequestMethod.POST)
	public ResponseInfo notices() {
		return null;
	}
	
	/**
	 * 代办事件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/to_do_list",method=RequestMethod.POST)
	public ResponseInfo toDOList() {
		return null;
	}
	
}

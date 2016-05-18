package com.wzq.tbmp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.controller.response.ResponseInfo;
import com.wzq.tbmp.controller.response.ResponseList;
import com.wzq.tbmp.pojo.Project;
import com.wzq.tbmp.service.http.IProjectService;
import com.wzq.tbmp.service.http.IUserService;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProjectService projectService;
	
	/**
	 * 项目摘要
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/summary",method=RequestMethod.POST)
	public ResponseInfo summary() {
		List<Project> list = projectService.queryAllProject();
		if(list.isEmpty()){
			return new ResponseInfo(Constant.SUCCESS, "列表为空");
		}
		return new ResponseList(Constant.SUCCESS, "获取成功", list);
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

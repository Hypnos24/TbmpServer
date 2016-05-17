package com.wzq.tbmp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.controller.response.ResponseInfo;
import com.wzq.tbmp.controller.response.ResponseList;
import com.wzq.tbmp.pojo.Notice;
import com.wzq.tbmp.service.http.INoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private INoticeService noticeService;
	
	/**
	 * 公告通知
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/notice_list",method=RequestMethod.POST)
	public ResponseInfo notices(@RequestParam String userId) {
		
		List<Notice> list = noticeService.getToDoNotices(userId);
		if(list != null){
			return new ResponseList(Constant.SUCCESS, "查询成功", list);
		}
		return new ResponseInfo(Constant.SUCCESS, "没有新的通知");
	}
	
}

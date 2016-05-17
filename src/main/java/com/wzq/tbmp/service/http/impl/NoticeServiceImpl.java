package com.wzq.tbmp.service.http.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wzq.tbmp.dao.DaoSupport;
import com.wzq.tbmp.pojo.Notice;
import com.wzq.tbmp.service.http.INoticeService;
import com.wzq.tbmp.util.DateUtil;

@Service(value = "noticeService")
public class NoticeServiceImpl extends DaoSupport implements INoticeService{

	@Override
	public List<Notice> getToDoNotices(String userId) {
		long nowTime = DateUtil.getNowUnixTime();
		String queryHql = "from Notice as t where t.userId = ? and t.createTime > ?";
		List notices = this.hibernateDao.queryList(queryHql, userId, nowTime);
		return notices;
	}

}

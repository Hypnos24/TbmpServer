package com.wzq.tbmp.service.http;

import java.util.List;

import com.wzq.tbmp.pojo.Notice;

public interface INoticeService {
	public List<Notice> getToDoNotices(String userId);

}

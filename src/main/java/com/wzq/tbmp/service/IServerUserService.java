package com.wzq.tbmp.service;

import java.util.List;

import com.wzq.tbmp.bean.PageBean;
import com.wzq.tbmp.bean.ServerUserBean;
import com.wzq.tbmp.pojo.Menu;
import com.wzq.tbmp.pojo.ServerUser;


public interface IServerUserService {
	public ServerUser queryServerUser(ServerUserBean userBean, StringBuffer sb);
	public void updateServerUserLastLoginTime(ServerUser user);
	public List<Menu> queryLoginMenu(String userId);
	public List<Menu> queryAllMenu();
	public List<String> queryLoginHrefUrl(List<Menu> menuList);
	public PageBean queryServerUser(ServerUserBean serverUserBean);
	public ServerUser getServerUser(String userId);
	public boolean updateServerUserInfo(ServerUserBean userBean);
	public boolean addServerUser(ServerUserBean userBean);
	public ServerUser updateServerUserPass(ServerUserBean userBean);
	public ServerUser updateServerUserBaseInfo(ServerUserBean userBean);
	public void updateServerUserPassToDefault(String userId);
}

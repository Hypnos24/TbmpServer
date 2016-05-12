package com.wzq.tbmp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.springframework.stereotype.Service;

import com.wzq.tbmp.bean.PageBean;
import com.wzq.tbmp.bean.ServerUserBean;
import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.dao.DaoSupport;
import com.wzq.tbmp.listener.CometListener;
import com.wzq.tbmp.pojo.Menu;
import com.wzq.tbmp.pojo.Role;
import com.wzq.tbmp.pojo.ServerUser;
import com.wzq.tbmp.pojo.UserRole;
import com.wzq.tbmp.pojo.UserRolePK;
import com.wzq.tbmp.service.IServerUserService;
import com.wzq.tbmp.util.DateUtil;
import com.wzq.tbmp.util.GsonUtil;
import com.wzq.tbmp.util.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service(value = "serverUserService")
public class ServerUserServiceImpl extends DaoSupport implements IServerUserService{

	public ServerUser queryServerUser(ServerUserBean userBean, StringBuffer sb) {
		String queryHql = "from ServerUser as t where t.username = ?";
		List list = this.hibernateDao.queryList(queryHql, userBean.getUsername());
		if (list.isEmpty()) {
			sb.append("用户名不存在.");
			return null;
		}
		ServerUser user = (ServerUser)list.get(0);
		if (!StringUtil.checkPassword(userBean.getPassword(), user.getEncryptedPassword(), user.getAssistantPassword())) {
			sb.append("密码错误.");
			return null;
		}
		if (user.getStatus() == ServerUser.Status.AwayCompay) {
			sb.append("你已离职,无权登录.");
			return null;
		}
		return user;
	}
	
	public void updateServerUserLastLoginTime(ServerUser user) {
		user.setLastLoginTime(DateUtil.getNow());
		this.hibernateDao.update(user);
	}
	
	public List<Menu> queryAllMenu() {
		String queryHql = "from Menu as t order by t.sort";
		return this.hibernateDao.queryList(queryHql);
	}
	
	public List<Menu> queryLoginMenu(String userId) {
		String queryHql = "select distinct(t2.pk.menu) from RoleMenu as t2 where t2.pk.role.roleId in " +
		"(select t3.pk.role.roleId from UserRole as t3 where t3.pk.serverUser.userId = ?) order by t2.pk.menu.sort";
		return this.hibernateDao.queryList(queryHql, userId);
	}
	
	public PageBean queryServerUser(ServerUserBean serverUserBean) {
        String queryHql = "from ServerUser as s where s.parent.userId is not null";
        Map paramMap = new HashMap();
        if (!StringUtil.isEmpty(serverUserBean.getName())) {
            queryHql += " and s.name like :name";
            paramMap.put("name", "%" +serverUserBean.getName()+"%" );
        }
        if (!StringUtil.isEmpty(serverUserBean.getUsername())) {
            queryHql += " and s.username like :username";
            paramMap.put("username", "%" +serverUserBean.getUsername()+"%" );
        }
        int allRow = this.hibernateDao.getAllRow("select count(*) " + queryHql, paramMap);
        queryHql += " order by s.createTime desc";
        List list = this.hibernateDao.queryList(queryHql, serverUserBean.getPage(), serverUserBean.getPageSize(), paramMap);
        return new PageBean(list, allRow, serverUserBean.getPage(), serverUserBean.getPageSize());
    }

	public ServerUser getServerUser(String userId) {
		return this.hibernateDao.get(ServerUser.class, userId);
	}
	
	public boolean updateServerUserInfo(ServerUserBean userBean) {
		ServerUser user = this.getServerUser(userBean.getUserId());
		if (user == null) {
			return false;
		}
		user.setName(userBean.getName());
		user.setPhone(userBean.getPhone());
		user.setStatus(userBean.getStatus());
		user.setModifyTime(DateUtil.getNow());
		this.hibernateDao.update(user);
		if (userBean.getOperator().getUserId().equals(Constant.ADMIN_ID)) {
			String queryHql = "select t.pk.role.roleId from UserRole as t where t.pk.serverUser.userId = ? order by t.pk.role.name";
			List<String> oldRoleIds = this.hibernateDao.queryList(queryHql, userBean.getUserId());
			List list = this.hibernateDao.queryList("from UserRole as u where u.pk.serverUser.userId = ?", userBean.getUserId());
			this.hibernateDao.deleteAll(list);
			JsonArray roleIdArray = GsonUtil.parseJsonArray(userBean.getRoleIds());
			for (int i = 0; i < roleIdArray.size(); i++) {
				UserRole ur = new UserRole();
				UserRolePK pk = new UserRolePK();
				pk.setRole(this.hibernateDao.get(Role.class, roleIdArray.get(i).getAsString()));
				pk.setServerUser(user);
				ur.setPk(pk);
				this.hibernateDao.save(ur);
			}
			if (!GsonUtil.toJsonString(oldRoleIds).equals(roleIdArray.toString())) {
				CometEngine engine = CometContext.getInstance().getEngine();
				JsonObject jo = new JsonObject();
				jo.addProperty("userId", user.getUserId());
				jo.addProperty("msg", "你的权限已被更新");
			    engine.sendToAll(CometListener.CHANNEL_LOGOUT_PUSH, jo.toString());
			}
		}
		return true;
	}
	
	public ServerUser updateServerUserPass(ServerUserBean userBean) {
		ServerUser user = this.getServerUser(userBean.getUserId());
		if (!StringUtil.checkPassword(userBean.getOldPassword(), user.getEncryptedPassword(), user.getAssistantPassword())) {
			return null;
		}
		String[] passwords = StringUtil.generatePassword(userBean.getNewPassword());
		user.setAssistantPassword(passwords[1]);
		user.setEncryptedPassword(passwords[0]);
		user.setModifyTime(DateUtil.getNow());
		this.hibernateDao.update(user);
		return user;
	}
	
	public ServerUser updateServerUserBaseInfo(ServerUserBean userBean) {
		ServerUser user = this.getServerUser(userBean.getUserId());
		user.setPhone(userBean.getPhone());
		user.setModifyTime(DateUtil.getNow());
		this.hibernateDao.update(user);
		return user;
	}

	public boolean addServerUser(ServerUserBean userBean) {
		String queryHql = "from ServerUser as t where t.username = ?";
		List list = this.hibernateDao.queryList(queryHql, userBean.getUsername());
		if (!list.isEmpty()) {
			return false;
		}
		ServerUser user = new ServerUser();
		user.setUsername(userBean.getUsername());
		user.setName(userBean.getName());
		user.setPhone(userBean.getPhone());
		user.setModifyTime(DateUtil.getNow());
		user.setParent(userBean.getOperator());
		user.setCreateTime(DateUtil.getNow());
		String[] passwords = StringUtil.generatePassword(Constant.INIT_PASSWORD);
		user.setAssistantPassword(passwords[1]);
		user.setEncryptedPassword(passwords[0]);
		user.setStatus(ServerUser.Status.InCompany);
		this.hibernateDao.save(user);
		JsonArray roleIdArray = GsonUtil.parseJsonArray(userBean.getRoleIds());
		for (int i = 0; i < roleIdArray.size(); i++) {
			UserRole ur = new UserRole();
			UserRolePK pk = new UserRolePK();
			pk.setRole(this.hibernateDao.get(Role.class, roleIdArray.get(i).getAsString()));
			pk.setServerUser(user);
			ur.setPk(pk);
			this.hibernateDao.save(ur);
		}
		return true;
	}
	
	public List<String> queryLoginHrefUrl(List<Menu> menuList) {
		List<String> urlList = this.hibernateDao.queryList("select t.hrefUrl from SubMenu as t where t.parent.menuId is null");
		for (Menu menu : menuList) {
			urlList.add(menu.getHrefUrl());
			if (menu.getLevel() == 1) {
				List list = this.hibernateDao.queryList("select t.hrefUrl from SubMenu as t where t.parent.menuId = ?", menu.getMenuId());
				urlList.addAll(list);
			}
		}
		return urlList;
	}
	
	public void updateServerUserPassToDefault(String userId) {
		ServerUser user = this.getServerUser(userId);
		String[] passwords = StringUtil.generatePassword(Constant.INIT_PASSWORD);
		user.setAssistantPassword(passwords[1]);
		user.setEncryptedPassword(passwords[0]);
		user.setModifyTime(DateUtil.getNow());
		this.hibernateDao.update(user);
	}
}

package com.wzq.tbmp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.springframework.stereotype.Service;

import com.wzq.tbmp.bean.PageBean;
import com.wzq.tbmp.bean.RoleBean;
import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.dao.DaoSupport;
import com.wzq.tbmp.listener.CometListener;
import com.wzq.tbmp.pojo.Menu;
import com.wzq.tbmp.pojo.Role;
import com.wzq.tbmp.pojo.RoleMenu;
import com.wzq.tbmp.pojo.RoleMenuPK;
import com.wzq.tbmp.service.IRoleService;
import com.wzq.tbmp.util.DateUtil;
import com.wzq.tbmp.util.GsonUtil;
import com.wzq.tbmp.util.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service(value = "roleService")
public class RoleServiceImpl extends DaoSupport implements IRoleService{

	public String queryRoleName(String userId) {
		String queryHql = "select t.pk.role.name from UserRole as t where t.pk.serverUser.userId = ?";
		List<String> list = this.hibernateDao.queryList(queryHql, userId);
		String roleName = "";
		for (int i = 0; i < list.size(); i++) {
			roleName += list.get(i);
			if (i < list.size() - 1) {
				roleName += "，";
			}
		}
		return roleName;
	}
	
	public String queryRoleId(String userId) {
		String queryHql = "select t.pk.role.roleId from UserRole as t where t.pk.serverUser.userId = ?";
		List<String> list = this.hibernateDao.queryList(queryHql, userId);
		return GsonUtil.toJsonString(list);
	}
	
	public PageBean queryRole(RoleBean roleBean) {
		String queryHql = "from Role as r where r.serverUser.userId is not null";
		Map paramMap = new HashMap();
		if (!StringUtil.isEmpty(roleBean.getName())) {
			queryHql += " and r.name like :name";
			paramMap.put("name", "%"+roleBean.getName()+"%");
		}
		int allRow = this.hibernateDao.getAllRow("select count(*) " + queryHql, paramMap);
		queryHql += " order by r.createTime desc";
		List list = this.hibernateDao.queryList(queryHql, roleBean.getPage(), roleBean.getPageSize(), paramMap);
		return new PageBean(list, allRow, roleBean.getPage(), roleBean.getPageSize());
	}
	
	public List<String> getMenuIds(String roleId) {
		String queryHql = "select rm.pk.menu.menuId from RoleMenu as rm where rm.pk.role.roleId = ? order by rm.pk.menu.sort";
		return this.hibernateDao.queryList(queryHql, roleId);
	}
	
	public Role getRole(String roleId) {
		return this.hibernateDao.get(Role.class, roleId);
	}
	
	public boolean addRole(RoleBean roleBean) {
		String queryHql = "from Role as r where r.name = ?";
		List list = this.hibernateDao.queryList(queryHql, roleBean.getName());
		if (!list.isEmpty()) {
			return false;
		}
		Role role = new Role();
		role.setCreateTime(DateUtil.getNow());
		role.setDescription(roleBean.getDescription());
		role.setModifyTime(DateUtil.getNow());
		role.setName(roleBean.getName());
		role.setServerUser(roleBean.getOperator());
		this.hibernateDao.save(role);
		JsonArray menuIdArray = GsonUtil.parseJsonArray(roleBean.getMenuIds());
		for (int i = 0; i < menuIdArray.size(); i++) {
			RoleMenu rm = new RoleMenu();
			RoleMenuPK pk = new RoleMenuPK();
			pk.setMenu(this.hibernateDao.get(Menu.class, menuIdArray.get(i).getAsString()));
			pk.setRole(role);
			rm.setPk(pk);
			this.hibernateDao.save(rm);
		}
		return true;
	}
	
	public boolean updateRole(RoleBean roleBean) {
		String queryHql = "from Role as r where r.name = ? and r.roleId <> ?";
		List list = this.hibernateDao.queryList(queryHql, roleBean.getName(), roleBean.getRoleId());
		if (!list.isEmpty()) {
			return false;
		}
		Role role = this.hibernateDao.get(Role.class, roleBean.getRoleId());
		role.setDescription(roleBean.getDescription());
		role.setModifyTime(DateUtil.getNow());
		role.setName(roleBean.getName());
		this.hibernateDao.update(role);
		// 获取该角色绑定的权限
		List<String> oldMenuIds = this.getMenuIds(role.getRoleId());
		queryHql = "from RoleMenu as rm where rm.pk.role.roleId = ?";
		list = this.hibernateDao.queryList(queryHql, roleBean.getRoleId());
		this.hibernateDao.deleteAll(list);
		JsonArray menuIdArray = GsonUtil.parseJsonArray(roleBean.getMenuIds());
		for (int i = 0; i < menuIdArray.size(); i++) {
			RoleMenu rm = new RoleMenu();
			RoleMenuPK pk = new RoleMenuPK();
			pk.setMenu(this.hibernateDao.get(Menu.class, menuIdArray.get(i).getAsString()));
			pk.setRole(role);
			rm.setPk(pk);
			this.hibernateDao.save(rm);
		}
		// 如果角色绑定的权限发生变化，找出该角色绑定的用户，通知重新登录
		if (!GsonUtil.toJsonString(oldMenuIds).equals(menuIdArray.toString())) {
			queryHql = "select ur.pk.serverUser.userId from UserRole as ur where ur.pk.role.roleId = ?";
			List<String> logoutIds = this.hibernateDao.queryList(queryHql, roleBean.getRoleId());
			CometEngine engine = CometContext.getInstance().getEngine();
			for (String userId : logoutIds) {
				JsonObject jo = new JsonObject();
				jo.addProperty("userId", userId);
				jo.addProperty("msg", "你的权限已被更新");
			    engine.sendToAll(CometListener.CHANNEL_LOGOUT_PUSH, jo.toString());
			}
		}
		return true;
	}
	
	public List<Role> queryRole(String userId) {
		if (userId.equals(Constant.ADMIN_ID)) {
			String queryHql = "from Role as r where r.serverUser.userId is not null order by r.name";
			return this.hibernateDao.queryList(queryHql);
		} else {
			String queryHql = "from Role as r where r.roleId = ?";
			return this.hibernateDao.queryList(queryHql, Constant.DEFAULT_ROLE_ID);
		}
	}
	
}

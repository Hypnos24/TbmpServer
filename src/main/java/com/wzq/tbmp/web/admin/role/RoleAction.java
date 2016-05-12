package com.wzq.tbmp.web.admin.role;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.factory.annotation.Autowired;

import com.wzq.tbmp.bean.AjaxBean;
import com.wzq.tbmp.bean.RoleBean;
import com.wzq.tbmp.pojo.Menu;
import com.wzq.tbmp.service.IRoleService;
import com.wzq.tbmp.service.IServerUserService;
import com.wzq.tbmp.util.StringUtil;
import com.wzq.tbmp.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class RoleAction extends AdminBaseAction implements ModelDriven<RoleBean>{
	private static final long serialVersionUID = -3113561808050453434L;

	private RoleBean roleBean = new RoleBean();
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IServerUserService serverUserService;
	
	public RoleBean getModel() {
		return roleBean;
	}

	@Action("index")
	public String index() {
		return SUCCESS;
	}
	
	@Action("list")
	public String list() {
		this.setAttrToRequest("pageBean", this.roleService.queryRole(roleBean));
		return SUCCESS;
	}
	
	@Action("add_input")
	public String addInput() {
		List<Menu> menuList = this.serverUserService.queryAllMenu();
		String menuHtml = getMenuHtml(menuList, null);
		this.setAttrToRequest("roleMenuHtml", menuHtml);
		return SUCCESS;
	}
	
	@Action("add")
	public String add() {
		roleBean.setOperator(this.getSessionServerUser());
		boolean result = this.roleService.addRole(roleBean);
		if (result) {
			ajaxBean = new AjaxBean(true, "提交成功.");
		} else {
			ajaxBean = new AjaxBean(false, "该角色名称已经存在.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
	@Action("edit_input")
	public String editInput() {
		List<Menu> menuList = this.serverUserService.queryAllMenu();
		List<String> menuIds = this.roleService.getMenuIds(roleBean.getRoleId());
		String menuHtml = getMenuHtml(menuList, menuIds);
		this.setAttrToRequest("roleMenuHtml", menuHtml);
		this.setAttrToRequest("role", this.roleService.getRole(roleBean.getRoleId()));
		return SUCCESS;
	}
	
	private String getMenuHtml(List<Menu> menuList, List<String> menuIds) {
		StringBuffer sb = new StringBuffer();
		LinkedHashMap<Menu, List<Menu>> map = StringUtil.turnListToMap(menuList);
		for (Map.Entry<Menu, List<Menu>> kv : map.entrySet()) {
			String checked = "";
			if (menuIds != null && menuIds.contains(kv.getKey().getMenuId())) {
				checked = "checked";
			}
			sb.append("<dd>");
			sb.append("<div class='title'>");
			sb.append("<label class='checkbox inline'><input type='checkbox' id='"+kv.getKey().getMenuId()+"' "+checked+" onclick='selectAll(this);'/>" + kv.getKey().getText() + "</label>");
			sb.append("</div>");
			sb.append("<ul class='menuson' style='display:block;'>");
			for (Menu menu : kv.getValue()) {
				checked = "";
				if (menuIds != null && menuIds.contains(menu.getMenuId())) {
					checked = "checked";
				}
				sb.append("<li><cite></cite><label class='checkbox inline'><input type='checkbox' id='"+menu.getMenuId()+"' "+checked+" onclick='selectOne(this);'/>"+menu.getText()+"</label><i></i></li>");
			}
			sb.append("</ul></dd>");
		}
		return sb.toString();
	}
	
	@Action("edit")
	public String edit() {
		boolean result = this.roleService.updateRole(roleBean);
		if (result) {
			ajaxBean = new AjaxBean(true, "提交成功.");
		} else {
			ajaxBean = new AjaxBean(false, "该角色名称已经存在.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
}

package com.wzq.tbmp.web.admin.user;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.factory.annotation.Autowired;

import com.wzq.tbmp.bean.AjaxBean;
import com.wzq.tbmp.bean.PageBean;
import com.wzq.tbmp.bean.ServerUserBean;
import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.pojo.ServerUser;
import com.wzq.tbmp.service.IRoleService;
import com.wzq.tbmp.service.IServerUserService;
import com.wzq.tbmp.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class UserAction extends AdminBaseAction implements ModelDriven<ServerUserBean> {
	private static final long serialVersionUID = 1323581861086668462L;

	@Autowired
    private IServerUserService serverUserService;

	@Autowired
	private IRoleService roleService;

    private ServerUserBean serverUserBean = new ServerUserBean();

    public ServerUserBean getModel() {
		return serverUserBean;
	}

    @Action("index")
    public String index() {
        return SUCCESS;
    }

    @Action("list")
    public String list() {
        PageBean pageBean = this.serverUserService.queryServerUser(serverUserBean);
		for (int i = 0; i < pageBean.getList().size(); i++) {
			ServerUser user = (ServerUser)pageBean.getList().get(i);
			user.setRoleName(this.roleService.queryRoleName(user.getUserId()));
		}
		this.setAttrToRequest("pageBean", pageBean);
        return SUCCESS;
    }
    
    @Action("edit_input")
    public String editInput() {
    	ServerUser user = this.serverUserService.getServerUser(serverUserBean.getUserId());
    	if (this.getSessionServerUser().getUserId().equals(Constant.ADMIN_ID)) {
    		this.setAttrToRequest("roleIds", this.roleService.queryRoleId(serverUserBean.getUserId()));
        	this.setAttrToRequest("roleList", this.roleService.queryRole(this.getSessionServerUser().getUserId()));
        	this.setAttrToRequest("isAdmin", true);
    	} else {
    		this.setAttrToRequest("isAdmin", false);
    		user.setRoleName(this.roleService.queryRoleName(user.getUserId()));
    	}
    	this.setAttrToRequest("user", user);
        return SUCCESS;
    }
    
    @Action("edit")
   	public String edit() {
    	serverUserBean.setOperator(this.getSessionServerUser());
        boolean result = this.serverUserService.updateServerUserInfo(serverUserBean);
        if (result) {
   			ajaxBean = new AjaxBean(true, "编辑成功.");
   		} else {
   			ajaxBean = new AjaxBean(false, "该用户不存在.");
   		}
   		this.ajaxWrite(ajaxBean);
   		return null;
   	}
    
    @Action("add_input")
    public String addInput() {
    	this.setAttrToRequest("roleList", this.roleService.queryRole(this.getSessionServerUser().getUserId()));
        return SUCCESS;
    }
    
    @Action("add")
   	public String add() {
    	serverUserBean.setOperator(this.getSessionServerUser());
        boolean result = this.serverUserService.addServerUser(serverUserBean);
        if (result) {
   			ajaxBean = new AjaxBean(true, "新增成功.");
   		} else {
   			ajaxBean = new AjaxBean(false, "该用户名已存在.");
   		}
   		this.ajaxWrite(ajaxBean);
   		return null;
   	}
    
    @Action("reset_pass")
	public String resetPass() {
    	this.serverUserService.updateServerUserPassToDefault(serverUserBean.getUserId());
    	ajaxBean = new AjaxBean(true, "密码成功重置为系统初始化密码" + Constant.INIT_PASSWORD);
		this.ajaxWrite(ajaxBean);
		return null;
	}
    
}

package com.wzq.tbmp.interceptor;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.pojo.ServerUser;
import com.wzq.tbmp.util.StringUtil;
import com.wzq.tbmp.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class AdminAuthority extends Authority {
	private static final long serialVersionUID = 595626559058339723L;
	private final Logger logger = Logger.getLogger(AdminAuthority.class);

	/**
	 * 默认构造器
	 */
	public AdminAuthority() {
		this.setAuthorityUrl("admin_login");// 设置返回的配置路径
	}

	@Override
	public boolean authority(ActionInvocation AI) {
		// 取得请求相关的ActionContext实例
		ActionContext ctx = AI.getInvocationContext();
		// 取出名为user的Session属性
		String requestUrl = ServletActionContext.getRequest().getRequestURI();
		ServerUser loginUser = (ServerUser)ctx.getSession().get(AdminBaseAction.SESSION_USERINFO_KEY);
		// 如果有登陆,通过认证
		if (loginUser != null) {
			List<String> urlList = (List<String>)ctx.getSession().get("urlList");
			boolean identify = this.identifyUrl(requestUrl, urlList);
			if (!identify) {
				logger.info("访问令牌错误");
				this.setAuthorityUrl("visit_limit");
				return false;
			}
			if (requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/welcome") ||
					requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/main") ||
					requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/edit_pass") ||
					requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/top") ||
					requestUrl.equals(Constant.PRO_CTX_VALUE + "/web/admin/edit_info")) {
				return true;
			}
			if (StringUtil.checkPassword(Constant.INIT_PASSWORD, loginUser.getEncryptedPassword(), loginUser.getAssistantPassword())) {
				logger.info("必须修改密码");
				this.setAuthorityUrl("admin_welcome");
				return false;
			}
			return true;
		} else {
			logger.info("登录超时,返回到登录界面");
			this.setAuthorityUrl("admin_login");
			return false;
		}
	}
	
	private boolean identifyUrl(String requestUrl, List<String> urlList) {
		for (String url : urlList) {
			if (requestUrl.equals(Constant.PRO_CTX_VALUE + "/" + url)) {
				return true;
			}
		}
		return false;
	}
	
}

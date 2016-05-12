package com.wzq.tbmp.web.admin.system.dictionary;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.factory.annotation.Autowired;

import com.wzq.tbmp.bean.AjaxBean;
import com.wzq.tbmp.bean.DictionaryParamBean;
import com.wzq.tbmp.service.IDictionaryParamService;
import com.wzq.tbmp.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class DictionaryParamAction extends AdminBaseAction implements ModelDriven<DictionaryParamBean> {

	private static final long serialVersionUID = 7749074010189763666L;

	private DictionaryParamBean paramBean = new DictionaryParamBean();
	
	@Autowired
	private IDictionaryParamService dictionaryParamService;
	
	
	public DictionaryParamBean getModel() {
		return paramBean;
	}
	
	@Action("index")
	public String index() {
		return SUCCESS;
	}
	
	@Action("list")
	public String list() {
		this.setAttrToRequest("module", paramBean.getModule());
		this.setAttrToRequest("pageBean", this.dictionaryParamService.queryDictionaryParam(paramBean));
		return SUCCESS;
	}
	
	@Action("edit")
	public String edit() {
		boolean result = this.dictionaryParamService.updateDictionaryParam(paramBean);
		if (result) {
			ajaxBean = new AjaxBean(true, "保存成功.");
		} else {
			ajaxBean = new AjaxBean(false, "该字典已存在.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
	@Action("add")
	public String add() {
		StringBuffer sb = new StringBuffer();
		boolean result = this.dictionaryParamService.addDictionaryParam(paramBean, sb);
		ajaxBean = new AjaxBean(result, sb.toString());
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
}

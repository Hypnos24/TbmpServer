package com.wzq.tbmp.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_operate_log")
@Cache(region = "tbmpHibernateCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperateLog implements Serializable {
	private static final long serialVersionUID = 7380301920408544875L;
	private String logId;
	private Date operateTime;
	private ServerUser operator;
	private String operateContent;
	private int module;
	private String logIp;
	
	public class Module {
		public static final int UserLoginOut = 1000;
	}

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "log_id_")
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "operate_time_")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id_")
	public ServerUser getOperator() {
		return operator;
	}

	public void setOperator(ServerUser operator) {
		this.operator = operator;
	}

	@Column(name = "operate_content_")
	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	@Column(name = "log_ip_")
	public String getLogIp() {
		return logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	
	@Column(name = "module_")
	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

}

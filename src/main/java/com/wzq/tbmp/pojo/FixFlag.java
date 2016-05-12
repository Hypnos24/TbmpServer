package com.wzq.tbmp.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_fix_flag")
@Cache(region = "tbmpHibernateCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class FixFlag implements Serializable {
	private static final long serialVersionUID = -5161185526591854185L;
	private String fixId;
	private String fixDesc;
	private Date fixTime;

	@GenericGenerator(name = "generator", strategy = "assigned")
	@Id
	@Column(name = "fix_id_")
	public String getFixId() {
		return fixId;
	}

	public void setFixId(String fixId) {
		this.fixId = fixId;
	}

	@Column(name = "fix_time_")
	public Date getFixTime() {
		return fixTime;
	}

	public void setFixTime(Date fixTime) {
		this.fixTime = fixTime;
	}

	@Column(name = "fix_desc_")
	public String getFixDesc() {
		return fixDesc;
	}

	public void setFixDesc(String fixDesc) {
		this.fixDesc = fixDesc;
	}

}

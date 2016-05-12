package com.wzq.tbmp.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_dictionary_param")
@Cache(region = "tbmpHibernateCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryParam implements Serializable {
	private static final long serialVersionUID = 592552220777016262L;
	private DictionaryParamPK pk;
	private String value;
	private Date createTime;
	private Date modifyTime;
	private int sort;

	public class Module {
		public static final int OPERATOR = 1;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "index", column = @Column(name = "index_")),
			@AttributeOverride(name = "module", column = @Column(name = "module_")) })
	public DictionaryParamPK getPk() {
		return pk;
	}

	public void setPk(DictionaryParamPK pk) {
		this.pk = pk;
	}

	@Column(name = "value_")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "create_time_")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modify_time_")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "sort_")
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}

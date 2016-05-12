package com.wzq.tbmp.pojo;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_role_menu")
@Cache(region = "tbmpHibernateCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleMenu implements Serializable{
	private static final long serialVersionUID = -3262032370296478702L;
	private RoleMenuPK pk;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "role", column = @Column(name = "role_id_")),
			@AttributeOverride(name = "menu", column = @Column(name = "menu_id_")) })
	public RoleMenuPK getPk() {
		return pk;
	}

	public void setPk(RoleMenuPK pk) {
		this.pk = pk;
	}
	
}

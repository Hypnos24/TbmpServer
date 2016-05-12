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
@Table(name = "t_user_role")
@Cache(region = "tbmpHibernateCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserRole implements Serializable{
	private static final long serialVersionUID = -232044930064003506L;
	private UserRolePK pk;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "serverUser", column = @Column(name = "user_id_")),
			@AttributeOverride(name = "role", column = @Column(name = "role_id_")) })
	public UserRolePK getPk() {
		return pk;
	}

	public void setPk(UserRolePK pk) {
		this.pk = pk;
	}
	
}

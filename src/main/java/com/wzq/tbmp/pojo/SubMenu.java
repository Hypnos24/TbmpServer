package com.wzq.tbmp.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_sub_menu")
@Cache(region = "tbmpHibernateCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubMenu implements Serializable {
	private static final long serialVersionUID = -3980673168104414774L;
	private String menuId;
	private Menu parent;
	private int level;
	private String hrefUrl;

	@GenericGenerator(name = "generator", strategy = "assigned")
	@Id
	@Column(name = "menu_id_")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id_")
	public Menu getParent() {
		return parent;
	}
	
	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@Column(name = "level_")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "href_url_")
	public String getHrefUrl() {
		return hrefUrl;
	}

	public void setHrefUrl(String hrefUrl) {
		this.hrefUrl = hrefUrl;
	}

}

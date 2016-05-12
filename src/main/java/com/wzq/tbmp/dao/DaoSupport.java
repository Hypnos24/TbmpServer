package com.wzq.tbmp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DaoSupport {
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected HibernateDao hibernateDao;
}

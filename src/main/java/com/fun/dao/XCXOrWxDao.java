package com.fun.dao;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fun.bean.XCXOrWx;
@Repository
public class XCXOrWxDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public XCXOrWx selectXCXOrWxByWxOpenId(XCXOrWx xcxorwx){
		String sql = "select * from XCX_Or_Wx_userinfo where xcx_OpenId=?";
		RowMapper<XCXOrWx> rowMapper = new BeanPropertyRowMapper<>(XCXOrWx.class);
		XCXOrWx pic = null;
		try {
			pic = jdbcTemplate.queryForObject(sql, new Object[]{xcxorwx.getXcx_OpenId()}, rowMapper );
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return pic;
	}
}

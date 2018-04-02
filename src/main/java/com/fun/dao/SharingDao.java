package com.fun.dao;


import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fun.bean.Sharing;

@Repository
public class SharingDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public Sharing selectSharing(Integer XID){
		String sql = "select * from XCX__Group_sharing where XID=?";
		RowMapper<Sharing> rowMapper = new BeanPropertyRowMapper<>(Sharing.class);
		Sharing sharing = jdbcTemplate.queryForObject(sql, new Object[]{XID}, rowMapper );
		return sharing;
	}
	
	public int insertSharing(Sharing sharing){
		String sql = "insert into XCX__Group_sharing(openID,Group_xid,sharingTime) values(?,?,?)";
		Object[] args = {sharing.getOpenID(),sharing.getGroup_xid(),sharing.getSharingTime()};
		int count = jdbcTemplate.update(sql,args);
		return count;
	}
}

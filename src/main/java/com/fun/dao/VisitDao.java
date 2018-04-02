package com.fun.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fun.bean.Project;
import com.fun.bean.Visit;

@Repository
public class VisitDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public int insertVisit(Visit visit){
		String sql = "insert into XCX_Group_visit(Group_XID,openID,nickName,avatarUrl,VisitDate,Visitmessage,WeChatGroup) values(?,?,?,?,?,?,?);";
		/*KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				// TODO Auto-generated method stub
				int i = 0;
				Date visitDate = visit.getVisitDate();
				java.sql.Date date = new java.sql.Date(visitDate.getTime());
				java.sql.PreparedStatement ps = conn.prepareStatement(sql);
				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(++i, visit.getGroup_XID());
				ps.setString(++i, visit.getOpenID());
				ps.setString(++i, visit.getNickName());
				ps.setString(++i, visit.getAvatarUrl());
				ps.setDate(++i, date);
				ps.setString(++i, visit.getVisitmessage());
				ps.setString(++i, visit.getWeChatGroup());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();*/
		Object[] ags = {visit.getGroup_XID(),visit.getOpenID(),visit.getNickName(),visit.getAvatarUrl(),visit.getVisitDate(),visit.getVisitmessage(),visit.getWeChatGroup()};
		int count = jdbcTemplate.update(sql,ags);
		return count;
	}
	
	public Visit selectVisitByGroupOrOpenId(Integer Group_XID,String openId){
		String sql = "select * from XCX_Group_visit where Group_XID=? and openID=?";
		RowMapper<Visit> rowMapper = new BeanPropertyRowMapper<>(Visit.class);
		Visit visit = null;
		try {
			visit = jdbcTemplate.queryForObject(sql, new Object[]{Group_XID,openId}, rowMapper);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error("selectVisitByGroupOrOpenId -> 无数据，可以新增");
			
		}
		return visit;
	}
	
	public List<Visit> selectVisitByGroup_XID(Integer Group_XID){
		String sql = "select * from XCX_Group_visit where Group_XID=?";
		RowMapper<Visit> rowMapper = new BeanPropertyRowMapper<>(Visit.class);
		List<Visit> query = jdbcTemplate.query(sql, new Object[]{Group_XID}, rowMapper);
		return query;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int saveVisitByIsNot(final Visit visit){
		Integer count = jdbcTemplate.execute("{call Proc_Save_Member(?,?,?,?,?,?)}",new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)throws SQLException {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(visit.getVisitDate());
				cs.setInt("Group_XID",visit.getGroup_XID());
				cs.setString("openID",visit.getOpenID());
//				cs.setString("nickName",visit.getNickName());
//				cs.setString("avatarUrl",visit.getAvatarUrl());
				cs.setString("VisitDate",dateString);
				cs.setString("Visitmessage",visit.getVisitmessage());
				cs.setString("WeChatGroup",visit.getWeChatGroup());
				cs.registerOutParameter("XID",Types.INTEGER);// 注册输出参数的类型
				cs.execute();
				
				return cs.getInt("XID");
			}
					
					
		});
		return count;
	}
}

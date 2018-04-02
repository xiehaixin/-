package com.fun.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fun.bean.Sharing;
import com.fun.bean.Userinfo;
@Repository
public class UserinfoDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public int insertUserinfo(Userinfo userinfo){
		String sql = "insert into liteapp_userinfo_subscribe(openId,nickName,gender,language,city,province,country,avatarUrl,createTime,project_id) values(?,?,?,?,?,?,?,?,?,?);";
		Object[] args = {userinfo.getOpenId(),userinfo.getNickName(),userinfo.getGender(),userinfo.getLanguage(),userinfo.getCity(),userinfo.getProvince(),userinfo.getCountry(),userinfo.getAvatarUrl(),userinfo.getCreateTime(),userinfo.getProject_id()};
		int count = jdbcTemplate.update(sql,args);
		return count;
	}
	
	public Userinfo selectUserinfo(String openId){
		String sql = "select * from liteapp_userinfo_subscribe where openId=?";
		RowMapper<Userinfo> rowMapper = new BeanPropertyRowMapper<>(Userinfo.class);
		logger.info("sdaf = "+openId);
		try {
			Userinfo userinfo = jdbcTemplate.queryForObject(sql, new Object[]{openId}, rowMapper );
			logger.info("ewrerererer = "+userinfo);
			return userinfo;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("数据库已有openId");
		}
		return null;
	}
	
	/*public Userinfo selectUserinfoByOpenId(String openId){
		String sql = "select * from liteapp_userinfo_subscribe where openId=?";
		RowMapper<Userinfo> rowMapper = new BeanPropertyRowMapper<>(Userinfo.class);
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer saveUserinfoByIsNot(final Userinfo userinfo){
		int count = jdbcTemplate.execute("{call Proc_Save_UserInfo(?,?,?,?,?,?,?,?,?,?,?)}",new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs)throws SQLException {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(userinfo.getCreateTime());
				cs.setString("openId",userinfo.getOpenId());
				cs.setString("nickName",userinfo.getNickName());
				cs.setString("gender",userinfo.getGender());
				cs.setString("language",userinfo.getLanguage());
				cs.setString("city",userinfo.getCity());
				cs.setString("province",userinfo.getProvince());
				cs.setString("country",userinfo.getCountry());
				cs.setString("avatarUrl",userinfo.getAvatarUrl());
				cs.setString("createTime",dateString);
				cs.setInt("project_id",userinfo.getProject_id());
				cs.registerOutParameter("id",Types.INTEGER);// 注册输出参数的类型
				cs.execute();
				
				return cs.getInt("id");
			}
					
					
		});
		return count;
	}
}

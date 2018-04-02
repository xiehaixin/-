package com.fun.dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fun.bean.Project;
import com.fun.bean.Userinfo;

@Repository
public class ProjectDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public List<Project> selectProject(Integer id){
		String sql = "select p.*,s.* from XCX_Group_Project p,XCX__Group_sharing s where p.XID=s.Group_xid and p.XID=?";
		
		List<Project> query = jdbcTemplate.query(sql,new Object[]{id}, new BeanPropertyRowMapper<>(Project.class));
		return query;
	}
	
	public int insertProject(final Project project){
		final String sql="insert into XCX_Group_Project(sType,sName,sContent,sInitiator,CreateTime,WeChatGroup,signature) values(?,?,?,?,?,?,?)";
		Object[] args = {project.getsType(),project.getsName(),project.getsContent(),project.getsInitiator(),project.getCreateTime(),project.getWeChatGroup(),project.getSignature()};
		KeyHolder keyHolder = new GeneratedKeyHolder();
//		jdbcTemplate.update(sql,args,keyHolder);
		jdbcTemplate.update(
                new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection conn)
							throws SQLException {
						// TODO Auto-generated method stub
						int i = 0;
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String date = formatter.format(project.getCreateTime());
						java.sql.PreparedStatement ps = conn.prepareStatement(sql); 
						ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setString(++i, project.getsType());
						ps.setString(++i, project.getsName());
						ps.setString(++i, project.getsContent());
						ps.setString(++i, project.getsInitiator());
						ps.setString(++i,date);
						ps.setString(++i, project.getWeChatGroup());
						ps.setString(++i, project.getSignature());
						return ps;
					}
				},
                keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public List<Project> selectProjectAllByOpenId(String openId){
		String sql = "select pro.*,pic.PIC_URL,(select COUNT(*) from XCX_Group_visit where Group_XID=pro.XID) 'count' from XCX_Group_Project as pro left join XCX_Group_PIC as pic on pic.group_XID=pro.XID where sInitiator=? order by CreateTime Desc";
		List<Project> query = jdbcTemplate.query(sql,new Object[]{openId}, new BeanPropertyRowMapper<>(Project.class));
		return query;
	}
	
	public Project selectProjectByXID(Integer XID){
		String sql = "select * from XCX_Group_Project where XID = ?";
		RowMapper<Project> rowMapper = new BeanPropertyRowMapper<>(Project.class);
		Project project = jdbcTemplate.queryForObject(sql, new Object[]{XID}, rowMapper);
		return project;
	}
}

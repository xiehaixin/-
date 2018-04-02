package com.fun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fun.bean.PIC;

@Repository
public class PICDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public int insertPIC(final PIC pic){
		
		final String sql = "insert into XCX_Group_PIC(group_XID,PIC_iType,PIC_URL,CreateTime,Creater) values(?,?,?,?,?)";
//		Object[] args = {pic.getGroup_XID(),pic.getPIC_iType(),pic.getPIC_URL(),pic.getCreateTime(),pic.getCreater()};
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
                new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection conn)
							throws SQLException {
						// TODO Auto-generated method stub
						int i = 0;
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String date = formatter.format(pic.getCreateTime());
						java.sql.PreparedStatement ps = conn.prepareStatement(sql); 
						ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setInt(++i, pic.getGroup_XID());
						ps.setInt(++i, pic.getPIC_iType());
						ps.setString(++i, pic.getPIC_URL());
						ps.setString(++i,date);
						ps.setString(++i, pic.getCreater());
						return ps;
					}
				},
                keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public PIC selectPICByGroup_XID(Integer XID){
		String sql = "select * from XCX_Group_PIC where group_XID=?";
		RowMapper<PIC> rowMapper = new BeanPropertyRowMapper<>(PIC.class);
		PIC pic = null;
		try {
			pic = jdbcTemplate.queryForObject(sql, new Object[]{XID}, rowMapper );
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return pic;
	}
}

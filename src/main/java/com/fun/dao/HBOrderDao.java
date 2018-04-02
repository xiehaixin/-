package com.fun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fun.bean.HBOrder;
import com.fun.bean.PIC;

@Repository
public class HBOrderDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public Integer insertHBOrder(final HBOrder hbOrder){
		final String sql = "insert into XCX_HB_Order(appid,mch_id,nonce_str,out_trade_no,total_fee,spbill_create_ip,send_name,total_num,wishing,act_name,remark) values(?,?,?,?,?,?,?,?,?,?,?)";
//		Object[] args = {pic.getGroup_XID(),pic.getPIC_iType(),pic.getPIC_URL(),pic.getCreateTime(),pic.getCreater()};
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
                new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection conn)
							throws SQLException {
						// TODO Auto-generated method stub
						int i = 0;
						/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String date = formatter.format(hbOrder.getCreateTime());*/
						java.sql.PreparedStatement ps = conn.prepareStatement(sql); 
						ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setString(++i, hbOrder.getAppid());
						ps.setInt(++i, hbOrder.getMch_id());
						ps.setString(++i, hbOrder.getNonce_str());
						ps.setString(++i, hbOrder.getOut_trade_no());
						ps.setInt(++i, hbOrder.getTotal_fee());
						ps.setString(++i,hbOrder.getSpbill_create_ip());
						ps.setString(++i,hbOrder.getSend_name());
						ps.setInt(++i, hbOrder.getTotal_num());
						ps.setString(++i,hbOrder.getWishing());
						ps.setString(++i, hbOrder.getAct_name());
						ps.setString(++i, hbOrder.getRemark());
						return ps;
					}
				},
                keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public HBOrder selectPayConfig(String out_trade_no){
		String sql = "select send_name,total_num,wishing,spbill_create_ip from XCX_HB_Order where out_trade_no =?";
		RowMapper<HBOrder> rowMapper = new BeanPropertyRowMapper<>(HBOrder.class);
		HBOrder pic = null;
		try {
			pic = jdbcTemplate.queryForObject(sql, new Object[]{out_trade_no}, rowMapper );
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return pic;
	}
}

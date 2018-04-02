package com.fun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fun.bean.WxPayNotifyModel;
@Repository
public class WxPayNotifyModelDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public int insertNotifyOrder(final WxPayNotifyModel model){
		final String sql = "insert into XCX_notifyOrder(appid,bank_type,cash_fee,fee_type,is_subscribe,mch_id,nonce_str,openid,out_trade_no,result_code,return_code,sign,time_end,total_fee,trade_type,transaction_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
						String date = formatter.format(pic.getCreateTime());*/
						java.sql.PreparedStatement ps = conn.prepareStatement(sql); 
						ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setString(++i, model.getAppid());
						ps.setString(++i, model.getBankType());
						ps.setString(++i, model.getCashFee());
						ps.setString(++i, model.getFeeType());
						ps.setString(++i, model.getIsSubscribe());
						ps.setString(++i, model.getMchId());
						ps.setString(++i, model.getNonceStr());
						ps.setString(++i, model.getOpenid());
						ps.setString(++i, model.getOutTradeNo());
						ps.setString(++i, model.getResultCode());
						ps.setString(++i, model.getReturnCode());
						ps.setString(++i, model.getSign());
						ps.setString(++i, model.getTimeEnd());
						ps.setString(++i, model.getTotalFee());
						ps.setString(++i, model.getTradeType());
						ps.setString(++i, model.getTransactionId());
						return ps;
					}
				},
                keyHolder);
		return keyHolder.getKey().intValue();
	}
	
}

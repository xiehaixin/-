package com.fun.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fun.bean.HBOrder;
import com.fun.dao.HBOrderDao;

@Service
public class HBOrderService {

	@Resource
	HBOrderDao hbOrderDao;
	
	public Integer addHBOrder(final HBOrder hbOrder){
		return hbOrderDao.insertHBOrder(hbOrder);
	}
	
	public HBOrder findPayConfig(String out_trade_no){
		return hbOrderDao.selectPayConfig(out_trade_no);
	}
}

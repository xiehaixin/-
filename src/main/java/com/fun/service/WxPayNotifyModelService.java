package com.fun.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fun.bean.WxPayNotifyModel;
import com.fun.dao.WxPayNotifyModelDao;

@Service
public class WxPayNotifyModelService {

	@Resource
	WxPayNotifyModelDao modelDao;
	
	
	public int addNotifyOrder(WxPayNotifyModel model){
		return modelDao.insertNotifyOrder(model);
	}
}

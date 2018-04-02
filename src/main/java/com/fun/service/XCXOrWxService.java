package com.fun.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fun.bean.XCXOrWx;
import com.fun.dao.XCXOrWxDao;

@Service
public class XCXOrWxService {

	@Resource
	XCXOrWxDao xcxOrWxDao;
	
	public XCXOrWx addXCXOrWxByWxOpenId(XCXOrWx xcxorwx){
		return xcxOrWxDao.selectXCXOrWxByWxOpenId(xcxorwx);
	}
}

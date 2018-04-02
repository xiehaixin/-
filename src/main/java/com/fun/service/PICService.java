package com.fun.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fun.bean.PIC;
import com.fun.dao.PICDao;
@Service
public class PICService {
	
	@Resource
	PICDao picDao;

	
	public int addPIC(final PIC pic){
		return picDao.insertPIC(pic);
	}
	
	public PIC findPICByGroup_XID(Integer XID){
		return picDao.selectPICByGroup_XID(XID);
	}
}

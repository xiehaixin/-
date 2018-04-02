package com.fun.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fun.bean.Visit;
import com.fun.dao.VisitDao;

@Service
public class VisitService {

	@Resource
	VisitDao visitDao;
	
	public int addVisit(Visit visit){
		return visitDao.insertVisit(visit);
	}
	
	public Visit findVisitByGroupOrOpenId(Integer Group_XID,String openId){
		return visitDao.selectVisitByGroupOrOpenId(Group_XID, openId);
	}
	
	public List<Visit> findVisitByGroup_XID(Integer Group_XID){
		return visitDao.selectVisitByGroup_XID(Group_XID);
	}
	
	public int addVisitByIsNot(Visit visit){
		return visitDao.saveVisitByIsNot(visit);
	}
}

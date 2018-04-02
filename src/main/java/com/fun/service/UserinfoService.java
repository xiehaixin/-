package com.fun.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fun.bean.Userinfo;
import com.fun.dao.UserinfoDao;
@Service
public class UserinfoService {
	
	@Resource
	UserinfoDao userinfoDao;

	public int addUserinfo(Userinfo userinfo){
		Userinfo user = userinfoDao.selectUserinfo(userinfo.getOpenId());
		int count = -1;
		if(user==null){
			count = userinfoDao.insertUserinfo(userinfo);
			System.out.println("UserinfoService.addUserinfo = "+count);
		}
		return count;
	}
	
	public Userinfo findUserinfoByOpenId(String openId){
		return userinfoDao.selectUserinfo(openId);
	}
	
	public Integer addUserinfoByIsNot(Userinfo userinfo){
		Integer count = userinfoDao.saveUserinfoByIsNot(userinfo);
		return count;
	}
	
}

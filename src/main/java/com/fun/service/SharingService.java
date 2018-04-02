package com.fun.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fun.bean.Sharing;
import com.fun.dao.SharingDao;

@Service
public class SharingService {
	
	@Resource
	SharingDao sharingDao;
	
	public Sharing selectSharing(Integer XID){
		Sharing sharing = sharingDao.selectSharing(XID);
		return sharing;
	}
	
	public int insertSharing(String openID,Integer Group_xid,Date sharingTime){
		Sharing sharing = new Sharing();
		sharing.setOpenID(openID);
		sharing.setGroup_xid(Group_xid);
		sharing.setSharingTime(sharingTime);
		int count = sharingDao.insertSharing(sharing);
		return count;
	}

}

package com.fun.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fun.bean.PIC;
import com.fun.bean.Project;
import com.fun.dao.PICDao;
import com.fun.dao.ProjectDao;
import com.fun.util.QiniuFileSystemUtil;

@Service
public class ProjectService {

	@Resource
	ProjectDao projectDao;
	
	@Resource
	PICDao picDao;
	
	public List<Project> selectProject(Integer id){
		return projectDao.selectProject(id);
	}
	
	public int addProject(Project project){
		return projectDao.insertProject(project);
	}
	
	public int addProject(Project project,MultipartFile img) throws IOException{
		int proId=projectDao.insertProject(project);
		PIC pic = new PIC();
		try {
			pic.setPIC_iType(Integer.parseInt(project.getsType()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return -2;
		}
		pic.setCreateTime(project.getCreateTime());
		pic.setGroup_XID(proId);
		byte[] buffer = img.getBytes();
		String uploadShearPic = QiniuFileSystemUtil.uploadShearPic(buffer);
		pic.setPIC_URL(uploadShearPic);
		pic.setCreater("-1");
		
		return proId;
	}
	
	public int addProject(Project project,String img) throws IOException{
		int proId=projectDao.insertProject(project);
		PIC pic = new PIC();
		try {
			pic.setPIC_iType(Integer.parseInt(project.getsType()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return -2;
		}
		pic.setCreateTime(project.getCreateTime());
		pic.setGroup_XID(proId);
		/*byte[] buffer = img.getBytes();
		String uploadShearPic = QiniuFileSystemUtil.uploadShearPic(buffer);*/
		pic.setPIC_URL(img);
		pic.setCreater("-1");
		picDao.insertPIC(pic);
		return proId;
	}
	
	public List<Project> findProjectAllByOpenId(String openId){
		return projectDao.selectProjectAllByOpenId(openId);
	}
	
	public Project findProjectByXID(Integer XID){
		return projectDao.selectProjectByXID(XID);
	}
}

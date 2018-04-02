package com.fun.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.fun.bean.HBOrder;
import com.fun.bean.PIC;
import com.fun.bean.PayConfig;
import com.fun.bean.Project;
import com.fun.bean.Sharing;
import com.fun.bean.Userinfo;
import com.fun.bean.Visit;
import com.fun.bean.XCXPayModel;
import com.fun.pay.NotifyUrlHelper;
import com.fun.pay.PayResponseReturnModel;
import com.fun.pay.WxPay;
import com.fun.service.HBOrderService;
import com.fun.service.PICService;
import com.fun.service.ProjectService;
import com.fun.service.SharingService;
import com.fun.service.UserinfoService;
import com.fun.service.VisitService;
import com.fun.util.CallBackPar;
import com.fun.util.Create3rd_Session;
import com.fun.util.HttpClientUtil;
import com.fun.util.OrderSave;
import com.fun.util.StrUtils;
import com.fun.util.WXAppletUserInfo;
import com.fun.util.XCX3rdSession;




@RestController
public class FunController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static Userinfo userInfo = new Userinfo();
	
	@Resource
	SharingService sharingService;
	
	@Resource
	ProjectService projectService;
	
	@Resource
	UserinfoService userinfoService;
	
	@Resource
	VisitService visitService;
	
	@Resource
	PICService picService;
	
	@Resource
	HBOrderService hbOrderService;
	
	
	/*@Autowired
	private Environment environment;*/
	
	@Autowired
	private WXAppletUserInfo wxInfo;
	
	@Autowired
	private NotifyUrlHelper notifyUrlHelper;
	
	
	@RequestMapping("writelog")  
    public Object writeLog()  
    {  
        logger.debug("This is a debug message");  
        logger.info("This is an info message");  
        logger.warn("This is a warn message");  
        logger.error("This is an error message");  
        return "OK";  
    }  

	/*@RequestMapping("showHello")
	public String showHello(){
		
		return environment.getProperty("weixin.AppId");
	}*/
	
	@RequestMapping("showHello2")
	public String showHello2(){
		return "HelloWorld2";
	}
	
	
	@RequestMapping("List")
	public List<String> showList(){
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		return list;
	}
	
	/*@RequestMapping("showWxOrZfb")
	public Map<String,Object> showWxOrZfb(HttpServletRequest request){
		Map<String, Object> parMap = CallBackPar.getParMap();
		Object attribute = request.getAttribute("user-Agent");
		String s = request.getHeader("user-Agent");
		System.out.println(s);
		parMap.put("data", s);
		return parMap;
		
	}*/
	
	@RequestMapping("Map")
	public Map<String, Object> showMap(){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("aaa", "111");
		map.put("bbb", "222");
		map.put("ccc", "333");
		return map;
	}
	
	@RequestMapping("showProject")
	public List<Project> showProject(Integer id){
		try {
			List<Project> projects = projectService.selectProject(id);
			logger.info(projects.toString());
			return projects;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("showSharing")
	public Sharing showSharing(Integer XID){
		try {
			Sharing sharing = sharingService.selectSharing(XID);
			return sharing;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="addUserInfo")
	public Map<String, Object> addUserInfo(HttpServletRequest request,String cacheKey,String encryptedData,String iv){
		/*encryptedData = "WPDYtrO/7n/+IvzD66w6qvWIKJnsL/8kOZTPM+TeNP1JmICrwki5hHGLdG/YZjYK/zeEc4+6bOCu63lJBlD3bq8lb6LFE1ZvqWhq0Ezlad1t6QK9IXp8Gxzb4obft9xtKvuisYknsz9/OFDLhUz6jC8wnMpSKEFPD8iRAcelsohvF7n4haJHtSMJ/Xil44OusQz8Q4FmbfilhZOGHGAJI3t1o/gHxIYMco8gq8wuICewb5I/Yb189tRhLdX++MGpKWv0GVE4wTkKTagASd52ee50xHNbpfdSGXE6M1EpVgFfIze+K/HhTdI6J4mPKuIT6gdxIwyVevrT9A/97yRfCXp7QdHg538mjtRLPk4UuqhqHqit1ePKxrnLeMhnnIvzdvajase41hZL+PGugCQy9PFTqA8fYoUN1mGMTmoQ573lVyclSsrlQlLCD0U8S13eisGKC/4uxXCRYmA8AlliNfDJbceWnBH1ApwAla9UBJc=";
		iv = "SibUKuhQnMvPIlEg3eVQnA==";*/
		logger.info("request = "+request+" cacheKey = "+ cacheKey+" encryptedData = "+encryptedData+ " iv = "+iv);
		Map<String, Object> parMap = CallBackPar.getParMap();
		JSONObject userInfoJSON = wxInfo.getUserInfo(encryptedData, XCX3rdSession.getSession_Key(request, cacheKey), iv);
		if(userInfoJSON.get("errcode")!=null){
			parMap.put("code","E00001");
			parMap.put("message", userInfoJSON.get("errmsg"));
			return parMap;
		}
		Userinfo userinfo = new Userinfo();
		userinfo.setAvatarUrl((String)userInfoJSON.get("avatarUrl"));
		userinfo.setCity((String)userInfoJSON.get("city"));
		userinfo.setNickName((String)userInfoJSON.get("nickName"));
		userinfo.setProvince((String)userInfoJSON.get("province"));
		userinfo.setGender(userInfoJSON.get("gender").toString());
		userinfo.setCountry((String)userInfoJSON.get("country"));
		userinfo.setLanguage((String)userInfoJSON.get("language"));
		userinfo.setOpenId((String)userInfoJSON.get("openId"));
		userinfo.setProject_id(-1);
		userinfo.setCreateTime(new Date());
		Integer addUserinfo = null;
		try {
			addUserinfo = userinfoService.addUserinfoByIsNot(userinfo);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					"fun", e });
			parMap.put("code","E00001");
			parMap.put("message", "系统异常");
			return parMap;
		}
		logger.info(String.valueOf(addUserinfo));
		userInfo = userinfo;
		return parMap;
	}
	
	@RequestMapping("addUserInfo1")
	public String addUserInfo1(String nickName,String avatarUrl,String city,Integer gender,String province){
		Userinfo userinfo = new Userinfo();
		if(StrUtils.isEmOrUn(nickName)||StrUtils.isEmOrUn(avatarUrl)||StrUtils.isEmOrUn(city)||StrUtils.isEmOrUn(province)||gender==null){
			return "-1";
		}
		userinfo.setAvatarUrl(avatarUrl);
		userinfo.setCity(city);
		userinfo.setNickName(nickName);
		userinfo.setProvince(province);
//		userinfo.setGender(gender);
		userinfo.setCreateTime(new Date());
		userInfo = userinfo;
		return "0";
	}
	
	@RequestMapping("showUserinfo")
	public Userinfo showUserinfo(){
		
		return userinfoService.findUserinfoByOpenId(userInfo.getOpenId());
	}
	
	@RequestMapping("showUserinfo1")
	public Userinfo showUserinfo1(){
			return userInfo;
	}
	
	@RequestMapping("insertSharing")
	public int insertSharing(String openID,Integer Group_xid){
		try {
			Date sharingTime = new Date();
			int insertSharing = sharingService.insertSharing(openID, Group_xid, sharingTime );
			return insertSharing;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	@RequestMapping(value="login")
	public Map<String, Object> login(HttpServletRequest request,String code){
		JSONObject sessionKeyOropenid = wxInfo.getSessionKeyOropenid(code);
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(sessionKeyOropenid.get("errcode")!=null&&"40029".equals(sessionKeyOropenid.get("errcode").toString())){
			parMap.put("code","E00001");
			parMap.put("message", "code超时");
			return parMap;
		}else if(sessionKeyOropenid.get("errcode")!=null&&"40163".equals(sessionKeyOropenid.get("errcode").toString())){
			parMap.put("code","E00001");
			parMap.put("message", "code被使用");
			return parMap;
		}else if(sessionKeyOropenid.get("errcode")!=null){
			parMap.put("code","E00001");
			parMap.put("message", sessionKeyOropenid.get("errmsg"));
			return parMap;
		}
		Map<String, String> cacheKeyMap = new HashMap<String, String>();
		cacheKeyMap.put("session_key", (String) sessionKeyOropenid.get("session_key"));
		cacheKeyMap.put("openId", (String) sessionKeyOropenid.get("openid"));
		logger.info("sessionKeyOropenid = "+sessionKeyOropenid);
		String cacheKey = Create3rd_Session.get3rdSession();
		request.getSession().setAttribute(cacheKey, cacheKeyMap);
		Map<String, Object> b = new HashMap<String, Object>();
		b.put("cacheKey", cacheKey);
		b.put("session_id", request.getSession().getId());
		logger.info("session_keyMap = "+request.getSession().getAttribute(cacheKey)+" cacheKey = "+cacheKey+" session = "+request.getSession());
		parMap.put("data", b);
		return parMap;
	}
	
	@RequestMapping(value="addActivity")
	public Map<String, Object> addActivity(HttpServletRequest request,String cacheKey,String type,String title,String content,String signature,String img){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(StrUtils.isEmOrUn(cacheKey)||StrUtils.isEmOrUn(type)||StrUtils.isEmOrUn(title)||StrUtils.isEmOrUn(signature)){
			parMap.put("code","E00001");
			parMap.put("message", "参数缺失");
			logger.error("参数缺失 ——>cacheKey = "+cacheKey+" type = "+type+" signature = "+signature);
			return parMap;
		}
		Date newTime = new Date();
		String openId = XCX3rdSession.getOpenId(request, cacheKey);
		Project pro = new Project();
		pro.setsInitiator(openId);
		pro.setCreateTime(newTime);
		pro.setsContent(content);
		pro.setsName(title);
		pro.setsType(type);
		pro.setSignature(signature);
		int count = 0;
		if(StrUtils.isEmOrUn(img)){
			logger.info("img = 无");
			count = projectService.addProject(pro);
		}else{
			logger.info("img = "+img.toString());
			try {
				count = projectService.addProject(pro,img);
			} catch (IOException e) {
				logger.error("[project:{}] [exception:{}]", new Object[] {
						"fun", e });
				parMap.put("code","E00001");
				parMap.put("message", "系统异常");
			}
		}
		if(count<=0){
			if(count==-2){
				logger.error("type = "+type);
				parMap.put("code","E00001");
				parMap.put("message", "请输入正确的类型 type");
				return parMap;
			}
			logger.info("cacheKey = "+cacheKey+" type = "+type+" title = "+title+" content = "+content);
			parMap.put("code","E00001");
			parMap.put("message", "系统异常");
			return parMap;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("XID", count);
		parMap.put("data", map);
		return parMap;
	}

	@RequestMapping(value="showMyActivitys")
	public Map<String, Object> showMyActivity(HttpServletRequest request,String cacheKey){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(StrUtils.isEmOrUn(cacheKey)){
			parMap.put("code","E00001");
			parMap.put("message", "参数缺失");
			logger.error("参数缺失 ——>cacheKey = "+cacheKey);
			return parMap;
		}
		String openId = XCX3rdSession.getOpenId(request, cacheKey);
		List<Project> all = projectService.findProjectAllByOpenId(openId);
		logger.info(" all = "+all);
//		JSONObject.fromObject(all);
		parMap.put("data", all);
		return parMap;
	}
	
	@RequestMapping(value="showActivity")
	public Map<String, Object> showActivity(HttpServletRequest request,String cacheKey,Integer XID){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(StrUtils.isEmOrUn(cacheKey)||XID==null){
			parMap.put("code","E00001");
			parMap.put("message", "参数缺失");
			logger.error("参数缺失 ——>cacheKey = "+cacheKey+" XID = "+XID);
			return parMap;
		}
		Project pro = projectService.findProjectByXID(XID);
		logger.info(pro.toString());
		List<Visit> visitList = visitService.findVisitByGroup_XID(XID);
		PIC findPICByGroup_XID = picService.findPICByGroup_XID(XID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityInfo", pro);
		map.put("members", visitList);
		map.put("number", visitList.size());
		if(findPICByGroup_XID!=null){
			map.put("image", findPICByGroup_XID.getPIC_URL());
		}else{
			map.put("image", null);
		}
		parMap.put("data", map);
		
		return parMap;
	}
	
	/*@RequestMapping(value="addMember")
	public Map<String, Object> addMember(HttpServletRequest request,String cacheKey,Integer XID){
		
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(StrUtils.isEmOrUn(cacheKey)||XID==null){
			parMap.put("code","E00001");
			parMap.put("message", "参数缺失");
			logger.error("参数缺失 ——>cacheKey = "+cacheKey+" XID = "+XID);
			return parMap;
		}
		String openId = XCX3rdSession.getOpenId(request, cacheKey);
		logger.info("Group_XID = "+XID+" openId = "+openId);
		Visit viUser = visitService.findVisitByGroupOrOpenId(XID, openId);
		if(viUser!=null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", viUser.getXID());
			parMap.put("data", map);
			return parMap;
		}
		Visit visit = new Visit();
		visit.setGroup_XID(XID);
		visit.setVisitDate(new Date());
		visit.setOpenID(openId);
		visit.setVisitmessage("-1");
		Userinfo user = userinfoService.findUserinfoByOpenId(openId);
		if(user==null){
			parMap.put("code","E00001");
			parMap.put("message", "未登陆");
			return parMap;
		}
		visit.setAvatarUrl(user.getAvatarUrl());
		visit.setNickName(user.getNickName());
		int memberId = visitService.addVisit(visit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		parMap.put("data", map);
		return parMap;
	}*/
	
	/**
	 * 
	 * @param request
	 * @param cacheKey 
	 * @param scene 场景值
	 * @param XID
	 * @param encryptedData 密钥
	 * @param iv 偏移量
	 * @return
	 */
	@RequestMapping(value="addMember")
	public Map<String, Object> addMember(HttpServletRequest request,String cacheKey,String scene,Integer XID,String encryptedData,String iv){
		// 获取场景值
		Map<String, Object> parMap = CallBackPar.getParMap();
		Visit visit = new Visit();
		if(StrUtils.isEmOrUn(cacheKey)||XID==null){
			parMap.put("code","E00001");
			parMap.put("message", "参数缺失");
			logger.error("参数缺失 ——>cacheKey = "+cacheKey+" XID = "+XID);
			return parMap;
		}
		int memberId = 0;
		if(!StrUtils.isEmOrUn(scene)&&"1044".equals(scene)){
			logger.info("encryptedData = "+encryptedData+" iv = "+iv);
			
			JSONObject userInfo2 = null;
			if(!StrUtils.isEmOrUn(encryptedData)&&!StrUtils.isEmOrUn(iv)){
				try {
					userInfo2 = wxInfo.getUserInfo(encryptedData, XCX3rdSession.getSession_Key(request, cacheKey), iv);
					com.alibaba.fastjson.JSONObject parseObject = JSON.parseObject(userInfo2.toString());
					String openGId = (String) parseObject.get("openGId");
					visit.setWeChatGroup(openGId);
					logger.info(" openGId = "+openGId);
				} catch (Exception e1) {
					logger.error("[project:{}] [exception:{}]", new Object[] {
							"fun", e1 });
					parMap.put("code","E00001");
					parMap.put("message", "系统异常");
					return parMap;
				}
			}
			
		
			String openId = XCX3rdSession.getOpenId(request, cacheKey);
			
			visit.setGroup_XID(XID);
			visit.setVisitDate(new Date());
			visit.setOpenID(openId);
			visit.setVisitmessage("-1");
			
			try {
				memberId = visitService.addVisitByIsNot(visit);
			} catch (Exception e) {
				logger.error("[project:{}] [exception:{}]", new Object[] {
						"fun", e });
				parMap.put("code","E00001");
				parMap.put("message", "系统异常");
				return parMap;
				
			}
			if(memberId<0){
				parMap.put("code","E00001");
				parMap.put("message", "未登陆");
				return parMap;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		parMap.put("data", map);
		return parMap;
	}
	
	@RequestMapping(value="newOrderSave")
	public Map<String, Object> newOrderSave(HttpServletRequest request){
		Map<String, Object> parMap = CallBackPar.getParMap();
		String quickSpJson = request.getParameter("quickSpJson");
		String cacheKey = request.getParameter("cacheKey");
		System.out.println("quickSpJson = "+quickSpJson+" cacheKey = "+cacheKey);
		if(StrUtils.isEmOrUn(quickSpJson)||StrUtils.isEmOrUn(cacheKey)){
			parMap.put("code","E00001");
			parMap.put("message", "参数缺失");
			return parMap;
		}
		
//		com.alibaba.fastjson.JSONArray parseArray = JSON.parseArray(quickSpJson);
		JSONObject parseObject = JSONObject.fromObject(quickSpJson);
		
		String money = (String) parseObject.get("money");
		String moneyRemark = (String) parseObject.get("moneyRemark");
		String payType = (String) parseObject.get("payType");
		XCXPayModel x = new XCXPayModel();
		x.setAppId(PayConfig.getXcx_appid());
		x.setMchId(PayConfig.getXcx_mchId());
		UUID uuid=UUID.randomUUID();
	    String str = uuid.toString(); 
	    String uuidStr=str.replace("-", "");
		x.setNonceStr(uuidStr);
		x.setBody(moneyRemark);
		x.setOrderSN(OrderSave.getOrderNew());
		x.setTotalFee(String.valueOf((int)(Double.parseDouble(money)*100)));
		x.setCreateIp(HttpClientUtil.getRequestIP(request));
		x.setOpenId(XCX3rdSession.getOpenId(request, cacheKey));
		x.setAppKey(PayConfig.getXcx_appKey());
		x.setNotifyUrl(PayConfig.getXcx_domainName());
		if("2".equals(payType)){
			logger.info("=================小程序支付转微信红包=============");
			HBOrder hbOrder = new HBOrder();
			hbOrder.setAppid(PayConfig.getXcx_appid());
			hbOrder.setMch_id(Integer.parseInt(PayConfig.getXcx_mchId()));
			hbOrder.setNonce_str(uuidStr);
			hbOrder.setOut_trade_no(x.getOrderSN());
			hbOrder.setOut_trade_no(x.getOrderSN());
			hbOrder.setTotal_fee(Integer.parseInt(x.getTotalFee()));
			hbOrder.setSpbill_create_ip(x.getCreateIp());
			hbOrder.setSend_name((String) parseObject.get("sendName"));
			hbOrder.setWishing((String) parseObject.get("wishing"));
			hbOrder.setTotal_num(Integer.parseInt((String) parseObject.get("totalNum")));
			hbOrder.setAct_name("发红包");
			hbOrder.setRemark("备注");
			Integer addHBOrder = null;
			try {
				addHBOrder = hbOrderService.addHBOrder(hbOrder);
			} catch (Exception e) {
				logger.error("[project:{}] [exception:{}]", new Object[] {
						"fun", e });
				parMap.put("code","E00001");
				parMap.put("message", "系统异常");
				return parMap;
			}
			if(addHBOrder<0){
				logger.error("[project:{}] [参数:{}]", new Object[] {
						"fun", parseObject });
				parMap.put("code","E00001");
				parMap.put("message", "支付失败");
				return parMap;
			}
		}
		WxPay p = new WxPay();
		PayResponseReturnModel pay = p.Pay(x);
		logger.info("newOrderSave.pay = "+pay);
		parMap.put("data", pay);
		return parMap;
	}
	
	@RequestMapping(value="notifyUrl")
	public String notifyUrl(HttpServletRequest request,HttpServletResponse response){
		NotifyUrlHelper helper = notifyUrlHelper;
		helper.Init(request,response);
		return helper.getResult();
	}
	
	@RequestMapping("Test")
	public Visit Test(Integer Group_XID,String openId){
		logger.info("Group_XID = "+Group_XID+" openId = "+openId);
		return visitService.findVisitByGroupOrOpenId(Group_XID, openId);
	}
	
	@RequestMapping("Test1")
	public Visit Test1(Integer Group_XID,String openId){
		logger.info("Group_XID = "+Group_XID+" openId = "+openId);
		return visitService.findVisitByGroupOrOpenId(Group_XID, openId);
	}
	
	@RequestMapping(value={"Test2","{appid}/qq"})
	public String Test2(@PathVariable String appid){
		logger.info("appid = "+appid);
		return appid;
	}
}

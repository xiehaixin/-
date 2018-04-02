package com.fun.pay;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.xml.sax.InputSource;

import com.alibaba.druid.util.StringUtils;
import com.fun.WxHB.WxPayHB;
import com.fun.bean.HBOrder;
import com.fun.bean.PayConfig;
import com.fun.bean.WXHB;
import com.fun.bean.WxPayNotifyModel;
import com.fun.bean.XCXOrWx;
import com.fun.service.HBOrderService;
import com.fun.service.WxPayNotifyModelService;
import com.fun.service.XCXOrWxService;
@Component
public class NotifyUrlHelper {

	private static final Logger logger = LoggerFactory.getLogger(NotifyUrlHelper.class);
	
	@Autowired
	WxPayNotifyModelService notifyModelService;
	
	@Autowired
	HBOrderService hbOrderService;
	
	@Autowired
	XCXOrWxService xcxOrWxService;
	
	@Autowired
	WxPayHB wxph;
	
	Model _model;
	HttpServletRequest _request1;
	HttpServletResponse _response1;
	String _result;
	
	public NotifyUrlHelper(Model model, HttpServletRequest request, HttpServletResponse response) {
		_model = model;
		_request1 = request;
		_response1 = response;
	}
	
	public NotifyUrlHelper(HttpServletRequest request, HttpServletResponse response) {
		_request1 = request;
		_response1 = response;
	}
	
	public NotifyUrlHelper() {
	}

	public void Init(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequest _request = request;
		HttpServletResponse _response = response;
		
		logger.info("微信支付回调数据开始！");
		// 示例报文
		// String xml =
		// "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
		String inputLine;
		String notityXml = "";
		String resXml = "";

		try {
			while ((inputLine = _request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			_request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
			_result = "no";
			logger.info("解析错误：" + e.toString());
			return;
		}

		logger.info("接收到的报文：" + notityXml);
		
		if(StringUtils.isEmpty(notityXml)){
			_result = "empty";
			logger.info("接收到的报文为空");
			return;
		}

		Map m = parseXmlToList2(notityXml);
		WxPayNotifyModel wpr = new WxPayNotifyModel();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(String.valueOf(Double.valueOf(m.get("total_fee").toString())/100.0));
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());

		if ("SUCCESS".equals(wpr.getResultCode())) {
			// 支付成功
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			/*String result = weiXinService.weixinPayToSuc(wpr.getOpenid(), wpr.getOutTradeNo(), wpr.getTransactionId(), wpr.getBankType(), wpr.getTotalFee(), DateUtil.getStringDate());
			JSONObject jsonResult = JSONObject.fromObject(result);*/
			int addNotifyOrder = notifyModelService.addNotifyOrder(wpr);
			if(addNotifyOrder>0){
				HBOrder findPayConfig = hbOrderService.findPayConfig(wpr.getOutTradeNo());
				if(findPayConfig==null){
					logger.error("红包查询参数不成功");
					_result = "faild1";
				}else{
					WXHB w = new WXHB();
					w.setNonce_str(wpr.getNonceStr());
					w.setMchBillno(wpr.getOutTradeNo());
					w.setMchId(PayConfig.getWeixin_mchId());
					w.setWxappid(PayConfig.getWeixin_appid());
					w.setSendName(findPayConfig.getSend_name()); // 商户名称
					XCXOrWx xcxOrWx = new XCXOrWx();
					xcxOrWx.setXcx_OpenId(wpr.getOpenid());
					w.setReOpenid(xcxOrWxService.addXCXOrWxByWxOpenId(xcxOrWx).getWx_OpenId()); // 用户openid
					w.setTotalAmount(m.get("total_fee").toString()); // 付款金额
					w.setTotalNum(String.valueOf(findPayConfig.getTotal_num())); // 红包发放总人数
					w.setAmt_type("ALL_RAND"); // 红包金额设置方式 
					w.setWishing(findPayConfig.getWishing()); // 红包祝福语
					w.setClientIp(findPayConfig.getSpbill_create_ip()); // Ip地址
					w.setActName("测试"); // 活动名称
					w.setRemark("备注不能空着"); // 备注
					w.setAppKey(PayConfig.getWeixin_appKey());
					String pay = wxph.Pay(w);
					logger.info("红包发送 = "+pay);
					_result = "success";
				}
			}else{
				_result = "faild1";
			}
		} else {;
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			_result = "faild";
		}

		logger.error("微信支付回调数据结束！");
	}

	public String getResult() {
		return _result;
	}

	/**
	 * description: 解析微信通知xml
	 * 
	 * @param xml
	 * @return
	 * @author ex_yangxiaoyi
	 * @see
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private static Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

}

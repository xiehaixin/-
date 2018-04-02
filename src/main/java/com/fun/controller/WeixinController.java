package com.fun.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fun.util.Sha1Util;

@RestController
@RequestMapping("/weixin")
public class WeixinController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping("oauth2")
	public String oauth2(String signature,String timestamp,String nonce,String echostr){
		logger.info("[signature:{}] [timestamp:{}] [nonce:{}] [echostr:{}]",
				new Object[] { signature,timestamp,nonce,echostr});
		if(checkSignature(signature, timestamp, nonce)){
			return echostr;
		}
		return null;
	}
	
	
	/** 验证 */
	private boolean checkSignature(String signature, String timestamp, String nonce) {
		// getSysParamMapValue(SysParamNames.wxParaWeiXinToken)获取系统缓存的token没有的话 返回""
		String[] arr = new String[] { "shjtoken", timestamp, nonce };

		// 排序
		Arrays.sort(arr);
		
		// 生成字符串
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		logger.error("[[signature:{}],[content:{}]", new Object[] { signature, content });
		
		// sha1加密
		String temp = Sha1Util.getSha1(content.toString());
		
		logger.error("[temp:{}],[signature:{}],[content:{}]", new Object[] { temp, signature, content });
		return temp.equals(signature); // 与微信传递过来的签名进行比较
	}
	
	
}

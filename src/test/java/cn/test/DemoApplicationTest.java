package cn.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fun.FunApplication;
import com.fun.WxHB.WxPayHB;
import com.fun.bean.PayConfig;
import com.fun.bean.Visit;
import com.fun.bean.WXHB;
import com.fun.dao.VisitDao;
import com.fun.util.HttpClientUtil;
import com.fun.util.QiniuFileSystemUtil;
import com.fun.util.RemoteInterfaceAddress;
import com.fun.util.WXAppletUserInfo;
import com.qiniu.common.QiniuException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FunApplication.class)
@WebAppConfiguration
public class DemoApplicationTest {

	@Autowired
	VisitDao visitDao;
	
	@Autowired
	WxPayHB wxph;
	
	
	@Autowired
	private RemoteInterfaceAddress remoteInterfaceAddress;
	
	@Test
	public void test01(){
		Visit visit = new Visit();
		visit.setVisitDate(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(visit.getVisitDate());
		System.out.println(dateString);
	}
	
	@Test
	public void test02(){
		Visit visit = new Visit();
		visit.setGroup_XID(56);
		visit.setOpenID("saf1");
		visit.setNickName("qwe");
		visit.setAvatarUrl("sdafas2");
		visit.setVisitDate(new Date());
		visit.setVisitmessage("asdfwer");
		visit.setWeChatGroup("dfdf");
		visit.setXID(1);
		int xid = visitDao.saveVisitByIsNot(visit);
		System.out.println(xid);
	}
	
	@Test
	public void test03() throws QiniuException{
		
		String filePath = "C:\\Users\\XHX\\Desktop\\asdfasfasdfasdfas.png";
		byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		String uploadShearPic = QiniuFileSystemUtil.uploadShearPic(buffer);
		System.out.println(uploadShearPic);
	}
	
	@Test
	public void test04(){
		Integer.parseInt("sdfg");
	}
	
	@Test
	public void test05(){
		WXHB w = new WXHB();
		UUID uuid=UUID.randomUUID();
	    String str = uuid.toString(); 
	    String uuidStr=str.replace("-", "");
		w.setNonce_str(uuidStr);
		Random ran=new Random();
		int a=ran.nextInt(99999999);
		int b=ran.nextInt(99999999);
		long l=a*10000000L+b;
		String num=String.valueOf(l);
		w.setMchBillno(num);
		w.setMchId("1427007502");
		w.setWxappid(PayConfig.getWeixin_appid());
		System.out.println(PayConfig.getWeixin_appid());
		w.setSendName("谢海鑫"); // 商户名称
		w.setReOpenid("o0xutwDJu8wwa-MQCC_GXxsCAy4k"); // 用户openid
		w.setTotalAmount("300"); // 付款金额
		w.setTotalNum("3"); // 红包发放总人数
		w.setAmt_type("ALL_RAND"); // 红包金额设置方式 
		w.setWishing("测试"); // 红包祝福语
		w.setClientIp("183.238.231.83"); // Ip地址
		w.setActName("测试"); // 活动名称
		w.setRemark("备注不能空着"); // 备注
		w.setAppKey(PayConfig.getWeixin_appKey());
		String pay = wxph.Pay(w);
		System.out.println(pay);
	}
	
	@Test
	public void test07(){
		// {"access_token":"6_8uGB1rMT-i_zi6E_5ChEN6rrNmahXAyYgSp-3NsHJ5XXw7-ase5bCKJNvKse_K872NmVj-aaR9MoIy12p49ydVa00W6doNy9WdVnCEcE59ojkC53DOpun4oKyhAt4GLibVTT3s369EKnA966KQRdAJAYEF","expires_in":7200}
		// {"errcode":40125,"errmsg":"invalid appsecret, view more at http:\/\/t.cn\/RAEkdVq hint: [KjmSya0537sha7]"}
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		Map<String, String> param = new HashMap<String, String>();
		param.put("grant_type", "client_credential");
		param.put("appid", "wx5288d5e0e47b4f76");
		param.put("secret", "f2601ba78aca4888877b81f07540b892");
		String doGet = HttpClientUtil.doGet(url, param);
		System.out.println(doGet);
	}

	@Test
	public void test08(){
		String appId = "wx4f4bc4dec97d474b";  
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";  
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"  
                               + "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"  
                               + "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"  
                               + "3hVbJSRgv+4lGOETKUQz6OYStslQ142d"  
                               + "NCuabNPGBzlooOmB231qMM85d2/fV6Ch"  
                               + "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"  
                               + "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"  
                               + "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"  
                               + "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"  
                               + "8LOddcQhULW4ucetDf96JcR3g0gfRK4P"  
                               + "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"  
                               + "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"  
                               + "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"  
                               + "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"  
                               + "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"  
                               + "20f0a04COwfneQAGGwd5oa+T8yO5hzuy" + "Db/XcxxmK01EpqOyuxINew==";  
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";  
		WXAppletUserInfo a = new WXAppletUserInfo();
		JSONObject userInfo = a.getUserInfo(encryptedData, sessionKey, iv);
		System.out.println(userInfo);
	}
}

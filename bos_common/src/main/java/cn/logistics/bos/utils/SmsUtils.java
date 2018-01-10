package cn.itcast.bos.utils;

import org.apache.commons.lang3.RandomStringUtils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 阿里大于短信平台工具类封装
 * @author BoBo
 *
 */
public class SmsUtils {
	//短信平台请求地址
	/*
	 * 正式环境	http://gw.api.taobao.com/router/rest	https://eco.taobao.com/router/rest
	 * 沙箱环境	http://gw.api.tbsandbox.com/router/rest	https://gw.api.tbsandbox.com/router/rest
	 */
	private static final String SMS_CHECKCODE_URL="https://eco.taobao.com/router/rest";
	
	private static final String SMS_CHECKCODE_APPKEY="24664507";
	private static final String SMS_CHECKCODE_SECRET="2d3156051d75c8417905e807c1063f59";
	
	//发送4个随机数验证码，并返回验证码
	public static String sendCheckCodeHasRandomNum(int count,String recNum){
		String randomCode = RandomStringUtils.randomNumeric(4);
		String smsParam="{checkcode:'"+randomCode+"'}";
		sendCheckCode(smsParam, recNum, "SMS_67590099");
		return randomCode;
	}
	//发送4个随机数验证码，并返回验证码
	//randomCode:验证码
	public static void sendCheckCode(String checkcode,String recNum){
		String smsParam="{checkcode:'"+checkcode+"'}";
		
		sendCheckCode(smsParam, recNum, "SMS_67590099");
	}
	//发送验证码
	public static void sendCheckCode(String smsParam,String recNum,String smsTemplateCode){
		sendSms(SMS_CHECKCODE_URL, SMS_CHECKCODE_APPKEY, SMS_CHECKCODE_SECRET,
				"", "normal", "波波老师", smsParam, recNum, smsTemplateCode);
	}
	
	//通用的、完整的发送短信的方法
	public static void sendSms
	(String serverUrl, String appKey, String appSecret
			,String extend, String smsType,String smsFreeSignName,String smsParam,String recNum,String smsTemplateCode){
		
		TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		/*
		 * 公共回传参数，在“消息返回”中会透传回该参数；
		 * 举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
		 */
		req.setExtend( extend );
		/*
		 * 短信类型，传入值请填写normal
		 */
		req.setSmsType( smsType );
		/*
		 * 短信签名，传入的短信签名必须是在阿里大于“管理中心-验证码/短信通知/推广短信-配置短信签名”中的可用签名。
		 * 如“阿里大于”已在短信签名管理中通过审核，则可传入”阿里大于“（传参时去掉引号）作为短信签名。
		 * 短信效果示例：【阿里大于】欢迎使用阿里大于服务。
		 */
		req.setSmsFreeSignName( smsFreeSignName );
		/*
		 * 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
		 * 示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
		 */
		req.setSmsParamString( smsParam );
		/*
		 * 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。
		 * 群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。
		 * 示例：18600000000,13911111111,13322222222
		 */
		req.setRecNum( recNum );
		/*
		 * 短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
		 */
		req.setSmsTemplateCode( smsTemplateCode );
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			
			rsp = client.execute(req);
			/*
			 * 响应示例：
			 * {
				    "alibaba_aliqin_fc_sms_num_send_response":{
				        "result":{
				        }
				    }
				}
			 * result	BizResult	0	返回值
			 *  └ err_code String 0错误码
			 *  └ model String 134523^4351232返回结果
			 *  └ success Boolean falsetrue表示成功，false表示失败
			 *  └ msg String 成功返回信息描述
			 *  
			 *  错误码详见阿里大鱼的文档。
			 */
			System.out.println("短信发送成功，短信平台返回的信息："+rsp.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("通过阿里大鱼平台发送验证码失败！");
		}
		
	}
	
	//发送短信通知
	
	
	//发送推广短信
	
	
	//发送语音通知
	
	//群发助手
	
	public static void main(String[] args) {
		//测试
		//验证码发送
		String checkCode = sendCheckCodeHasRandomNum(4, "15921470367");
		//验证码为
		System.out.println("发送的验证码为："+checkCode);
		
//		System.out.println( RandomStringUtils.randomNumeric(4));
	}

}

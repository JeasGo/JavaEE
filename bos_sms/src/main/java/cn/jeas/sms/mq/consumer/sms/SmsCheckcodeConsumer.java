package cn.jeas.sms.mq.consumer.sms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;

import cn.jeas.bos.utils.SMSUtils;



@Service
public class SmsCheckcodeConsumer implements MessageListener{

	@Override
	public void onMessage(Message message) {
		//目标：获取消息，发送短信
		MapMessage mapMessage=(MapMessage)message;
		
		try {
			//手机号码
			String telephone = mapMessage.getString("telephone");
			//短信内容验证码
			String checkcode = mapMessage.getString("checkcode");
			//发送短信：
			//调用阿里大鱼发送验证码的短信
			
			//SMSUtils.sendMsg(telephone,checkcode);
			
			
			
			System.out.println("手机号是："+telephone+"的验证码："+checkcode);
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("发送验证码失败！！！");
		}
	}

}

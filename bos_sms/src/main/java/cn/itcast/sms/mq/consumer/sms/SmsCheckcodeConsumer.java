package cn.itcast.sms.mq.consumer.sms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

//短信消费者
@Component
public class SmsCheckcodeConsumer implements MessageListener{

	@Override
	public void onMessage(Message message) {
		//目标：获取消息，发送短信
		MapMessage mapMessage=(MapMessage)message;
		
		try {
			//手机号码
			String mobilePhone = mapMessage.getString("mobilePhone");
			//短信内容验证码
			String checkcode = mapMessage.getString("checkcode");
			//发送短信：
			//调用阿里大鱼发送验证码的短信
//			SmsUtils.sendCheckCode(checkcode, mobilePhone);
			System.out.println("手机号是："+mobilePhone+"的验证码："+checkcode);
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("发送验证码失败！！！");
		}
		
	}

}

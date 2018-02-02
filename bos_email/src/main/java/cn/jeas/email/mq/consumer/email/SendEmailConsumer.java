package cn.jeas.email.mq.consumer.email;



import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;

import cn.jeas.bos.utils.MailUtils;

@Service
public class SendEmailConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		MapMessage msg = (MapMessage) message;
		try {
			String subject = msg.getString("subject");
			String content = msg.getString("content");
			String to = msg.getString("to");
			
			
			MailUtils.sendMail(subject, content, to);
			System.out.println("成功了!!!");
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("发送邮件失败!!");
		}
	}

}

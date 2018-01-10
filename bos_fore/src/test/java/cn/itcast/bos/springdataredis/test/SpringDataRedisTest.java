package cn.itcast.bos.springdataredis.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-cache.xml"})
public class SpringDataRedisTest {
	//注入bean
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void testRedis(){
		//保存数据
		//简单的值
//		redisTemplate.opsForValue().set("username1", "Rose");
		//参数3：毫秒消亡的时间
		redisTemplate.opsForValue().set("username3", "aa", 24, TimeUnit.HOURS);
		
		//获取数据
//		String username1 = (String) redisTemplate.opsForValue().get("username1");
//		System.out.println(username1);
//		
//		redisTemplate.opsForList().leftPush("aa", "123l");
//		redisTemplate.opsForList().rightPush("aa", "234r");
		System.out.println(redisTemplate.opsForList().rightPop("aa"));
	}
	
}

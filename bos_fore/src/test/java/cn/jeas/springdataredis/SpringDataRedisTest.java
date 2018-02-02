package cn.jeas.springdataredis;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-cache.xml")
public class SpringDataRedisTest {
	
	//注入模版对象
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void test(){
		
		redisTemplate.opsForValue().set("username2", "小白");
		
		redisTemplate.opsForValue().set("username3", "小红",10,TimeUnit.SECONDS);
		
		
	}
	
}

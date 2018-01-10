package cn.itcast.bos.dao.base;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class StandardRepositoryTest {
	//注入要测试的bean
	@Autowired
	private StandardRepository standardRepository;
	
	//保存测试
	@Test
	public void testSave(){
		Standard standard=new Standard();
		standard.setId(1);
		standard.setName("小件体积货物");
		standardRepository.save(standard);
	}
	
	//所有列表查询测试
	@Test
	public void testFindAll(){
		List<Standard> list = standardRepository.findAll();
		System.out.println(list);
	}
	
	@Test
	//根据名字查询一个
	public void testFindByName(){
		Standard standard = standardRepository.findByName("小件体积货物");
		System.out.println(standard);
	}
	@Test
	//根据名字查询id
	public void testFindIdByName(){
		Integer id = standardRepository.findIdByName("小件体积货物");
		System.out.println(id);
	}
	

}

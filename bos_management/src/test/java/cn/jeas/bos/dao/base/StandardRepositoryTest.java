package cn.jeas.bos.dao.base;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.StandardRepository;
import cn.jeas.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@Transactional
public class StandardRepositoryTest {
	
	@Resource(name="standardRepository")
	private StandardRepository standardRepository;
	
	
	//保存测试
	@Test
	public void testSave (){
		Standard standard  =new Standard();
		standard.setId(3);
		standard.setName("大件体积货物");
		Standard save = standardRepository.save(standard);
		System.out.println(save);
	}
	
	//所有列表查询测试
	@Test
	public  void  testFindAll(){
		List<Standard> list = standardRepository.findAll();
		System.err.println(list);
		
	}
	
	//根据姓名查询派去标准数据
	@Test
	public void findByName(){
		List<Standard> list = standardRepository.findByName("小件体积货物");
		System.err.println(list);
		
	}
	@Test
	public void findIdByName(){
		String name = "小件体积货物";
		List<Standard> list = standardRepository.findIdByName(name);
		System.err.println(list);
	}
	
	@Test
	public void updateNameById(){
		String name = "小件体积货物";
		Integer id = 24;
		standardRepository.updateNameById(id, name);
		
	}
	
	
}

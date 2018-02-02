package cn.jeas.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.jeas.bos.domain.base.Standard;
//收派标准的dao接口
//必须继承jparepository接口,泛型类型: 实现类类型和OID的类型
public interface StandardRepository extends JpaRepository<Standard, Integer> {

	//1.属性表达式
	//需求：根据名字来查询取派标准数据
	/*
	 * 方法名必须是驼峰命名
	 * 参数，必须满足属性的值和类型
	 * 返回值：必须是public的，新版可以省略，不建议。
	 * 返回类型：如果最多返回一个，则可以使用实体类类型；
	 * 但如果返回结果任意多个，可以使用集合list，但如果返回两个以上，必须用list
	 */
	//public Standard findByName(String name);
	public List<Standard> findByName(String name);

	@Query("from Standard where name = ?")
	public List<Standard> findIdByName(String name);
	
	@Query("update Standard set name =?2 where id =?1")
	@Modifying
	public void updateNameById(Integer id ,String name );
}

package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.itcast.bos.domain.base.Standard;

//收派标准的dao的接口
//必须继承JpaRepository接口，泛型类型：实体类类型和OID的类型
public interface StandardRepository extends JpaRepository<Standard, Integer>{
	
	//需求：根据名字来查询取派标准数据
	//1.属性表达式
	/*
	 * 方法名必须是驼峰命名
	 * 参数，必须满足属性的值和类型
	 * 返回值：必须是public的，新版可以省略，不建议。
	 * 返回类型：如果最多返回一个，则可以使用实体类类型；
	 * 但如果返回结果任意多个，可以使用集合list，但如果返回两个以上，必须用list
	 */
	public Standard findByName(String name);
//	public List<Standard> findByName(String name);
	
	//2.Query注解:支持jpql或sql
	@Query(value="select id from Standard where name =?")//JPQL(HQL)
//	@Query(value="select id from t_standard where name =?",nativeQuery=true)//SQL
	public Integer findIdByName(String name);
	
	//参数占位符
	//1）匿名占位符
	@Query("from Standard where id =? and name =?")
	public Standard findByIdAndName1(Integer id,String name);
	
	//2）命名占位符
	@Query("from Standard where id =:id and name =:name")
	public Standard findByIdAndName2(@Param("name")String name,@Param("id")Integer id);
	//3）JPA占位符:?索引（索引从1开始）
	@Query("from Standard where id =?2 and name =?1")
	public Standard findByIdAndName3(String name,Integer id);
	
	
}

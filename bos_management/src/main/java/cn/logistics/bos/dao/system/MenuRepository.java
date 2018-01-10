package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

//菜单dao接口
public interface MenuRepository extends JpaRepository<Menu, Integer>{

	//超管：
	@Query("from Menu m order by m.priority asc")
	public List<Menu> findAllOrderByPriority();
	
	//属性表达式
	public List<Menu> findByOrderByPriority();
	
	//普通用户：根据用户查询菜单
	//hql
	@Query("from Menu m inner join fetch m.roles r inner join fetch r.users u where u=? order by m.priority asc")
	public List<Menu> findByUser(User user);
	//sql
	@Query(value="SELECT * FROM t_menu t1 INNER JOIN t_role_menu t2 ON t1.c_id=t2.c_menu_id INNER JOIN t_user_role t3 ON t2.c_role_id=t3.c_role_id WHERE t3.c_user_id=? ORDER BY t1.c_priority ASC"
			,nativeQuery=true)
	public List<Menu> findByUserId(String userId);
	
	
	
}

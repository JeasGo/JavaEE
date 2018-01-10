package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;

//角色dao接口
public interface RoleRepository extends JpaRepository<Role, Integer>{

	//根据多个用户查询角色列表
//	List<Role> findByUsers(Set users);
	//根据一个用户查询角色列表
	List<Role> findByUsers(User user);
}

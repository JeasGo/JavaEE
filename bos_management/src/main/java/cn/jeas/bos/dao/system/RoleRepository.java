package cn.jeas.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.jeas.bos.domain.system.Role;
import cn.jeas.bos.domain.system.User;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	List<Role> findByUsers(User user);

}

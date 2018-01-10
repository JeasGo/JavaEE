package cn.itcast.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.User;
import java.lang.String;
import java.util.List;

//用户操作的dao
public interface UserRepository extends JpaRepository<User, Integer>{

	//根据用户名查询用户
	User findByUsername(String username);
	
	//超过N个月锁定用户状态
	@Query(value="UPDATE t_user t SET t.status='0' WHERE months_between(SYSDATE,t.activetime)>?",nativeQuery=true)
	@Modifying
	void updateStatusByActivetimeForLock(Integer monthNumber);
}

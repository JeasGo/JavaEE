package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

//快递员dao接口

public interface CourierRepository extends JpaRepository<Courier, Integer>
,JpaSpecificationExecutor<Courier>{
	
	//更改删除标记
	@Query("update Courier set deltag =?2 where id =?1")//默认事务是只读的。
	@Modifying//如果是增删改的语句，必须打开事务的可读写。
	public void updateDeltagById(Integer id, Character deltag);

	//根据删除标记来查询列表
	public List<Courier> findByDeltag(Character deltag);
	
}

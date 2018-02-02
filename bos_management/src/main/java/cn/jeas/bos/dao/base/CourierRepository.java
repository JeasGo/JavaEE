package cn.jeas.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.jeas.bos.domain.base.Courier;

public interface CourierRepository extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {

	/**
	 * 功能:根据id来更新快递员状态字段
	 * 
	 * @param parseInt
	 * @param string
	 */
	@Query("update Courier set deltag =?2 where id =?1")
	@Modifying // 如果是增删改语句 必须打开事物的可读写
	void updateDeleteById(Integer id, Character deltag);

	@Query("from Courier where id =?1 and deltag =?2")
	Courier findByIdAndDeltag(Integer id, Character deltag);

	/**
	 * 根据删除标记来查询
	 * 
	 * @param c
	 * @return
	 */
	List<Courier> findByDeltag(Character deltag);

}

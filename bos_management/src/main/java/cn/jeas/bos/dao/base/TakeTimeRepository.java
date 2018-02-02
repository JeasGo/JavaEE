package cn.jeas.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.jeas.bos.domain.base.TakeTime;

public interface TakeTimeRepository extends JpaRepository<TakeTime, Integer>{

	List<TakeTime> findByStatus(String status);

}

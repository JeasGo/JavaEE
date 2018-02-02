package cn.jeas.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.jeas.bos.domain.base.WorkBill;

public interface WorkBillRepository extends JpaRepository<WorkBill, Integer> {
	
}

package cn.itcast.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.take_delivery.WayBill;

//运单dao、接口
public interface WayBillRepository extends JpaRepository<WayBill, Integer>{

}

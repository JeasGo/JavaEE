package cn.jeas.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.jeas.bos.domain.base.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}

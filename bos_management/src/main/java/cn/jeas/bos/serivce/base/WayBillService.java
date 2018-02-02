package cn.jeas.bos.serivce.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.jeas.bos.domain.base.WayBill;

public interface WayBillService {

	/**
	 * 功能:查询分页信息
	 * Author:jeas
	 * @param pageable
	 * @return
	 * Time:2018年1月23日 上午10:55:50
	 */
	Page<WayBill> findByListPage(Pageable pageable);
	
	/**
	 * 功能:保存订单
	 * Author:jeas
	 * @param model
	 * Time:2018年1月23日 上午11:08:05
	 */
	void saveWayBillQuick(WayBill model);
	
	/**
	 * 功能:根据查询条件查询订单
	 * Author:jeas
	 * @param pageable
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * Time:2018年1月26日 上午10:34:09
	 */
	Page<WayBill> findWayBillListPage(Pageable pageable, String fieldName, String fieldValue);

}

package cn.itcast.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.take_delivery.WayBill;

//运单业务层
public interface WayBillService {

	/**
	 * 
	 * 说明：快速录入添加一个运单
	 * @param wayBill
	 * @author 传智.BoBo老师
	 * @time：2017年10月31日 下午4:06:37
	 */
	void saveWayBillQuick(WayBill wayBill);

	/**
	 * 
	 * 说明：分页列表查询所有的运单
	 * @param pageable
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月31日 下午4:45:23
	 */
	Page<WayBill> findWayBillListPage(Pageable pageable);

	/**
	 * 
	 * 说明：基于es索引查询分页列表
	 * @param pageable
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年11月2日 下午4:54:59
	 */
	Page<WayBill> findWayBillListPage(Pageable pageable, String fieldName, String fieldValue);

}

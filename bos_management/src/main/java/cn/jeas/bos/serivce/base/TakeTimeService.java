package cn.jeas.bos.serivce.base;

import java.util.List;

import cn.jeas.bos.domain.base.TakeTime;

public interface TakeTimeService {

	/**
	 * 查询未作废在这个时间段上下班的快递员
	 * @return
	 */
	List<TakeTime> findTakeTimeListNoDeltag();

}

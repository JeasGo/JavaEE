package cn.itcast.bos.service.base;

import java.util.List;

import cn.itcast.bos.domain.base.TakeTime;

public interface TakeTimeService {

	/**
	 * 
	 * 说明：查询没有作废的取派时间
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月27日 下午6:20:57
	 */
	List<TakeTime> findTakeTimeNoDel();

}

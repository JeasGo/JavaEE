package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

//定区业务层接口
public interface FixedAreaService {

	/**
	 * 
	 * 说明：保存定区
	 * @param fixedArea
	 * @author 传智.BoBo老师
	 * @time：2017年10月19日 下午6:02:40
	 */
	void saveFixedArea(FixedArea fixedArea);

	/**
	 * 
	 * 说明：组合条件分页查询
	 * @param spec
	 * @param pageable
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月19日 下午6:30:08
	 */
	Page<FixedArea> findFixedAreaListPage(Specification<FixedArea> spec, Pageable pageable);

	/**
	 * 
	 * 说明：定区关联快递员
	 * @param fixedArea
	 * @param courierId
	 * @param takeTimeId
	 * @author 传智.BoBo老师
	 * @time：2017年10月27日 下午6:39:01
	 */
	void associationCourierToFixedArea(FixedArea fixedArea, Integer courierId, Integer takeTimeId);

}

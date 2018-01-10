package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

//快递员业务层接口
public interface CourierService {

	/**
	 * 
	 * 说明：保存快递员
	 * @param courier
	 * @author 传智.BoBo老师
	 * @time：2017年10月16日 下午5:45:46
	 */
	void saveCourier(Courier courier);
	
	/**
	 * 
	 * 说明：组合条件分页查询
	 * @param spec 业务条件
	 * @param pageable 分页条件
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月18日 下午3:56:11
	 */
	Page<Courier> findCourierListPage(Specification<Courier> spec, Pageable pageable);

	/**
	 * 
	 * 说明：批量删除快递员
	 * @param ids
	 * @author 传智.BoBo老师
	 * @time：2017年10月18日 下午5:19:46
	 */
	void deleteCourierBatch(String ids);

	/**
	 * 
	 * 说明：查询没有删除标记的快递员
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月27日 下午6:06:11
	 */
	List<Courier> findCourierListNoDeltag();

}

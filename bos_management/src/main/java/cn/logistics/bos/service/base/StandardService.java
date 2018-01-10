package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Standard;

//取派标准的业务层接口
public interface StandardService {

	/**
	 * 
	 * 说明：保存一个标准数据
	 * @param standard
	 * @author 传智.BoBo老师
	 * @time：2017年10月15日 下午6:15:30
	 */
	void saveStandard(Standard standard);
	
	/**
	 * 
	 * 说明：分页列表查询
	 * @param pageable
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月16日 下午3:16:52
	 */
	Page<Standard> findStandardListPage(Pageable pageable);

	/**
	 * 
	 * 说明：查询所有的标准
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月16日 下午5:16:17
	 */
	List<Standard> findStandardList();
	
	
	

}

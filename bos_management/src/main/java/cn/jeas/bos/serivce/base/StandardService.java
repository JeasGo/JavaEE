package cn.jeas.bos.serivce.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.jeas.bos.domain.base.Standard;

//取派标准的业务层接口
public interface StandardService {

	/**
	 * 说明: 保存一个标准数据
	 * @param standard
	 */
	void saveStandard(Standard standard);

	/**
	 * 说明:分页查询  方式:选择SpringDatajpa  利用Pageable
	 * @param pageable
	 * @return
	 */
	Page<Standard> findStandardListPage(Pageable pageable);

	List<Standard> findStandardList();

}

package cn.jeas.bos.serivce.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.jeas.bos.domain.base.FixedArea;

public interface FixedAreaService {

	/**
	 * 功能:添加FixedArea pojo
	 * 
	 * @param model
	 */
	void add(FixedArea model);

	Page<FixedArea> findFixedAreaListPage(Specification<FixedArea> spec, Pageable pageable);

	void saveFixedArea(FixedArea model);

	/**
	 * 功能:定区关联快递员
	 * 
	 * @param fixedArea
	 * @param courierId
	 * @param takeTimeId
	 */
	void associationCourierToFixedArea(FixedArea fixedArea, Integer courierId, Integer takeTimeId);

}

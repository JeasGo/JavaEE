package cn.jeas.bos.serivce.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.jeas.bos.domain.base.SubArea;

public interface SubAreaService {

	/**
	 * 功能:批量保存所有分区
	 * Author:jeas
	 * @param subAreaList
	 * Time:2018年1月26日 上午11:51:37
	 */
	void saveAll(List<SubArea> subAreaList);

	/**
	 * 分页查询
	 * Author:jeas
	 * @param pageable
	 * @return
	 * Time:2018年1月26日 下午2:57:35
	 */
	Page<SubArea> findAreaListPage(Pageable pageable);

}

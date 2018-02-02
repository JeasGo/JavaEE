package cn.jeas.bos.serivce.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.jeas.bos.domain.base.Area;

public interface AreaService {

	/**
	 * 说明：批量保存区域
	 * 
	 * @param areaList
	 */
	void saveArea(List<Area> areaList);

	/**
	 * 说明: 分页展示区域数据
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Area> findAreaListPage(Pageable pageable);

	/**
	 * 查询地区
	 * Author:jeas
	 * @param province
	 * @param city
	 * @param distrcict
	 * @return
	 * Time:2018年1月26日 下午3:10:57
	 */
	Area findByProvinceAndCityAndDistrict(String province, String city, String distrcict);

}

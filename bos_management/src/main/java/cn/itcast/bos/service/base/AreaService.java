package cn.itcast.bos.service.base;

import java.util.List;

import cn.itcast.bos.domain.base.Area;

//区域业务层接口
public interface AreaService {

	/**
	 * 
	 * 说明：批量保存区域
	 * @param areaList
	 * @author 传智.BoBo老师
	 * @time：2017年10月19日 下午4:21:54
	 */
	void saveArea(List<Area> areaList);

}

package cn.itcast.bos.dao.base;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.Area;

//区域持久层接口
public interface AreaRepository extends JpaRepository<Area, String>{

	//根据省市区定位查询一个区域
	public Area findByProvinceAndCityAndDistrict(String province,String city,String district);
}

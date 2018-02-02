package cn.jeas.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.jeas.bos.domain.base.Area;

public interface AreaRepository extends JpaRepository<Area, String> {

	Area findByProvinceLikeAndCityLikeAndDistrictLike(String province, String city, String district);
	//Area finByProvinceLikeAndCityLikeAndDistrictLike(String province, String city,String district);

	Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}

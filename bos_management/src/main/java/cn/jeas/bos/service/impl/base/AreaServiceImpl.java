package cn.jeas.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.AreaRepository;
import cn.jeas.bos.domain.base.Area;
import cn.jeas.bos.serivce.base.AreaService;

@Service("areaService")
@Transactional
public class AreaServiceImpl implements AreaService {
	
	
	@Autowired
	private AreaRepository areaRepository;

	
	@Override
	public void saveArea(List<Area> areaList) {
		areaRepository.save(areaList);

	}


	@Override
	public Page<Area> findAreaListPage(Pageable pageable) {
		Page<Area> page = areaRepository.findAll(pageable);
		return page;
	}


	@Override
	public Area findByProvinceAndCityAndDistrict(String province, String city, String district) {
		province= "%"+province+"%";
		city= "%"+city+"%";
		district= "%"+district+"%";
		Area area = areaRepository.findByProvinceLikeAndCityLikeAndDistrictLike(province, city,district);
		return area;
	}

}

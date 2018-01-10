package cn.itcast.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
//区域业务层实现
@Service("areaService")
@Transactional
public class AreaServiceImpl implements AreaService {
	//注入dao
	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void saveArea(List<Area> areaList) {
		areaRepository.save(areaList);
	}

}

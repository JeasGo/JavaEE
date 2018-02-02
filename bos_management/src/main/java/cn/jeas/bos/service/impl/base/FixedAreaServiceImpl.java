package cn.jeas.bos.service.impl.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.CourierRepository;
import cn.jeas.bos.dao.base.FixedAreaRepository;
import cn.jeas.bos.dao.base.TakeTimeRepository;
import cn.jeas.bos.domain.base.Courier;
import cn.jeas.bos.domain.base.FixedArea;
import cn.jeas.bos.domain.base.TakeTime;
import cn.jeas.bos.serivce.base.FixedAreaService;

@Service("fixedAreaService")
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Autowired
	private CourierRepository courierRepository;
	
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	
	@Override
	public void add(FixedArea model) {
		fixedAreaRepository.save(model);
	}

	@Override
	public Page<FixedArea> findFixedAreaListPage(Specification<FixedArea> spec, Pageable pageable) {
		Page<FixedArea> findAll = fixedAreaRepository.findAll(spec, pageable);
		return findAll;
	}

	@Override
	public void saveFixedArea(FixedArea model) {
		FixedArea save = fixedAreaRepository.save(model);
		System.out.println(save);
		
	}

	@Override
	public void associationCourierToFixedArea(FixedArea fixedArea, Integer courierId, Integer takeTimeId) {
		FixedArea findOne = fixedAreaRepository.findOne(fixedArea.getId());
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		
		//关联
		findOne.getCouriers().add(courier);
		courier.setTakeTime(takeTime);

		//等待flush  快照保存  效率更快
	}

}

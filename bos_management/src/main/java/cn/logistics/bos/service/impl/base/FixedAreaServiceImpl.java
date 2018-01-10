package cn.itcast.bos.service.impl.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;
//定区业务层实现
@Service("fixedAreaService")
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	//注入dao
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	//注入快递员的dao
	@Autowired
	private CourierRepository courierRepository;
	//注入取派时间的dao
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public void saveFixedArea(FixedArea fixedArea) {
		fixedAreaRepository.save(fixedArea);
	}

	@Override
	public Page<FixedArea> findFixedAreaListPage(Specification<FixedArea> spec, Pageable pageable) {
		return fixedAreaRepository.findAll(spec, pageable);
	}

	@Override
	public void associationCourierToFixedArea(FixedArea fixedArea, Integer courierId, Integer takeTimeId) {
		
		//涉及到多对多和一对多保存（持久态对象的关系维护）
		//1）定区和快递员:多对多
		//查询定区
		FixedArea fixedAreaPersist = fixedAreaRepository.findOne(fixedArea.getId());
		//查询快递员
		Courier courierPersist = courierRepository.findOne(courierId);
		
		//关系维护（中间表的数据维护）
		fixedAreaPersist.getCouriers().add(courierPersist);
		//等flush
		
		//2)快递员和取派时间
		//查询取派时间
		TakeTime takeTimePersist = takeTimeRepository.findOne(takeTimeId);
		//快照更新
		courierPersist.setTakeTime(takeTimePersist);
		//等flush
		
	}

}

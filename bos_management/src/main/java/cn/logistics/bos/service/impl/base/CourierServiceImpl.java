package cn.itcast.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
//快递员业务层实现
@Service("courierService")
@Transactional
public class CourierServiceImpl implements CourierService {

	//注入dao
	@Autowired
	private CourierRepository courierRepository;
	@Override
	public void saveCourier(Courier courier) {
		courierRepository.save(courier);
	}
	@Override
	public Page<Courier> findCourierListPage(Specification<Courier> spec, Pageable pageable) {
		return courierRepository.findAll(spec, pageable);
	}
	@Override
	public void deleteCourierBatch(String ids) {
		// 逻辑删除（更新操作）
		/*
		 * springdatajpa：save方法可以更新，得先查再更（所有字段）
		 * hibernate：update方法，先查再更。
		 * hibernate：快照更新，先查再改一级缓存的数据，等flush
		 * 今天：直接写update语句。
		 * 
		 */
		//先切
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			courierRepository.updateDeltagById(Integer.parseInt(id), '1');
		}
	}
	@Override
	public List<Courier> findCourierListNoDeltag() {
		return courierRepository.findByDeltag('0');
	}

}

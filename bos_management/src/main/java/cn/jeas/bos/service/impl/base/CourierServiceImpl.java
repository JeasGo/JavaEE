package cn.jeas.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.CourierRepository;
import cn.jeas.bos.domain.base.Courier;
import cn.jeas.bos.serivce.base.CourierService;

@Service("courierService")
@Transactional
public class CourierServiceImpl implements CourierService{
	
	//注入dao
	@Autowired
	private CourierRepository courierRepository;

	@Override
	public void saveCourier(Courier courier) {
		Courier save = courierRepository.save(courier);
		System.out.println(save);
	}

	@Override
	public Page<Courier> findCourierListPage(Specification<Courier> spec, Pageable pageable) {
		return courierRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteCourierBatch(String ids) throws Exception {
		String[] idArray = ids.split(",");
		for (String str : idArray) {
			int id = Integer.parseInt(str);
			Courier c = courierRepository.findByIdAndDeltag(id,'0');
			if (c!=null) {
				courierRepository.updateDeleteById(id,'1');
			} else {
				throw new Exception("快递员已作废");
			}
			
			
			
		}
	}

	@Override
	public List<Courier> finAll() {
		List<Courier> list = courierRepository.findAll();
		
		return list;
	}

	@Override
	public List<Courier> findCourierListNoDeltag() {
		return courierRepository.findByDeltag('0');
	}

//	@Override
//	public Page<Courier> findCourierListPage(Pageable pageable) {
//		Page<Courier> findAll = courierRepository.findAll(pageable);
//		return findAll;
//	}

}

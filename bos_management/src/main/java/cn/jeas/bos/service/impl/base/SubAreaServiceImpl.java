package cn.jeas.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.CourierRepository;
import cn.jeas.bos.dao.base.FixedAreaRepository;
import cn.jeas.bos.dao.base.SubAreaRepository;
import cn.jeas.bos.dao.base.TakeTimeRepository;
import cn.jeas.bos.domain.base.Courier;
import cn.jeas.bos.domain.base.FixedArea;
import cn.jeas.bos.domain.base.SubArea;
import cn.jeas.bos.domain.base.TakeTime;
import cn.jeas.bos.serivce.base.FixedAreaService;
import cn.jeas.bos.serivce.base.SubAreaService;

@Service("subAreaService")
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	private SubAreaRepository subAreaRepository;
	@Override
	public void saveAll(List<SubArea> subAreaList) {
		subAreaRepository.save(subAreaList);
	}
	@Override
	public Page<SubArea> findAreaListPage(Pageable pageable) {
		return subAreaRepository.findAll(pageable);
	}


}

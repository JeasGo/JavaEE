package cn.jeas.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.TakeTimeRepository;
import cn.jeas.bos.domain.base.TakeTime;
import cn.jeas.bos.serivce.base.TakeTimeService;

@Transactional
@Service("takeTimeService")
public class TakeTimeServiceImpl implements TakeTimeService {
	
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	
	@Override
	public List<TakeTime> findTakeTimeListNoDeltag() {
		return takeTimeRepository.findByStatus("1");
		
	}
}


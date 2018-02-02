package cn.jeas.bos.service.impl.base;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.StandardRepository;
import cn.jeas.bos.domain.base.Standard;
import cn.jeas.bos.serivce.base.StandardService;

@Service("standardService")
@Transactional
public class StandardServiceImpl implements StandardService {

	//注入dao
	@Autowired
	private StandardRepository standardRepository;
	
	@Override
	@RequiresRoles("base111")
	public void saveStandard(Standard standard) {
		standardRepository.save(standard);
	}

	@Override
	public Page<Standard> findStandardListPage(Pageable pageable) {
		return standardRepository.findAll(pageable);
	}

	@Override
	public List<Standard> findStandardList() {
		return standardRepository.findAll();
	}

}

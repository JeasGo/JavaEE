package cn.itcast.bos.service.impl.base;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
//取派标准的额业务层实现
@Service("standardService")
@Transactional
public class StandardServiceImpl implements StandardService {
	//注入dao
	@Autowired
	private StandardRepository standardRepository;
	@Override
	/**
	 * 目标：方法级别的权限控制该方法的访问
	 */
	@RequiresRoles("base1")//阻止该方法的执行，如果没有该角色权限的情况下
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

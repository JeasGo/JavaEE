package cn.jeas.bos.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.system.PermissionRepository;
import cn.jeas.bos.domain.system.Permission;
import cn.jeas.bos.serivce.system.PermissionService;

@Transactional
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	public List<Permission> findPermissionList() {
		return permissionRepository.findAll();
		
	}

	@Override
	public void addPermission(Permission permission) {
		permissionRepository.save(permission);
	}

}

package cn.jeas.bos.service.impl.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.system.MenuRepository;
import cn.jeas.bos.dao.system.PermissionRepository;
import cn.jeas.bos.dao.system.RoleRepository;
import cn.jeas.bos.domain.system.Menu;
import cn.jeas.bos.domain.system.Permission;
import cn.jeas.bos.domain.system.Role;
import cn.jeas.bos.serivce.system.RoleService;

@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private	MenuRepository menuRepository;
	
	
	@Override
	public List<Role> findAllRole() {
		return roleRepository.findAll();
	}

	@Override
	public void saveRole(Role role, Integer[] permissionIds, String menuIds) {
		roleRepository.save(role);//持久态
		if(permissionIds!=null){
			for (Integer permissionId : permissionIds) {
				Permission permission = permissionRepository.findOne(permissionId);
				//关联
//				permission.getRoles().add(role);//权限关联到角色，不行，因为权限放弃了外键维护权@ManyToMany(mappedBy = "permissions")
				role.getPermissions().add(permission);//角色关联权限
				//等flush
			}
		}
		
		//3)角色和菜单的关联-快照
		if(StringUtils.isNotBlank(menuIds)){
			String[] menuIdArray = menuIds.split(",");
			for (String menuId : menuIdArray) {
				Menu menu = menuRepository.findOne(Integer.parseInt(menuId));
				//关联
				role.getMenus().add(menu);//角色关联菜单
				//等flush
			}
		}

	}

}

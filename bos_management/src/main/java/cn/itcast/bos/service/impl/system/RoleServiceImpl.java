package cn.itcast.bos.service.impl.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuRepository;
import cn.itcast.bos.dao.system.PermissionRepository;
import cn.itcast.bos.dao.system.RoleRepository;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;
//角色业务实现
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	//注入dao
	@Autowired
	private RoleRepository roleRepository;
	//注入权限dao
	@Autowired
	private PermissionRepository permissionRepository;
	//注入菜单dao
	@Autowired
	private MenuRepository menuRepository;
	@Override
	public List<Role> findRoleList() {
		return roleRepository.findAll();
	}
	@Override
	public void saveRole(Role role, String[] permissionIds, String menuIds) {
		//1.保存角色
		roleRepository.save(role);
		//2.多对多的表的保存：只能通过持久态关系维护
		//功能权限
		if(permissionIds!=null){
			for (String permissionId : permissionIds) {
				Permission permission = permissionRepository.findOne(Integer.parseInt(permissionId));
				role.getPermissions().add(permission);
			}
		}
		
		//菜单
		if(StringUtils.isNoneBlank(menuIds)){
			String[] menuIdArray = menuIds.split(",");
			for (String menuId : menuIdArray) {
				Menu menu = menuRepository.findOne(Integer.parseInt(menuId));
				role.getMenus().add(menu);
			}
		}
		
		//等flush
		
	}

}

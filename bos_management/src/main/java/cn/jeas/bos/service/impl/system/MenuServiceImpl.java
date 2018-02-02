package cn.jeas.bos.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.system.MenuRepository;
import cn.jeas.bos.domain.system.Menu;
import cn.jeas.bos.domain.system.User;
import cn.jeas.bos.serivce.system.MenuService;

@Transactional
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private  MenuRepository menuRepository;
	
	@Override
	@CacheEvict(value="bos_menu_cache")
	public List<Menu> findMenuList() {
		
		return menuRepository.findAll();
	}

	@Override
	@CacheEvict(value="bos_menu_cache",allEntries=true)
	public void saveMenu(Menu menu) {
		//处理父菜单不选择的问题
		if(menu.getParentMenu()!=null && menu.getParentMenu().getId()==null){
			menu.setParentMenu(null);
		}

		menuRepository.save(menu);
		
	}

	@Override
	public List<Menu> findMenuByUser(User user) {
		//判断用户
				if("admin".equals(user.getUsername())){
					//管理员,所有菜单
					return menuRepository.findByOrderByPriority();
				}else{
					//普通用户，部分菜单
					List<Menu> findByUser = menuRepository.findByUser(user);
					return findByUser;
				}

	}

}

package cn.itcast.bos.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuRepository;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;
//菜单业务层实现
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	//注入dao
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<Menu> findMenuList() {
		return menuRepository.findAll();
	}

	@Override
	//需要在增删改，清除缓存区域
	@CacheEvict(value="bos_menu_cache",allEntries=true)
	public void saveMenu(Menu menu) {
		//单独对父menu进行处理
		if(menu.getParentMenu()!=null&&menu.getParentMenu().getId()==null){
			menu.setParentMenu(null);
		}
		
		menuRepository.save(menu);
	}

	@Override
	//value：缓存区域名字
//	@Cacheable(value="bos_menu_cache")//将结果缓存到ehcache
	
	/*
	 * value:返回的结果
	 * key是什么？
	 * 
	 * 1）如果参数没有，则key是固定值SimpleKey.EMPTY
	 * 2）如果参数有一个，key是对象的地址算法值
	 * 3）如果参数有多个，key是参数对象的hash算法后的值。
	 * key:支持spEl表达式
	 */
	@Cacheable(value="bos_menu_cache",key="#user.id")
	public List<Menu> findMenuListByUser(User user) {
		
		//判断
		if(user.getUsername().equals("admin")){
			//超管:查询所有
			return menuRepository.findByOrderByPriority();
		}else{
			//普通用户
			return menuRepository.findByUser(user);
		}
		
		
	}

}

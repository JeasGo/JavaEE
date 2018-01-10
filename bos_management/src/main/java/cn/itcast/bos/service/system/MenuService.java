package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

//菜单业务层接口
public interface MenuService {

	/**
	 * 
	 * 说明：查询所有菜单列表
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年11月5日 下午5:12:25
	 */
	List<Menu> findMenuList();

	/**
	 * 
	 * 说明：保存菜单
	 * @param menu
	 * @author 传智.BoBo老师
	 * @time：2017年11月5日 下午5:12:17
	 */
	void saveMenu(Menu menu);

	/**
	 * 
	 * 说明：根据用户查询菜单
	 * @param user
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年11月6日 下午3:43:46
	 */
	List<Menu> findMenuListByUser(User user);

}

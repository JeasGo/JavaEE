package cn.jeas.bos.serivce.system;

import java.util.List;

import cn.jeas.bos.domain.system.Menu;
import cn.jeas.bos.domain.system.User;

public interface MenuService {

	/**
	 * 功能:查询所有菜单列表
	 * Author:jeas
	 * @return
	 * Time:2018年1月31日 上午10:42:33
	 */
	List<Menu> findMenuList();

	/**
	 * 
	 * 功能:保存菜单
	 * Author:jeas
	 * @param model
	 * Time:2018年1月31日 上午11:14:44
	 */
	void saveMenu(Menu menu);
	/**
	 * 功能:根据角色权限查询菜单
	 * Author:jeas
	 * @param user
	 * @return
	 * Time:2018年2月1日 下午9:22:13
	 */
	List<Menu> findMenuByUser(User user);

}

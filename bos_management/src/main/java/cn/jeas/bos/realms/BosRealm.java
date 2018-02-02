package cn.jeas.bos.realms;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.jeas.bos.dao.system.PermissionRepository;
import cn.jeas.bos.dao.system.RoleRepository;
import cn.jeas.bos.dao.system.UserRepository;
import cn.jeas.bos.domain.system.Permission;
import cn.jeas.bos.domain.system.Role;
import cn.jeas.bos.domain.system.User;

@Component("bosRealm")
public class BosRealm extends AuthorizingRealm {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	//注入功能权限dao
	@Autowired
	private PermissionRepository permissionRepository;

	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权数据获取中。。。。");
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		User user=(User)principals.getPrimaryPrincipal();//推荐
		//2)根据用户判断权限
		if(user.getUsername().equals("admin")){
			//超管
			//查询所有角色权限
			List<Role> roleList = roleRepository.findAll();
			for (Role role : roleList) {
				//添加角色权限
				authorizationInfo.addRole(role.getKeyword());
			}
			//查询所有的功能权限
			List<Permission> permissionList = permissionRepository.findAll();
			for (Permission permission : permissionList) {
				//添加功能权限
				authorizationInfo.addStringPermission(permission.getKeyword());
			}
			
		}else{
			//普通用户
			//根据用户查询角色
			List<Role> roleList = roleRepository.findByUsers(user);
			for (Role role : roleList) {
				//添加角色权限
				authorizationInfo.addRole(role.getKeyword());
				
				//导航查询:查询角色拥有的功能权限
				Set<Permission> permissionSet = role.getPermissions();
				for (Permission permission : permissionSet) {
					//添加功能权限
					authorizationInfo.addStringPermission(permission.getKeyword());
				}
			}
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证数据获取中。。。。");
		String username=((UsernamePasswordToken)token).getUsername();
		//查询数据库:根据用户查询对象
		User user = userRepository.findByUsername(username);
		//判断
		if(null==user){
			//如果返回null，安全管理器认为用户名不存在。
			return null;
		}else{
			//用户名存在，将用户信息封装到认证信息对象中，叫给安全管理器
			SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo
			/*
			 * 参数1：principal:首长，主要负责人，就是用户对象，将来shiro会将user对象放入“session”中
			 * 参数2：credential：凭证，这里就是真实密码
			 * 参数3：realm的对象的一个唯一的名字，类似uuid，底层就是一个随机数
			 */
				(user, user.getPassword(), super.getName());
			
			return authenticationInfo;
		}

	}

}

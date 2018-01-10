package cn.itcast.bos.realms;

import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.itcast.bos.dao.system.PermissionRepository;
import cn.itcast.bos.dao.system.RoleRepository;
import cn.itcast.bos.dao.system.UserRepository;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
//bos的realm安全数据提供者
@Component("bosRealm")
public class BosRealm extends AuthorizingRealm{
	
	@Value("bos_realm_authentication_cache")
	public void setSuperAuthenticationCacheName(String authenticationCacheName){
		super.setAuthenticationCacheName(authenticationCacheName);
	}
	//向父类注入授权的缓存区域的名字（重要）
	@Value("bos_realm_authorization_cache")
	public void setSuperAuthorizationCacheName(String authorizationCacheName){
		super.setAuthorizationCacheName(authorizationCacheName);
	}

	
	
	//注入user的dao
	@Autowired
	private UserRepository userRepository;
	
	//注入角色权限dao
	@Autowired
	private RoleRepository roleRepository;
	//注入功能权限dao
	@Autowired
	private PermissionRepository permissionRepository;
	@Override
	//提供授权的安全数据
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("===========正在获取授权的安全数据。");
		//授权数据信息对象
		SimpleAuthorizationInfo authorizationInfo =new SimpleAuthorizationInfo();
		
		//====写死安全数据（仅限测试）
		//添加功能权限
//		authorizationInfo.addStringPermission("courier:list");
//		//添加角色权限
//		authorizationInfo.addRole("base");
		
		//=====动态从数据库中根据登录用户读取不同的安全数据
		//获取当前登录用户
		//两种方法
		//方法1：代码任何地方都可以调用该工具类获取用户
//		User user = (User)SecurityUtils.getSubject().getPrincipal();
		//方法2：通过参数获取当前用户
		User user = (User)principals.getPrimaryPrincipal();
		
		//判断用户类型
		if("admin".equals(user.getUsername())){
			//超管：拥有所有的角色和功能权限
			//角色权限
			List<Role> roleList = roleRepository.findAll();
			for (Role role : roleList) {
				authorizationInfo.addRole(role.getKeyword());
			}
			//功能权限
			List<Permission> permissionList = permissionRepository.findAll();
			for (Permission permission : permissionList) {
				authorizationInfo.addStringPermission(permission.getKeyword());
			}
			
		}else{
			//普通用户
			//角色权限：根据用户来查询
			List<Role> roleList = roleRepository.findByUsers(user);
			for (Role role : roleList) {
				authorizationInfo.addRole(role.getKeyword());
				//Hibernate有个导航查询,直接通过查询出来的角色，导航查询所有包含的功能权限
				Set<Permission> permissionSet = role.getPermissions();
				for (Permission permission : permissionSet) {
					authorizationInfo.addStringPermission(permission.getKeyword());
				}
			}
			
		}
		
		return authorizationInfo;
	}

	@Override
	//提供认证的安全数据
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("===========正在获取认证的安全数据。");
		//第一步：获取登录用户名
		String username = ((UsernamePasswordToken)token).getUsername();
		
		//第二步：根据用户名到数据库查询用户对象
		User user = userRepository.findByUsername(username);
		
		//第三步：判断并封装结果返回
		if(null==user){
			//用户名不存在
			return null;
		}else{
			
			//判断用户是否锁定了
			if(user.getStatus().equals("0")){
				//锁定
				throw new LockedAccountException("realm：帐号被锁定了。。。");
			}
			
			//给父类指定加密手段
			super.setCredentialsMatcher(new PasswordMatcher());
			
			//将认证安全数据封装后交给安全管理器
			//参数1：principal首长、负责人，这里就是用户对象，将来安全管理器会将该对象放入“session”
			//参数2：credentials 凭证，这里就是密码
			//参数3：当前reaml的名字，不能重复。直接父类提供。
			AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user, user.getPassword(), super.getName());
			//返回给安全管理
			return authenticationInfo;
		}
		
	}

}

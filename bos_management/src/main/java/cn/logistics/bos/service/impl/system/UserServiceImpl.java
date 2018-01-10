package cn.itcast.bos.service.impl.system;

import java.util.List;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.RoleRepository;
import cn.itcast.bos.dao.system.UserRepository;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;
import cn.itcast.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	//注入dao
	@Autowired
	private UserRepository userRepository;
	//注入角色dao
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<User> findUserList() {
		return userRepository.findAll();
	}

	@Override
	public void saveUser(User user, String[] roleIds) {
		//1 保存用户
//		userRepository.save(user);//持久态，抢占主键-Hibernate提供的主键--要序列
		//加密并保存用户
//		user.setPassword(MD5Utils.md5(user.getPassword()));
		//使用shiro的加密机制：
		//使用shiro默认强加密
		DefaultPasswordService passwordService=new DefaultPasswordService();
		String encryptPassword = passwordService.encryptPassword(user.getPassword());
		user.setPassword(encryptPassword);
		
		
		userRepository.save(user);
		//2.用户和角色关联（中间表）
		if(roleIds!=null){
			for (String roleId : roleIds) {
				Role role = roleRepository.findOne(Integer.parseInt(roleId));
				user.getRoles().add(role);
				//使用Hibernate的快照机制、
				//flush之前
			}
		}
		
	}

	@Override
	public void updateUserStatusByActivetimeForLock() {
		userRepository.updateStatusByActivetimeForLock(3);
	}

}

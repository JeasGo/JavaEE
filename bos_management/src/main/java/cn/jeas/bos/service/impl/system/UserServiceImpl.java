package cn.jeas.bos.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.system.RoleRepository;
import cn.jeas.bos.dao.system.UserRepository;
import cn.jeas.bos.dao.system.UserService;
import cn.jeas.bos.domain.system.Role;
import cn.jeas.bos.domain.system.User;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private	RoleRepository roleRepository;
	
	@Override
	public void saveUser(User user, Integer[] roleIds) {
		userRepository.save(user);
		if (roleIds!=null) {
			for (Integer roleId : roleIds) {
				Role role = roleRepository.findOne(roleId);
				//关联：用户关联角色
				user.getRoles().add(role);
				//等flush
			}

		}
	}

	@Override
	public List<User> findUserList() {
		List<User> list = userRepository.findAll();
		return list;
	}

}

package cn.jeas.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.jeas.bos.domain.system.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}

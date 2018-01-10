package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.FixedArea;

//定区dao层
public interface FixedAreaRepository extends JpaRepository<FixedArea, String>
, JpaSpecificationExecutor<FixedArea>{

}

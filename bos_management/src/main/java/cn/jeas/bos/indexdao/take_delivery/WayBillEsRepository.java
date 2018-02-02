package cn.jeas.bos.indexdao.take_delivery;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.jeas.bos.domain.base.WayBill;

public interface WayBillEsRepository extends ElasticsearchRepository<WayBill, Integer> {
	
}

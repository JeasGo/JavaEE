package cn.jeas.bos.service.impl.base;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.WayBillRepository;
import cn.jeas.bos.domain.base.WayBill;
import cn.jeas.bos.indexdao.take_delivery.WayBillEsRepository;
import cn.jeas.bos.serivce.base.WayBillService;

@Transactional
@Service("wayBillService")
public class WayBillServiceImpl implements WayBillService {

	@Autowired
	private WayBillRepository wayBillRepository;
	
	@Autowired
	private WayBillEsRepository wayBillEsRepository;
	
	@Override
	public Page<WayBill> findByListPage(Pageable pageable) {
		
		return wayBillRepository.findAll(pageable);
	}

	@Override
	public void saveWayBillQuick(WayBill model) {
		wayBillRepository.save(model);
		wayBillEsRepository.save(model);
	}

	@Override
	public Page<WayBill> findWayBillListPage(Pageable pageable, String fieldName, String fieldValue) {
		TermQueryBuilder termQueryBuilder=new TermQueryBuilder(fieldName, fieldValue);
		//通配符匹配
		WildcardQueryBuilder wildcardQueryBuilder=new WildcardQueryBuilder(fieldName, "*"+fieldValue+"*");
		
		//组合条件对象
		BoolQueryBuilder query=new BoolQueryBuilder();
		query.should(termQueryBuilder);//并集
		query.should(wildcardQueryBuilder);

		return wayBillEsRepository.search(query, pageable);
	}
}

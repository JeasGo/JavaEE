package cn.itcast.bos.service.impl.take_delivery;

import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WayBillRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.indexdao.WayBillEsRepository;
import cn.itcast.bos.service.take_delivery.WayBillService;
//运单业务实现
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
	//注入dao
	@Autowired
	private WayBillRepository wayBillRepository;
	
	//注入esdao
	@Autowired
	private WayBillEsRepository wayBillEsRepository;

	@Override
	public void saveWayBillQuick(WayBill wayBill) {
		//将完整数据信息保存到数据库
		wayBillRepository.save(wayBill);
		//将索引信息保存到es中
		wayBillEsRepository.save(wayBill);
	}

	@Override
	public Page<WayBill> findWayBillListPage(Pageable pageable) {
		return wayBillRepository.findAll(pageable);
	}

	@Override
	public Page<WayBill> findWayBillListPage(Pageable pageable, String fieldName, String fieldValue) {
		//基于索引库es来检索数据
		
		//构建查询规则
//		QueryBuilder query;
		
		//词条精确匹配
		TermQueryBuilder termQuery = QueryBuilders.termQuery(fieldName, fieldValue);
		//词条模糊匹配
		WildcardQueryBuilder wildcardQuery = QueryBuilders.wildcardQuery(fieldName, "*"+fieldValue+"*");
		
		//全文检索
		QueryStringQueryBuilder fullIndexQuery = QueryBuilders.queryStringQuery(fieldValue)
		//指定分词器，会覆盖默认的
		.analyzer("ik");
		
		
		//该Query对象，主要是用来结合其他规则
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//		boolQuery.must(queryBuilder);//某规则必须有效，必须走该规则，交集
//		boolQuery.mustNot(queryBuilder);//某规则必须无效，和上面相反，排除效果
//		boolQuery.should(queryBuilder);//可以有该规则，也可以没有。并集
		
		boolQuery.should(termQuery);
//		boolQuery.should(wildcardQuery);
		boolQuery.should(fullIndexQuery);
		
		//根据规则来分页搜索
		//参数1：规则查询对象
		//参数2：分页对象
		return wayBillEsRepository.search(boolQuery, pageable);
		
		//搜索后的结果，需要再重新从数据库查询一次
		
	}

}

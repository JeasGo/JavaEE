package cn.itheima.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/** 
 * @author 贾兵兵 
 * @date 2017年12月15日
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 * 加载配置文件;
 * 创建sessionFactory的对象
 * 创建类似session对象并且返回
 */
public class JPAUtils {
	private static EntityManagerFactory factory= null;
	//加载配置文件
	static {
		try {
			factory = Persistence.createEntityManagerFactory("myPersistence");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	public static EntityTransaction getTransaction(EntityManager entityManager) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		return tx;
	}
}

package cn.itheima.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** 
 * @author 贾兵兵 
 * @date 2017年12月11日
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class HibernateUtils {
	private static SessionFactory factory;
	/**
	 * hibernate中把所有编译时运行转为运行时异常
	 */
	static {
		
		try {

			//加载核心配置文件
			Configuration config = new Configuration().configure(); 
			//创建session工厂
			factory = config.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Session openSession() {
		return factory.openSession();
	}
	
	public static Session getCurrentSession() {
		return factory.getCurrentSession();
	}
}

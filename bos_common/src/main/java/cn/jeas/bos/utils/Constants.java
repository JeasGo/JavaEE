package cn.jeas.bos.utils;

/**
 * 常量工具类
 * @author jeas
 *
 */
public class Constants {
	//请求地址端口号
	
	/**
	 * 地址:http://localhost:9001
	 */
	public static final String BOS_MANAGEMENT_HOST = "http://localhost:9001";
	/**
	 * 地址:http://localhost:9002
	 */
	public static final String CRM_MANAGEMENT_HOST = "http://localhost:9002";
	/**
	 * 地址:http://localhost:9003
	 */
	public static final String BOS_FORE_HOST = "http://localhost:9003";
	/**
	 * 地址:http://localhost:9004
	 */
	public static final String BOS_SMS_HOST = "http://localhost:9004";
	/**
	 * 地址:http://localhost:9005
	 */
	public static final String BOS_MAIL_HOST = "http://localhost:9005";

	
	
	
	//路径
	
	
	/**
	 * 路径:/bos_management
	 */
	private static final String BOS_MANAGEMENT_CONTEXT = "/bos_management";
	/**
	 * 路径:/crm_management
	 */
	private static final String CRM_MANAGEMENT_CONTEXT = "/crm_management";
	/**
	 * 路径:/bos_fore
	 */
	private static final String BOS_FORE_CONTEXT = "/bos_fore";
	/**
	 * 路径:/bos_sms
	 */
	private static final String BOS_SMS_CONTEXT = "/bos_sms";
	/**
	 * 路径:/bos_mail
	 */
	private static final String BOS_MAIL_CONTEXT = "/bos_mail";
	
	
	//请求地址端口号+路径
	
	/**
	 * 地址:"http://localhost:9001/bos_management"
	 */
	public static final String BOS_MANAGEMENT_URL = BOS_MANAGEMENT_HOST + BOS_MANAGEMENT_CONTEXT;
	/**
	 * 地址:http://localhost:9002/crm_management
	 */
	public static final String CRM_MANAGEMENT_URL = CRM_MANAGEMENT_HOST + CRM_MANAGEMENT_CONTEXT;
	/**
	 * 地址:http://localhost:9003/bos_fore
	 */
	public static final String BOS_FORE_URL = BOS_FORE_HOST + BOS_FORE_CONTEXT;
	/**
	 * 地址:http://localhost:9004/bos_sms
	 */
	public static final String BOS_SMS_URL = BOS_SMS_HOST + BOS_SMS_CONTEXT;
	/**
	 * 地址:http://localhost:9005/bos_mail
	 */
	public static final String BOS_MAIL_URL = BOS_MAIL_HOST + BOS_MAIL_CONTEXT;

}

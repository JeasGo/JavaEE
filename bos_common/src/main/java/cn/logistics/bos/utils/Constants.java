package cn.itcast.bos.utils;
//系统常量
public class Constants {
	//主机信息
	public static final String BOS_MANAGEMENT_HOST = PropertyUtils.getValue("BOS_MANAGEMENT_HOST");
	public static final String CRM_MANAGEMENT_HOST = PropertyUtils.getValue("CRM_MANAGEMENT_HOST");
	public static final String BOS_FORE_HOST = PropertyUtils.getValue("BOS_FORE_HOST");
	public static final String BOS_SMS_HOST = PropertyUtils.getValue("BOS_SMS_HOST");
	public static final String BOS_MAIL_HOST = PropertyUtils.getValue("BOS_MAIL_HOST");

	//上下文信息
	private static final String BOS_MANAGEMENT_CONTEXT = PropertyUtils.getValue("BOS_MANAGEMENT_CONTEXT");;
	private static final String CRM_MANAGEMENT_CONTEXT = PropertyUtils.getValue("CRM_MANAGEMENT_CONTEXT");;
	private static final String BOS_FORE_CONTEXT = PropertyUtils.getValue("BOS_FORE_CONTEXT");;
	private static final String BOS_SMS_CONTEXT = PropertyUtils.getValue("BOS_SMS_CONTEXT");;
	private static final String BOS_MAIL_CONTEXT = PropertyUtils.getValue("BOS_MAIL_CONTEXT");;

	//URL信息
	public static final String BOS_MANAGEMENT_URL = BOS_MANAGEMENT_HOST + BOS_MANAGEMENT_CONTEXT;
	public static final String CRM_MANAGEMENT_URL = CRM_MANAGEMENT_HOST + CRM_MANAGEMENT_CONTEXT;
	public static final String BOS_FORE_URL = BOS_FORE_HOST + BOS_FORE_CONTEXT;
	public static final String BOS_SMS_URL = BOS_SMS_HOST + BOS_SMS_CONTEXT;
	public static final String BOS_MAIL_URL = BOS_MAIL_HOST + BOS_MAIL_CONTEXT;

}

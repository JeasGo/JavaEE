package cn.itcast.bos.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

//属性读取工具类
public class PropertyUtils {
	private static Map<String, String> map = null;

	private static void loadFile() {
		map = new HashMap<>();
		try {
			Properties p = new Properties();
			p.load(PropertyUtils.class.getClassLoader().getResourceAsStream("constant.properties"));
			Iterator<Object> it = p.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = p.getProperty(key);
				map.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String str) {
		if (map == null) {
			loadFile();
		}
		return (String) map.get(str);
	}
}
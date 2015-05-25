package com.stlz.util.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 属性文件读取工具类
 */
public class PropertiesUtil {

	public static void main(String[] args) {
		// TODO 相对路径和项目根目录对应
		String src="config/config.properties";
		if(new File(src).exists()){
			readProperties(src);
		}
	}

	// 读取properties的全部信息
	public static Map<String,String> readProperties(String filePath) {
		
		Map<String,String> map=new HashMap<String,String>();
		
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration<?> en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String property = props.getProperty(key);
				//System.out.println("key="+key +",property="+ property);
				map.put(key, property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 根据key读取value
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			System.out.println("key="+key +",value="+ value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

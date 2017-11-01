package com.movision.utils.propertiesLoader;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 *
 * @author zhuangyuhao
 *
 */
public class ApplicationPropertiesUtils {

	private static Properties properties = null;

	static {
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return properties.getProperty(key);
	}

}

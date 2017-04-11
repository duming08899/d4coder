package com.frame.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件读取
 *
 * @author duming
 * @date 2013-10-21
 */
public class SystemProperties {

    public static final String CARBO_ALERT_ON = "carbo.alert.on";

    private static SystemProperties instance = new SystemProperties();

    private Properties properties = new Properties();

    public static SystemProperties getInstance() {
        return instance;
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    private SystemProperties() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("d4coder.properties");
        try {
            if (inputStream != null) {
                properties.load(new InputStreamReader(inputStream, "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

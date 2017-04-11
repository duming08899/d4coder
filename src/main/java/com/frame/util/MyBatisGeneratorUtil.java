package com.frame.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * MBG JAVA方式生成DAO model mapper.xml
 *
 * @author duming
 */
public class MyBatisGeneratorUtil {
    /**
     * 根据配置文件生成dao model
     *
     * @param path
     * @throws Exception
     */
    public void generate(String path) throws Exception {
        File file = new File(path);
        file.mkdirs();
        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(FileUtil.getStream(FileUtil.getAbsPath() + "repo/mybatis-generator.xml"));
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
            myBatisGenerator.generate(null);
            if (warnings.size() == 0) {
                System.out.println("generate is success");
            } else {
                for (String warning : warnings) {
                    System.out.println(warning);
                }
            }
            System.out.println("---MBG自动生成结束");
        } catch (InvalidConfigurationException e) {
            throw e;
        }
    }

}
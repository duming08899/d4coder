package com.frame.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 精简mapper.xml 与模板中mapper.java文件匹配 完成分页
 *
 * @author duming
 */
public class XmlUtil {

    /**
     * 生成mbn配置文件
     *
     * @param pb
     * @return
     */
    public static boolean reloadMBGXml(ParameterBean pb) {
        try {
            System.out.println("---MBG自动生成开始");
            InputStream is = FileUtil.getStream(FileUtil.getAbsPath() + "mybatis-generator_temp.xml");
            System.out.println("---MBG获取模板：" + FileUtil.getAbsPath() + "mybatis-generator_temp.xml");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linetext = "";
            StringBuffer sbf = new StringBuffer();
            while ((linetext = br.readLine()) != null) {
                sbf.append(linetext);
                sbf.append("\r\n");
            }
            String context = sbf.toString();
            System.out.println("---MBG模板赋值");
            context = context.replace("t_mysqlDriver", StringUtil.toJavaPath(FileUtil.getAbsPath()));// 替换mysql驱动
            context = context.replace("t_ip", pb.getIp());// 数据库IP
            context = context.replace("t_db", pb.getDb());// 数据库
            context = context.replace("t_user", pb.getUser());// 用户名
            context = context.replace("t_pwd", pb.getPwd());// 密码
            context = context.replace("t_package", pb.getPackageRoot() + "." + pb.getPackageSys());// 包
            context = context.replace("t_table", pb.getTableName());// 表
            context = context.replace("t_class", StringUtil.toUpperCase(pb.getTableName()));// 类
            context = context.replace("target_project",pb.getRepoCodePath());

            System.out.println("      -驱动：" + StringUtil.toJavaPath(FileUtil.getAbsPath()) + "\\lib\\mysql-connector-java-5.1.20-bin.jar");
            System.out.println("      -连接：" + "jdbc:mysql://" + pb.getIp() + ":3306/" + pb.getDb() + "?user=" + pb.getUser() + "&password=" + pb.getPwd());
            System.out.println("      -映射：" + "Table:" + pb.getTableName() + " Class:" + StringUtil.toUpperCase(pb.getTableName()));
            saveFile(FileUtil.getAbsPath() + "/repo/", "mybatis-generator.xml", context);
            System.out.println("---MBG模板生成结束：" + FileUtil.getAbsPath() + "target/mybatis-generator.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 规范化mybatis生成的mapper文件
     *
     * @param spath
     * @param tPath
     * @param name
     * @return
     */
    public static boolean reloadMapperXml(String spath, String tPath,
                                          String name) {
        try {
            String linetext = "";
            String resultMapText = "";
            StringBuffer sbf = new StringBuffer();
            File f = new File(spath + name);
            BufferedReader br = new BufferedReader(new FileReader(f));
            int lineNum = 1;
            while ((linetext = br.readLine()) != null) {
                sbf.append(linetext);
                sbf.append("\r\n");
                if (lineNum == 4) {
                    resultMapText = linetext;
                }
                lineNum++;
            }
            br.close();
            String text = sbf.toString();

            if (text.contains("queryPage")) {
                return false;
            }

            int si = text.indexOf("<insert id=\"insert\"");
            int ei = text.indexOf("</insert>");
            String noinsert = text.substring(si, ei + 9);
            text = text.replace(noinsert, "");

            int su = text.indexOf("<update id=\"updateByPrimaryKey\"");
            int eu = text.indexOf("</update>", su);
            if (su > 0 && eu > 0) {
                String noupdate = text.substring(su, eu + 9);
                text = text.replace(noupdate, "");
            }

            text = text.replace("selectByPrimaryKey", "selectById");
            text = text.replace("deleteByPrimaryKey", "deleteById");
            text = text.replace("insertSelective", "insert");
            text = text.replace("updateByPrimaryKeySelective", "update");

            int sp = text.indexOf("<select");
            int ep = text.indexOf("</select>");

            String pageSql = "";
            if (sp > 0 && ep > 0) {
                pageSql = text.substring(sp, ep + 9);
            }
            pageSql = pageSql.replace("selectById", "queryPage");
            pageSql = pageSql.replace("where id = #{id,jdbcType=INTEGER}", "");

            int sc = resultMapText.indexOf("type=");
            if (sc > 0) {
                resultMapText = resultMapText.substring(sc + 6);
            }

            int ec = resultMapText.lastIndexOf("\"");
            if (ec > 0) {
                resultMapText = resultMapText.substring(0, ec);
            }

            pageSql = pageSql.replace("java.lang.Integer", resultMapText);

            text = text.replace("</mapper>", "");
            StringBuffer newSql = new StringBuffer(text);
            newSql.append(pageSql);
            newSql.append("</mapper>");

            saveFile(tPath, name, newSql.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void saveFile(String tPath, String name, String text) {
        try {
            File file = new File(tPath);
            file.mkdirs();
            FileWriter fw = new FileWriter(tPath + name, false);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

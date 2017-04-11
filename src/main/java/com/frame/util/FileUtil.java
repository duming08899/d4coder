package com.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 文件操作
 *
 * @author duming
 */
public class FileUtil {

    public static void del(String path) {
        File file = new File(path);
        System.out.println(file.delete());

    }

    public static String getAbsPath() {
        String path = StringUtil.class.getClassLoader().getResource("").getPath();
        return path;
    }

    public static InputStream getStream(String path) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }
}

package com.frame.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * 数据和模板合并
 *
 * @author duming
 */
public class MergeUtil {
    public static void outputCode(VelocityContext context, String templateFile, String path, String fileName) throws Exception {
        File file = new File(path);
        file.mkdirs();

        Template template = Velocity.getTemplate(templateFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + fileName), "UTF-8"));
        if (template != null) {
            template.merge(context, writer);
        }
        writer.flush();
        writer.close();
        System.out.println(path + fileName);
    }
}

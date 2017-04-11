package com.frame.util;

import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * 代码生成
 *
 * @author duming
 */
public class CodeFactory {

    private static VelocityContext context = new VelocityContext();

    /**
     * 根据模板生成 action service dao add.htm
     *
     * @param pb
     * @throws Exception
     */
    public static void production(ParameterBean pb) throws Exception {

        //velocity 初始化
        Properties props = new Properties();
        props.put("input.encoding", "UTF-8");
        props.put("output.encoding", "UTF-8");
        props.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, FileUtil.getAbsPath());
        Velocity.init(props);

        //用于类命名 ClassName
        String className = StringUtil.toUpperCase(pb.getTableName());
        //用于实例化出来的对象 className
        String variableName = StringUtil.toVariableName(pb.getTableName());

        //类变量赋值
        context.put("packageName", pb.getPackageRoot() + "." + pb.getPackageSys());
        context.put("className", className);
        context.put("variableName", variableName);


        // 根据实体类遍历方法
        DatabaseUtil.init(pb.getIp(), pb.getUser(), pb.getPwd(), pb.getDb(), pb.getPort());
        List<String> fields = DatabaseUtil.execute(pb.getTableName());
        context.put("fields", fields);

        // CODE
        String tempStyle = "/vm" + pb.getTempStyle();
        String codePackPath = pb.getRepoCodePath() + StringUtil.toPkgPath(pb.getPackageRoot() + "." + pb.getPackageSys());
        String repoViewPath = pb.getRepoViewPath();
        MergeUtil.outputCode(context, tempStyle + "/code/dao.vm", codePackPath + "/dao/", className + "Mapper.java");
        MergeUtil.outputCode(context, tempStyle + "/code/service.vm", codePackPath + "/service/", "I" + className + "Service.java");
        MergeUtil.outputCode(context, tempStyle + "/code/serviceImpl.vm", codePackPath + "/service/impl/", className + "ServiceImpl.java");
        MergeUtil.outputCode(context, tempStyle + "/code/action.vm", codePackPath + "/action/", className + "Controller.java");
        // WEB
        if (pb.getCreateWeb()) {
            MergeUtil.outputCode(context, tempStyle + "/page/list.vm", repoViewPath, pb.getTableName() + "_list.vm");
            MergeUtil.outputCode(context, tempStyle + "/page/add.vm", repoViewPath, pb.getTableName() + "_add.vm");
            MergeUtil.outputCode(context, tempStyle + "/page/modify.vm", repoViewPath, pb.getTableName() + "_modify.vm");
        }
        if (pb.getCreateMybatis()) {
            // mapper
            XmlUtil.reloadMapperXml(codePackPath + "/dao/", pb.getRepoResourcesPath() + "/mapper/", className + "Mapper.xml");
        }
    }

}

package com.frame;

import java.io.File;

import com.frame.util.*;
import com.frame.util.ParameterBean;

public class D4Coder {

    public static void main(String[] args) throws Exception {

        ParameterBean pb = ParameterBean.getInstance();

        if (pb.getCleanDir()) {
            // 清除历史项目
            DeleteDirectory.deleteDir(new File(pb.getRepoCodePath()));
        }
        if (pb.getCreateMybatis()) {
            //生成Mybatis相关文件
            XmlUtil.reloadMBGXml(pb);
            MyBatisGeneratorUtil mbn = new MyBatisGeneratorUtil();
            mbn.generate(pb.getRepoCodePath());
        }
        //生成代码
        CodeFactory.production(pb);
    }
}

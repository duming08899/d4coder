package com.frame.util;

import org.apache.commons.lang.StringUtils;

/**
 * 参数对象
 *
 * @author Administrator
 */
public class ParameterBean {

    //数据库IP
    private static final String DB_IP = getKey("db_ip");
    private static final String DB_USER = getKey("db_user");
    private static final String DB_PWD = getKey("db_pwd");
    private static final String DB_NAME = getKey("db_name");
    private static final Integer DB_PORT = Integer.parseInt(getKey("db_port"));
    private static final String DB_TABLE = getKey("db_table");

    //包名
    private static final String PACKAGE_ROOT = getKey("package_root");
    private static final String PACKAGE_SYS = getKey("package_sys");

    //新代码存储路径
    private static final String REPO_CODE = getKey("repo_code");
    private static final String REPO_RES = getKey("repo_res");
    private static final String REPO_VIEW = getKey("repo_view");

    //开关
    private static final Boolean IS_VIEWS = Boolean.valueOf(getKey("switch_views"));
    private static final Boolean IS_CLEAN_DIR = Boolean.valueOf(getKey("switch_clean"));
    private static final Boolean IS_MYBATIS = Boolean.valueOf(getKey("switch_mybatis"));

    //选择哪套模板
    private static final String TEMP_STYLE=getKey("sys_temp_style");

    //默认存储
    private static final Boolean SYS_SRC = Boolean.valueOf(getKey("sys_src"));


    private static ParameterBean pb = null;


    public static ParameterBean getInstance() {
        pb = new ParameterBean();
        // 数据库
        pb.setIp(DB_IP);
        pb.setUser(DB_USER);
        pb.setPwd(DB_PWD);
        pb.setDb(DB_NAME);
        pb.setPort(DB_PORT);
        pb.setTableName(DB_TABLE);

        // 包名
        pb.setPackageRoot(PACKAGE_ROOT);
        pb.setPackageSys(PACKAGE_SYS);

        // 如果不设置 获取默认目录
        pb.setRepoCodePath(FileUtil.getAbsPath() + REPO_CODE);
        pb.setRepoResourcesPath(FileUtil.getAbsPath() + REPO_RES);
        pb.setRepoViewPath(FileUtil.getAbsPath() + REPO_VIEW);

        pb.setTempStyle(TEMP_STYLE);

        // 是否删除目录重新生成
        pb.setCleanDir(IS_CLEAN_DIR);
        // 是否需要生成web页面
        pb.setCreateWeb(IS_VIEWS);
        //是否创建mybatis
        pb.setCreateMybatis(IS_MYBATIS);
        return pb;
    }

    private static String getKey(String key) {
        return SystemProperties.getInstance().getProperty(key);
    }

    private ParameterBean() {
    }


    // 数据库
    private String ip;
    private String user;
    private String pwd;
    private String db;
    private Integer port;
    private String tableName;

    //包名
    private String packageRoot;
    private String packageSys;

    // 生成目录
    private String repoCodePath;
    private String repoViewPath;
    private String repoResourcesPath;

    //模板根目录
    private String tempStyle;

    // 是否生成页面
    private Boolean createWeb;

    //清除target目录
    private Boolean cleanDir;

    private Boolean createMybatis;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPackageRoot() {
        return packageRoot;
    }

    public void setPackageRoot(String packageRoot) {
        this.packageRoot = packageRoot;
    }

    public String getPackageSys() {
        return packageSys;
    }

    public void setPackageSys(String packageSys) {
        this.packageSys = packageSys;
    }

    public String getRepoCodePath() {
        return repoCodePath;
    }

    public void setRepoCodePath(String repoCodePath) {
        this.repoCodePath = repoCodePath;
    }

    public String getRepoViewPath() {
        return repoViewPath;
    }

    public void setRepoViewPath(String repoViewPath) {
        this.repoViewPath = repoViewPath;
    }

    public String getRepoResourcesPath() {
        return repoResourcesPath;
    }

    public void setRepoResourcesPath(String repoResourcesPath) {
        this.repoResourcesPath = repoResourcesPath;
    }

    public Boolean getCreateWeb() {
        return createWeb;
    }

    public void setCreateWeb(Boolean createWeb) {
        this.createWeb = createWeb;
    }

    public Boolean getCleanDir() {
        return cleanDir;
    }

    public void setCleanDir(Boolean cleanDir) {
        this.cleanDir = cleanDir;
    }

    public String getTempStyle() {
        return tempStyle;
    }

    public void setTempStyle(String tempStyle) {
        this.tempStyle = tempStyle;
    }

    public Boolean getCreateMybatis() {
        return createMybatis;
    }

    public void setCreateMybatis(Boolean createMybatis) {
        this.createMybatis = createMybatis;
    }
}

package com.sixsixsix516.backup.mysql;

import lombok.Data;

/**
 * Mysql备份需要用到的数据
 *
 * @author sun 2020/10/27 12:14
 */
@Data
public class MysqlProperties {

    // ===================  数据库连接需要的参数 =====================

    public static String host;
    public static String port;
    public static String username;
    public static String password;
    /**
     * 需要备份的数据库
     */
    public static String db;


    // ===================  备份文件需要的参数 =====================
    /**
     * 备份文件的后缀
     */
    public static String suffix;
    /**
     * 备份文件存储的位置
     */
    public static String filePath;

    /**
     * 当前操作系统 是否为 windows true是windows /  false为linux
     */
    public static boolean windows;

    /**
     * mysql版本
     */
    public static String version;

    // ===================  发送邮箱需要的参数 =====================

    /**
     * 发送邮箱的标题
     */
    public static String title;
    /**
     * 发送邮箱地址
     */
    public static String sendToEmail;

    public static String print() {
        return "MysqlProperties{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", db='" + db + '\'' +
                ", suffix='" + suffix + '\'' +
                ", filePath='" + filePath + '\'' +
                ", windows='" + windows + '\'' +
                ", version='" + version + '\'' +
                ", title='" + title + '\'' +
                ", sendToEmail='" + sendToEmail + '\'' +
                '}';
    }


}

package com.sixsixsix516.config;

import com.sixsixsix516.backup.mysql.MysqlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * 备份环境
 *
 * @author SUN
 * @date 2021/6/2
 */
@Slf4j
@Component("backUpEnvironment")
public class Environment {

    @Autowired
    private ApplicationArguments applicationArguments;

    /**
     * 准备环境: 将命令行输入的参数 绑定到 备份配置文件中
     * 现在先写死全部绑定到Mysql, 因为只支持Mysql, 后续扩展增加了新的数据库可以动态绑定配置
     */
    public void prepare() {

        // 1.设置字段默认值
        setMysqlDefaultValue();

        // 2. 将命令行的参数 绑定到 MysqlProperties对象中
        Field[] mysqlFields = MysqlProperties.class.getDeclaredFields();
        Set<String> optionNames = applicationArguments.getOptionNames();
        optionNames.forEach(key -> {
            List<String> values = applicationArguments.getOptionValues(key);
            log.info("获取到参数: k:{}, v:{}",key,values);
            if (values.size() > 0) {
                for (Field mysqlField : mysqlFields) {
                    if (mysqlField.getName().equals(key)) {
                        try {
                            mysqlField.set(null, values.get(0));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("赋值失败");
                        }
                    }
                }
            }
        });
        log.info("接受到输入参数: {}", MysqlProperties.print());

        // 3.校验参数
        checkParam();

        // 4.检查数据库链接并设置数据库版本号
        checkConnectionAndSetVersion();
    }

    /**
     * 设置字段默认值
     */
    private void setMysqlDefaultValue() {
        MysqlProperties.host = "127.0.0.1";
        MysqlProperties.port = "3306";
        MysqlProperties.username = "root";
        MysqlProperties.password = "root";

        MysqlProperties.suffix = ".sql";
        MysqlProperties.filePath = new ApplicationHome(MysqlProperties.class).getSource().getParentFile().toString() + File.separator;
        MysqlProperties.windows = System.getProperty("os.name").startsWith("Windows");
        MysqlProperties.title = "备份";
    }

    /**
     * 参数校验
     */
    private void checkParam() {
        log.info("开始检查输入参数是否正确");
        if (StringUtils.isEmpty(MysqlProperties.db)) {
            throw new RuntimeException("db 字段为空, 请使用--db=xx 来指定");
        }
        if (StringUtils.isEmpty(MysqlProperties.sendToEmail)) {
            throw new RuntimeException("sendToEmail 字段为空, 请使用--sendToEmail=xx 来指定");
        }
        log.info("检查通过");
    }

    /**
     * 检查链接并返回数据库版本号
     */
    public void checkConnectionAndSetVersion() {
        String url = "jdbc:mysql://" + MysqlProperties.host + ":" + MysqlProperties.port + "/" + MysqlProperties.db + "?useSSL=false&characterEncoding=utf-8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull&";
        try (Connection conn = DriverManager.getConnection(url, MysqlProperties.username, MysqlProperties.password)) {
            Class.forName("com.mysql.jdbc.Driver");
            DatabaseMetaData metaData = conn.getMetaData();
            MysqlProperties.version = metaData.getDatabaseProductVersion();
            log.info("获取到数据库版本号: {}",MysqlProperties.version);
        } catch (Exception e) {
            throw new RuntimeException("数据库无法连接, 请检查连接参数: " + url);
        }
    }

}

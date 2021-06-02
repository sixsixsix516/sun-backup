package com.sixsixsix516.backup.mysql;

import com.sixsixsix516.backup.Backup;
import com.sixsixsix516.utils.FileUtil;
import com.sixsixsix516.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;


/**
 * MySQL 备份类
 *
 * @author sun 2020/10/27 11:55
 */
@Component
@Slf4j
public class MysqlBackup implements Backup {

    @Autowired
    private MailUtil mailUtil;

    /**
     * MySQL备份命令
     */
    private String MYSQL_BACKUP_BASH = "mysqldump %s --host=%s --port=%s  -u%s  -p%s  %s>%s";

    /**
     * 执行备份
     */
    @Override
    public void start() {
        boolean isWindows = MysqlProperties.windows;
        // 备份命令文件 路径
        String backBashPath = MysqlProperties.filePath + MysqlProperties.db + "-back" + (isWindows ? ".cmd" : ".sh");
        // 备份的SQL文件路径
        String backSqlPath = MysqlProperties.filePath + LocalDate.now() + MysqlProperties.db + MysqlProperties.suffix;

        // 需要执行的最终备份命令内容
        String backupBashContent = String.format(MYSQL_BACKUP_BASH,
                MysqlProperties.version.startsWith("5") ? "" : "--column-statistics=0",
                MysqlProperties.host,
                MysqlProperties.port,
                MysqlProperties.username,
                "\"" + MysqlProperties.password + "\"",
                MysqlProperties.db,
                backSqlPath);

        // 创建文件
        boolean isSuccess = FileUtil.createBashFile(isWindows, backBashPath, backupBashContent);
        if (!isSuccess) {
            throw new RuntimeException("备份命令文件创建失败!");
        }

        try {
            if (!isWindows) {
                // 允许sh文件直接执行
                log.info("检测到linux环境, 为备份命令文件增加可执行权限");
                Runtime.getRuntime().exec("chmod +x " + backBashPath);
            }
            // 执行备份文件
            log.info("开始备份");
            Runtime.getRuntime().exec(backBashPath);
            log.info("备份成功: {}", backSqlPath);
        } catch (IOException e) {
            throw new RuntimeException("备份命令执行失败! " + e.getMessage());
        }

        if (!StringUtils.isEmpty(MysqlProperties.sendToEmail)) {
            // 发送邮箱
            mailUtil.sendBackFile(backSqlPath);
        }
    }
}

package com.sixsixsix516.backup;

import com.sixsixsix516.backup.mysql.MysqlProperties;
import com.sixsixsix516.utils.FileUtil;

import java.io.IOException;
import java.time.LocalDate;


/**
 * @author sun 2020/10/27 11:55
 */
public class MysqlBackup implements Backup {

	/**
	 * MySQL备份命令
	 */
	private String MYSQL_BACKUP_BASH = "mysqldump %s --host=%s --port=%s  -u%s  -p%s  %s>%s";

	@Override
	public String backup() {
		// nohup java -jar sun-backup.jar  --password='!!!@JxhJXH2020!' --db=jxh --title=匠乡禾备份 --sendToEmail=sixsixsix517@163.com &
		boolean isWindows = MysqlProperties.os.startsWith("Windows");
		String bashSuffix = isWindows ? ".cmd" : ".sh";
		// 备份文件的路径
		String backBashPath = MysqlProperties.filePath + MysqlProperties.db + "-back" + bashSuffix;
		// 备份的SQL文件
		String backSqlPath = MysqlProperties.filePath + LocalDate.now() + MysqlProperties.db + MysqlProperties.suffix;

		// 需要执行的最终备份命令内容
		String backupBashContent = String.format(MYSQL_BACKUP_BASH, "5".equals(MysqlProperties.version) ? "" : "--column-statistics=0", MysqlProperties.host, MysqlProperties.port, MysqlProperties.username,
				isWindows ? MysqlProperties.password : "'" + MysqlProperties.password + "'",
				MysqlProperties.db, backSqlPath);

		// 创建文件
		boolean isSuccess = FileUtil.createBashFile(isWindows, backBashPath, backupBashContent);
		if (!isSuccess) {
			throw new RuntimeException("备份命令文件创建失败!");
		}

		try {
			if (!isWindows) {
				// 允许sh文件直接执行
				Runtime.getRuntime().exec("chmod +x " + backBashPath);
			}
			// 执行备份文件
			Runtime.getRuntime().exec(backBashPath);
		} catch (IOException e) {
			throw new RuntimeException("备份命令执行失败! " + e.getMessage());
		}
		return backSqlPath;
	}
}

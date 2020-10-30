package com.sixsixsix516;

import com.sixsixsix516.backup.mysql.MysqlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.util.List;

/**
 * @author sunlin
 */
@EnableScheduling
@SpringBootApplication
public class SunBackupApplication implements CommandLineRunner {

	@Autowired
	private Back back;
	@Autowired
	private ApplicationArguments applicationArguments;

	public static void main(String[] args) {
		SpringApplication.run(SunBackupApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// 解析参数

		List<String> host = applicationArguments.getOptionValues("host");

		if (host != null && host.size() > 0) {
			MysqlProperties.host = host.get(0);
		} else {
			MysqlProperties.host = "127.0.0.1";
		}

		List<String> port = applicationArguments.getOptionValues("port");
		if (port != null && port.size() > 0) {
			MysqlProperties.port = port.get(0);
		} else {
			MysqlProperties.port = "3306";
		}

		List<String> username = applicationArguments.getOptionValues("username");
		if (username != null && username.size() > 0) {
			MysqlProperties.username = username.get(0);
		} else {
			MysqlProperties.username = "root";
		}

		List<String> password = applicationArguments.getOptionValues("password");
		if (password != null && password.size() > 0) {
			MysqlProperties.password = password.get(0);
		} else {
			MysqlProperties.password = "root";
		}

		List<String> db = applicationArguments.getOptionValues("db");
		if (db == null || db.size() == 0) {
			throw new RuntimeException("db 参数未设置");
		}
		MysqlProperties.db = db.get(0);


		MysqlProperties.suffix = ".sql";

		ApplicationHome ah = new ApplicationHome(getClass());
		File file = ah.getSource();
		MysqlProperties.filePath = file.getParentFile().toString() + File.separator;

		List<String> title = applicationArguments.getOptionValues("title");
		if (title != null && title.size() > 0) {
			MysqlProperties.title = title.get(0);
		} else {
			MysqlProperties.title = "备份";
		}

		List<String> sendToEmail = applicationArguments.getOptionValues("sendToEmail");
		if (sendToEmail != null && sendToEmail.size() > 0) {
			MysqlProperties.sendToEmail = sendToEmail.get(0);
		} else {
			throw new RuntimeException("sendToEmail 参数未设置");
		}

		// 当前操作系统
		MysqlProperties.os = System.getProperty("os.name");

		List<String> version = applicationArguments.getOptionValues("version");
		if (version != null && version.size() > 0) {
			MysqlProperties.version = version.get(0);
		} else {
			throw new RuntimeException("sendToEmail 参数未设置");
		}

		// 启动后立即备份一次
		back.back();
	}

}

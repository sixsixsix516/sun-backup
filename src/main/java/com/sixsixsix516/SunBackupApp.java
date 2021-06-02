package com.sixsixsix516;

import com.sixsixsix516.backup.Backup;
import com.sixsixsix516.config.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author SUN
 */
@EnableScheduling
@SpringBootApplication
public class SunBackupApp implements CommandLineRunner {

	@Autowired
	private Backup backup;

	@Autowired
	private Environment backUpEnvironment;

	public static void main(String[] args) {
		SpringApplication.run(SunBackupApp.class, args);
	}

	// 测试: --port=12631 --password=123456 --db=superman --title=新版超人备份 --sendToEmail=sixsixsix@163.com
	@Override
	public void run(String... args) {
		// 解析参数, 准备环境
		backUpEnvironment.prepare();
		// 启动后立即备份一次(来验证程序是否正常运行)
		backup.start();
	}

}

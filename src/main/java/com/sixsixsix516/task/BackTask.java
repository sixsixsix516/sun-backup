package com.sixsixsix516.task;

import com.sixsixsix516.backup.Backup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时备份
 *
 * @author sun 2020/10/27 12:49
 */
@Slf4j
@Component
public class BackTask {

	@Autowired
	private Backup backup;

	/**
	 * 定时备份
	 */
	@Scheduled(cron = "1 0 0 * * ?")
	public void autoBackup() {
		log.info("------------------自动备份开始------------------------");
		backup.start();
		log.info("------------------自动备份结束------------------------");
	}
}

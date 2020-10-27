package com.sixsixsix516.task;

import com.sixsixsix516.Back;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author sun 2020/10/27 12:49
 */
@Component
public class BackTask {

	@Autowired
	private Back back;

	/**
	 * 定时备份
	 */
	@Scheduled(cron = "1 0 0 * * ?")
	public void autoBackup() {
		System.out.println("------------------自动备份开始------------------------");
		back.back();
		System.out.println("------------------自动备份结束------------------------");
	}
}

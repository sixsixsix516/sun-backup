package com.sixsixsix516;

import com.sixsixsix516.backup.Backup;
import com.sixsixsix516.backup.MysqlBackup;
import com.sixsixsix516.backup.mysql.MysqlProperties;
import com.sixsixsix516.mail.MyMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sun 2020/10/27 12:50
 */
@Component
public class Back {

	@Autowired
	private MyMail myMail;

	/**
	 * 备份一次
	 */
	public void back() {
		Backup backup = new MysqlBackup();
		// 备份一次
		String sqlPath = backup.backup();
		// 发送邮箱
		myMail.sendBackFile(sqlPath);
	}


}

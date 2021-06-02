package com.sixsixsix516.backup;

/**
 * 备份接口
 * 方便拓展 支持多种数据库备份
 *
 * @author sun 2020/10/27 11:56
 */
public interface Backup {

	/**
	 * 开始运行备份
	 */
	void start();
}

package com.sixsixsix516.backup.mysql;

/**
 * @author sun 2020/10/27 12:14
 */
public class MysqlProperties {

	// ===================  数据库连接需要的参数 =====================
	public static String host;
	public static String port;
	public static String username;
	public static String password;
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
	 * 当前操作系统
	 */
	public static String os;

	/**
	 * mysql版本
	 */
	public static String version;

	// ===================  发送邮箱需要的参数 =====================

	public static String title;
	public static String sendToEmail;



}

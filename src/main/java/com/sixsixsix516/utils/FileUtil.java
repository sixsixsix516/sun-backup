package com.sixsixsix516.utils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件工具类
 *
 * @author sun 2020/10/27 12:37
 */
public class FileUtil {

	private FileUtil() {
	}

	/**
	 * 向指定路径创建一个指定内容的bash文件
	 *
	 * @param path    创建的文件路径
	 * @param content 文件内容
	 * @return 是否创建成功 true成功 false失败
	 */
	public static boolean createBashFile(boolean isWindows, String path, String content) {
		try (FileWriter fileWriter = new FileWriter(path)) {
			if (!isWindows) {
				fileWriter.append("#!/bin/sh \n");
			}
			fileWriter.append(content);
			fileWriter.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}

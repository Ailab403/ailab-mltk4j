package org.mltk.mahout.load;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author superhy
 * 
 */
public class FileDocumentIOManagement {

	/**
	 * 通过文件地址获取文件名
	 * 
	 * @param textFilePath
	 * @return
	 */
	public static String getTextFileName(String textFilePath) {
		File file = new File(textFilePath);

		return file.getName();
	}

	/**
	 * 返回一个文件夹下所有文件的文件名
	 * 
	 * @param folderPath
	 * @return
	 */
	public static List<String> getTextFileNameInFolder(String folderPath) {
		// 准备返回的数据
		List<String> fileNameList = new ArrayList<String>();

		File folder = new File(folderPath);

		// 如果路径是文件，返回空值，发生错误
		if (folder.isFile()) {
			return null;
		}

		for (File file : folder.listFiles()) {
			fileNameList.add(file.getName());
		}

		return fileNameList;
	}

	/**
	 * 复制单个文件到另一个文件夹
	 * 
	 * @param sourceFilePath
	 * @param targetFolderPath
	 * @return
	 */
	public static boolean transFileToOtherFolder(String sourceFilePath,
			String targetFolderPath) {

		FileInputStream input;
		FileOutputStream output;

		try {
			input = new FileInputStream(sourceFilePath);

			String pathSeparator = File.separator;
			if (sourceFilePath.contains("/")) {
				pathSeparator = "/";
			} else if (sourceFilePath.contains("\\")) {
				pathSeparator = "\\";
			} else if (sourceFilePath.contains(File.separator)) {
				pathSeparator = File.separator;
			}

			String newFilePath = targetFolderPath
					+ "\\"
					+ sourceFilePath.substring(sourceFilePath
							.lastIndexOf(pathSeparator)
							+ pathSeparator.length());
			File file = new File(newFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			output = new FileOutputStream(newFilePath);
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			return false;
		}
	}

	/**
	 * 删除某条路径下的单独的一个文件
	 * 
	 * @param filePath
	 */
	public static boolean delFile(String filePath) {
		try {
			File file = new File(filePath);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
			} else {
				System.err.println("路径指向的文件可能为空或者路径不是指向一个文件，请检查！");

				return false;
			}

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}

	/**
	 * 删除文件夹 param（慎用）; folderPath 文件夹完整绝对路径
	 * 
	 * @param folderPath
	 */
	public static boolean delFolder(String folderPath) {
		try {
			delAllFileInFolder(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();

			File myFilePath = new File(filePath);

			myFilePath.delete(); // 删除空文件夹

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	/**
	 * 删除指定文件夹下所有文件 param; path 文件夹完整绝对路径
	 * 
	 * @param folderPath
	 * @return
	 */
	public static boolean delAllFileInFolder(String folderPath) {

		// 检查标记文件是否存在
		boolean flag = false;

		File file = new File(folderPath);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}

		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (folderPath.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFileInFolder(folderPath + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(folderPath + "/" + tempList[i]);// 再删除空文件夹

				// 删除成功
				flag = true;
			}
		}
		return flag;
	}
}

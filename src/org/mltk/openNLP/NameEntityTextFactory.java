package org.mltk.openNLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class NameEntityTextFactory {

	/**
	 * 载入单个文件
	 * 
	 * @param corpusFile
	 * @return
	 * @throws Exception
	 */
	public synchronized static String loadFileText(File corpusFile)
			throws Exception {
		String corpusStr = "";

		BufferedReader br = new BufferedReader(new FileReader(corpusFile));
		String line = null;
		while ((line = br.readLine()) != null) {
			corpusStr += line;
		}

		br.close();

		return corpusStr;
	}

	/**
	 * 载入全部文件
	 * 
	 * @param fileDirPath
	 *            文件或文件夹位置
	 * @return
	 * @throws Exception
	 */
	public synchronized static String loadFileTextDir(String fileDirPath)
			throws Exception {
		String allCorpusStr = "";

		File corpusFileDir = new File(fileDirPath);

		if (!corpusFileDir.exists()) {
			System.err.println("不存在那个文件或文件夹");
			return null;
		}

		if (corpusFileDir.isFile()) {
			allCorpusStr += loadFileText(corpusFileDir);
		} else {
			for (File eachFile : corpusFileDir.listFiles()) {
				allCorpusStr += loadFileText(eachFile);
			}
		}

		return allCorpusStr;
	}

	/**
	 * 载入词库中的命名实体
	 * 
	 * @param nameListFile
	 * @return
	 * @throws Exception
	 */
	public static List<String> loadNameWords(File nameListFile)
			throws Exception {
		List<String> nameWords = new ArrayList<String>();

		if (!nameListFile.exists() || nameListFile.isDirectory()) {
			System.err.println("不存在那个文件");
			return null;
		}

		BufferedReader br = new BufferedReader(new FileReader(nameListFile));
		String line = null;
		while ((line = br.readLine()) != null) {
			nameWords.add(line);
		}

		br.close();

		return nameWords;
	}

	/**
	 * 获取命名实体类型
	 * 
	 * @param nameListFile
	 * @return
	 */
	public static String getNameType(File nameListFile) {
		String nameType = nameListFile.getName();

		return nameType.substring(0, nameType.lastIndexOf(".") - 1);
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 * @param writeStr
	 * @throws Exception
	 */
	public static void writeIntoFile(String filePath, String writeStr)
			throws Exception {
		File file = new File(filePath);
		FileWriter fw = new FileWriter(file);

		fw.write(writeStr);

		fw.close();
	}

	/**
	 * 生成训练带标注语料
	 * 
	 * @param nameListFilePath
	 *            命名实体词库文件路径
	 * @param corpusfileDirPath
	 *            原始语料路径
	 * @param trainDataPath
	 *            带标注训练语料路径
	 * @throws Exception
	 */
	public static String prodNameFindTrainText(String nameListFilePath,
			String corpusfileDirPath, String trainDataPath) throws Exception {
		// 命名实体词库列表
		List<String> nameWordsList = new ArrayList<String>();
		// 待标注语料
		String tagCorpusStr = null;

		tagCorpusStr = loadFileTextDir(corpusfileDirPath);

		File nameListFile = new File(nameListFilePath);
		if (!nameListFile.exists()) {
			System.err.println("载入词库信息失败！");
			return null;
		} else if (nameListFile.isFile()) {
			// 只有一种类型的词库情况
			nameWordsList = loadNameWords(nameListFile);
			String nameType = getNameType(nameListFile);
			for (String nameWord : nameWordsList) {
				String replacement = "<START:" + nameType + ">" + nameWord
						+ "<END>";
				tagCorpusStr = tagCorpusStr.replaceAll(nameWord, replacement);
			}
		} else {
			// 有多种类型的词库情况
			for (File eachNameFile : nameListFile.listFiles()) {

				// TODO 下面这个部分需要抽象化处理

				nameWordsList = loadNameWords(eachNameFile);
				String nameType = getNameType(eachNameFile);
				for (String nameWord : nameWordsList) {
					String replacement = "<START:" + nameType + ">" + nameWord
							+ "<END>";
					tagCorpusStr = tagCorpusStr.replaceAll(nameWord,
							replacement);
				}
			}
		}

		// 重新写入文件
		if (tagCorpusStr != null) {
			writeIntoFile(trainDataPath, tagCorpusStr);
		}

		return tagCorpusStr;
	}

}

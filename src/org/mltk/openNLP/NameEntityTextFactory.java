package org.mltk.openNLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NameEntityTextFactory {

	public synchronized static String loadFileText(File corpusFile) throws Exception {
		String corpusStr = "";

		BufferedReader br = new BufferedReader(new FileReader(corpusFile));
		String line = null;
		while ((line = br.readLine()) != null) {
			corpusStr += line;
		}

		return corpusStr;
	}
	
	public synchronized static String loadFileTextDir(String fileDirPath) {
		String allCorpusStr = "";
		
		File corpusFile = new File(fileDirPath);
		
		if (!corpusFile.exists()) {
			System.err.println("不存在那个文件或文件夹");
			return null;
		}
		
		if (corpusFile.isFile()) {
			
		} else {

		}
		
		return null;
	}
}

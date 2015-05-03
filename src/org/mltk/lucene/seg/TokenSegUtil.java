package org.mltk.lucene.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 分词基础方法
 * 
 * @author superhy
 *
 */
public abstract class TokenSegUtil {

	/**
	 * 组合分词结果，以空格隔开
	 * 
	 * @param oriStr
	 * @param a
	 * @return
	 */
	public synchronized static String tokenStrSeg(String oriStr, Analyzer a) {
		try {
			TokenStream stream = a.tokenStream("content", new StringReader(
					oriStr));
			// 创建一个属性，这个属性会添加流中，随着这个TokenStream增加
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			String tokenStr = "";
			while (stream.incrementToken()) {
				System.out.print("[" + cta + "]");
				tokenStr += (cta + " ");
			}
			System.out.println();

			return tokenStr;
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * 对一个文件进行分词，生成分词后的文件，以空格隔开
	 * 
	 * @param oriFile
	 * @param a
	 * @param tagFilePath
	 * @return
	 */
	public synchronized static Boolean tokenFileSeg(File oriFile, Analyzer a,
			String tagFilePath) {

		try {
			File tagFile = new File(tagFilePath);
			if (!tagFile.exists()) {
				tagFile.createNewFile();
			}

			// 读取文件并分词
			BufferedReader br = new BufferedReader(new FileReader(oriFile));
			String line;
			String tokenText = "";
			while ((line = br.readLine()) != null) {
				tokenText += tokenStrSeg(line, a);
			}
			br.close();

			System.out.println("seg finish:" + tagFile.getPath());

			// 写入目标文件
			FileWriter fw = new FileWriter(tagFile);
			fw.write(tokenText);
			fw.close();

			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}

	}

}

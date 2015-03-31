package org.mltk.crawler.jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author superhy
 *
 */
public class WriteContentIntoFile {

	public boolean writeContent(String filePath, String content) {

		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			fw.write(content);

			fw.close();

			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}
}

package org.mltk.mahout.util;

import org.apache.mahout.cf.taste.model.DataModel;
import org.mltk.mahout.load.impl.LoadFileTasteDataImpl;

public class DataModelUtil {

	/**
	 * 从磁盘文件中载入数据模型
	 * 
	 * @param flagFileModel
	 * @param filePath
	 * @return
	 */
	public static DataModel loadTasteDataFromFile(String filePath) {

		// 从磁盘文件中载入数据
		DataModel fileModel = new LoadFileTasteDataImpl(filePath)
				.loadDataFromOutRes();

		return fileModel;
	}
	
	// TODO 从内存中读取术后据模型
}

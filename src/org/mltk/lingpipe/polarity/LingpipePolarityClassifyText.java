package org.mltk.lingpipe.polarity;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliasi.classify.JointClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Files;

public class LingpipePolarityClassifyText {

	private String modelDiskPath;
	private String polarityTestFileDir;

	public LingpipePolarityClassifyText(String modelDiskPath,
			String polarityTestFileDir) {
		super();
		this.modelDiskPath = modelDiskPath;
		this.polarityTestFileDir = polarityTestFileDir;
	}

	/**
	 * 从磁盘上载入极性分类器
	 * 
	 * @return
	 */
	public LMClassifier loadPolarityClassifier() {
		try {
			File classifierFile = new File(this.modelDiskPath);

			LMClassifier polarityClassifier = (LMClassifier) AbstractExternalizable
					.readObject(classifierFile);

			return polarityClassifier;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	public List<Map<String, Object>> polarityClassifyText() {

		// 准备返回数据
		List<Map<String, Object>> polarityClassifyResMaps = new ArrayList<Map<String, Object>>();

		// TODO delete print
		System.out.println("正在载入极性分类模型，请稍等...");

		LMClassifier polarityClassifier = this.loadPolarityClassifier();
		if (polarityClassifier == null) {
			System.err.println("未能成功加载极性分类器");
			return null;
		}

		try {
			File testFileDir = new File(this.polarityTestFileDir);
			for (File testFile : testFileDir.listFiles()) {
				Map<String, Object> polarityResMap = new HashMap<String, Object>();

				String testContent = Files.readFromFile(testFile, "UTF-8");

				JointClassification classification = polarityClassifier
						.classify(testContent);

				// 得出最佳分类的结果
				String bestCategory = classification.bestCategory();
				double polarityScore = classification
						.conditionalProbability("pos")
						- classification.conditionalProbability("neg");
				// 只取小数点前两位
				DecimalFormat df = new DecimalFormat("#.00");
				polarityScore = Double.valueOf(df.format(polarityScore));

				// TODO delete print
				System.out.println("文件名：" + testFile.getName() + "->最佳分类："
						+ bestCategory + "\n分类得分：\n" + polarityScore
						+ "\n分类细节：\n" + classification.toString());

				polarityResMap.put("fileName", testFile.getName());
				polarityResMap.put("polarityScore", polarityScore);
				polarityClassifyResMaps.add(polarityResMap);
			}

			return polarityClassifyResMaps;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	public String getModelDiskPath() {
		return modelDiskPath;
	}

	public void setModelDiskPath(String modelDiskPath) {
		this.modelDiskPath = modelDiskPath;
	}

	public String getPolarityTestFileDir() {
		return polarityTestFileDir;
	}

	public void setPolarityTestFileDir(String polarityTestFileDir) {
		this.polarityTestFileDir = polarityTestFileDir;
	}

}

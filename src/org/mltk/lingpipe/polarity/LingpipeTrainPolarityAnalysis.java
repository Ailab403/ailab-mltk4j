package org.mltk.lingpipe.polarity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.Files;

public class LingpipeTrainPolarityAnalysis {

	private String trainTextDir;
	private String modelDiskPath;
	private Integer nGram;

	private String[] categories;

	public LingpipeTrainPolarityAnalysis(String trainTextDir,
			String modelDiskPath, Integer nGram) {
		super();
		this.trainTextDir = trainTextDir;
		this.modelDiskPath = modelDiskPath;
		this.nGram = nGram;
	}

	/**
	 * 按照文件系统下的子目录文件名初始化类别名称列表
	 * 
	 * @param file
	 */
	public void initCategories(File file) {
		setCategories(file.list());
	}

	/**
	 * 训练文本倾向正负极性分析模型到磁盘上
	 * 
	 * @return
	 */
	public Boolean trainPolarityAnalysisModel() {
		// 加载语料集文件夹
		File pDir = new File(this.trainTextDir);
		// 初始化类别名称列表
		this.initCategories(pDir);

		try {
			// TODO delete print
			System.out.println("开始生成分类器");

			// 建立动态Logistic回归分析分类模型
			DynamicLMClassifier<NGramProcessLM> polarityClassifier = DynamicLMClassifier
					.createNGramProcess(this.categories, this.nGram);

			// 扫描极性分类训练集训练极性分类器
			for (int i = 0; i < this.categories.length; ++i) {
				String category = this.categories[i];
				// 新建类别
				Classification classification = new Classification(category);
				File dir = new File(pDir, this.categories[i]);
				File[] trainFiles = dir.listFiles();
				for (int j = 0; j < trainFiles.length; ++j) {
					File trainFile = trainFiles[j];

					// TODO delete print
					System.out.println("正在训练：" + trainFile.getName());

					String review = Files.readFromFile(trainFile, "UTF-8");
					// 指定内容和类别
					Classified classified = new Classified(review,
							classification);
					// 训练极性分类器
					polarityClassifier.handle(classified);
				}
			}

			// TODO delete print
			System.out.println("正在将分类器写到磁盘上，请稍等...");

			// 把分类器模型写到文件上
			File modelFile = new File(this.modelDiskPath);
			// 如果目录中没有对应文件，创建之
			if (!modelFile.exists()) {
				modelFile.createNewFile();
			}
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream(modelFile));
			polarityClassifier.compileTo(os);

			os.close();

			// TODO delete print
			System.out.println("分类器生成完成");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}

		return true;
	}

	public String getTrainTextDir() {
		return trainTextDir;
	}

	public void setTrainTextDir(String trainTextDir) {
		this.trainTextDir = trainTextDir;
	}

	public String getModelDiskPath() {
		return modelDiskPath;
	}

	public void setModelDiskPath(String modelDiskPath) {
		this.modelDiskPath = modelDiskPath;
	}

	public Integer getnGram() {
		return nGram;
	}

	public void setnGram(Integer nGram) {
		this.nGram = nGram;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

}

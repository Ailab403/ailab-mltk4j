package org.mltk.openNLP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.Collections;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.featuregen.AggregatedFeatureGenerator;
import opennlp.tools.util.featuregen.PreviousMapFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

/**
 * 中文命名实体识别模型训练组件
 * 
 * @author ddlovehy
 *
 */
public class NamedEntityMultiFindTrainer {

	// 默认参数
	private int iterations = 80;
	private int cutoff = 5;
	private String langCode = "general";
	private String type = "default";

	// 待设定的参数
	private String dataPath;
	private String modelPath;

	public NamedEntityMultiFindTrainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NamedEntityMultiFindTrainer(String dataPath, String modelPath) {
		super();
		this.dataPath = dataPath;
		this.modelPath = modelPath;
	}

	public NamedEntityMultiFindTrainer(int iterations, int cutoff,
			String langCode, String type, String dataPath, String modelPath) {
		super();
		this.iterations = iterations;
		this.cutoff = cutoff;
		this.langCode = langCode;
		this.type = type;
		this.dataPath = dataPath;
		this.modelPath = modelPath;
	}

	/**
	 * 生成定制特征
	 * 
	 * @return
	 */
	public AggregatedFeatureGenerator prodFeatureGenerators() {
		AggregatedFeatureGenerator featureGenerators = new AggregatedFeatureGenerator(
				new WindowFeatureGenerator(new TokenFeatureGenerator(), 2, 2),
				new WindowFeatureGenerator(new TokenClassFeatureGenerator(), 2,
						2), new PreviousMapFeatureGenerator());

		return featureGenerators;
	}

	/**
	 * 将模型写入磁盘
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void writeModelIntoDisk(TokenNameFinderModel model) throws Exception {
		File outModelFile = new File(this.getModelPath());
		FileOutputStream outModelStream = new FileOutputStream(outModelFile);
		model.serialize(outModelStream);
	}

	/**
	 * 训练模型
	 * 
	 * @param trainDataStr
	 * @return
	 */
	public boolean trainNameEntitySamples(String trainDataStr) {
		try {
			ObjectStream<NameSample> nameEntitySample = new NameSampleDataStream(
					new PlainTextByLineStream(new StringReader(trainDataStr)));

			TokenNameFinderModel nameFinderModel = NameFinderME.train(
					this.getLangCode(), this.getType(), nameEntitySample,
					this.prodFeatureGenerators(),
					Collections.<String, Object> emptyMap(),
					this.getIterations(), this.getCutoff());

			// 得到模型写入磁盘
			this.writeModelIntoDisk(nameFinderModel);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}

	}

	/**
	 * test function part time
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public int getCutoff() {
		return cutoff;
	}

	public void setCutoff(int cutoff) {
		this.cutoff = cutoff;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

}

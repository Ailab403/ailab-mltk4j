package org.mltk.openNLP;

import opennlp.tools.util.featuregen.AggregatedFeatureGenerator;
import opennlp.tools.util.featuregen.PreviousMapFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

public class NamedEntitySingleFindTrainer {

	// 默认参数
	private int iterations = 80;
	private int cutoff = 5;
	private String langCode = "general";

	// 待设定的参数
	private String type;
	private String modelPath;

	public NamedEntitySingleFindTrainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NamedEntitySingleFindTrainer(String type, String modelPath) {
		super();
		this.type = type;
		this.modelPath = modelPath;
	}

	public NamedEntitySingleFindTrainer(int iterations, int cutoff,
			String langCode, String type, String modelPath) {
		super();
		this.iterations = iterations;
		this.cutoff = cutoff;
		this.langCode = langCode;
		this.type = type;
		this.modelPath = modelPath;
	}

	// 生成定制特征
	public AggregatedFeatureGenerator prodFeatureGenerators() {
		AggregatedFeatureGenerator featureGenerators = new AggregatedFeatureGenerator(
				new WindowFeatureGenerator(new TokenFeatureGenerator(), 2, 2),
				new WindowFeatureGenerator(new TokenClassFeatureGenerator(), 2,
						2), new PreviousMapFeatureGenerator());

		return featureGenerators;
	}

	/**
	 * TODO 未完成
	 * 
	 * @return
	 */

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

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
}

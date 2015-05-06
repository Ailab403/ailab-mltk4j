package org.mltk.task.t_mcmf.model;

import java.util.List;
import java.util.Map;

import org.mltk.lucene.model.Document;

public class LdaDoc extends Document {

	// 生成主题集
	private List<Map<LdaTopic, Double>> generateTopics;

	public LdaDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LdaDoc(String docName, List<Map<LdaTopic, Double>> generateTopics) {
		super(docName);
		this.generateTopics = generateTopics;
	}

	public LdaDoc(List<Map<LdaTopic, Double>> generateTopics) {
		super();
		this.generateTopics = generateTopics;
	}

	public List<Map<LdaTopic, Double>> getGenerateTopics() {
		return generateTopics;
	}

	public void setGenerateTopics(List<Map<LdaTopic, Double>> generateTopics) {
		this.generateTopics = generateTopics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LdaDoc [generateTopics=" + generateTopics + ", docName="
				+ docName + "]";
	}

}

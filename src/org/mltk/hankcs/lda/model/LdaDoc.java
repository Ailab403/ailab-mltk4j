package org.mltk.hankcs.lda.model;

import java.util.List;
import java.util.Map;

public class LdaDoc {

	// 生成主题集
	private List<Map<LdaTopic, Double>> generateTopics;

	public LdaDoc() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "LdaDoc [generateTopics=" + generateTopics + "]";
	}

}

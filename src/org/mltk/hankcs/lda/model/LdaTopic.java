package org.mltk.hankcs.lda.model;

import java.util.List;
import java.util.Map;

public class LdaTopic {

	// 生成词集
	private List<Map<LdaWord, Double>> generateWords;
	// 所属doc 生成概率
	private List<Map<LdaDoc, Double>> belongDocs;

	public LdaTopic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LdaTopic(List<Map<LdaWord, Double>> generateWords,
			List<Map<LdaDoc, Double>> belongDocs) {
		super();
		this.generateWords = generateWords;
		this.belongDocs = belongDocs;
	}

	public List<Map<LdaWord, Double>> getGenerateWords() {
		return generateWords;
	}

	public void setGenerateWords(List<Map<LdaWord, Double>> generateWords) {
		this.generateWords = generateWords;
	}

	public List<Map<LdaDoc, Double>> getBelongDocs() {
		return belongDocs;
	}

	public void setBelongDocs(List<Map<LdaDoc, Double>> belongDocs) {
		this.belongDocs = belongDocs;
	}

	@Override
	public String toString() {
		return "LdaTopic [generateWords=" + generateWords + ", belongDocs="
				+ belongDocs + "]";
	}

}

package org.mltk.task.t_mcmf.model;

import java.util.List;
import java.util.Map;

import org.mltk.lucene.model.Word;

public class LdaWord extends Word {

	// 所属topic 生成概率
	private List<Map<LdaTopic, Double>> belongTopics;

	public LdaWord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LdaWord(String wordText, List<Map<LdaTopic, Double>> belongTopics) {
		super(wordText);
		this.belongTopics = belongTopics;
	}

	public List<Map<LdaTopic, Double>> getBelongTopics() {
		return belongTopics;
	}

	public void setBelongTopics(List<Map<LdaTopic, Double>> belongTopics) {
		this.belongTopics = belongTopics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LdaWord [belongTopics=" + belongTopics + ", wordText="
				+ wordText + "]";
	}

}

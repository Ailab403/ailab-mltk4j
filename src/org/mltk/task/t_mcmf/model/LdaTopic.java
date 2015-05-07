package org.mltk.task.t_mcmf.model;

import java.util.HashMap;
import java.util.Map;

public class LdaTopic {

	public int topicLdaId;
	// Éú³É´Ê¼¯
	public Map<Integer, Double> generateWords = new HashMap<Integer, Double>();

	public LdaTopic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LdaTopic(int topicLdaId, Map<Integer, Double> generateWords) {
		super();
		this.topicLdaId = topicLdaId;
		this.generateWords = generateWords;
	}

	@Override
	public String toString() {
		return "LdaTopic [topicLdaId=" + topicLdaId + ", generateWords="
				+ generateWords + "]";
	}

}

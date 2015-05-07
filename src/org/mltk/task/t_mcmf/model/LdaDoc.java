package org.mltk.task.t_mcmf.model;

import java.util.HashMap;
import java.util.Map;

import org.mltk.lucene.model.Document;

public class LdaDoc extends Document {

	public int docLdaId;
	// 生成主题集
	public Map<Integer, Double> generateTopics = new HashMap<Integer, Double>();

	public LdaDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LdaDoc(String docName) {
		super(docName);
		// TODO Auto-generated constructor stub
	}

	public LdaDoc(String docName, int docLdaId,
			Map<Integer, Double> generateTopics) {
		super(docName);
		this.docLdaId = docLdaId;
		this.generateTopics = generateTopics;
	}

	@Override
	public String toString() {
		return "LdaDoc [docLdaId=" + docLdaId + ", generateTopics="
				+ generateTopics + "]";
	}

}

package org.mltk.task.t_mcmf.model;

import java.util.ArrayList;
import java.util.List;

public class LdaGraph {

	public List<LdaDoc> allDocs = new ArrayList<LdaDoc>();
	public List<LdaTopic> allTopics = new ArrayList<LdaTopic>();;

	public LdaGraph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LdaGraph(List<LdaDoc> allDocs, List<LdaTopic> allTopics) {
		super();
		this.allDocs = allDocs;
		this.allTopics = allTopics;
	}

	public List<LdaDoc> getAllDocs() {
		return allDocs;
	}

	public void setAllDocs(List<LdaDoc> allDocs) {
		this.allDocs = allDocs;
	}

	public List<LdaTopic> getAllTopics() {
		return allTopics;
	}

	public void setAllTopics(List<LdaTopic> allTopics) {
		this.allTopics = allTopics;
	}

	@Override
	public String toString() {
		return "LdaGraph [allDocs=" + allDocs + ", allTopics=" + allTopics
				+ "]";
	}

}

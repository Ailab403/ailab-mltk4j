package org.mltk.task.t_mcmf.model;

import java.util.List;

public class LdaGraph {

	List<LdaDoc> allDocs;
	List<LdaTopic> allTopics;
	List<LdaWord> allWords;

	public LdaGraph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LdaGraph(List<LdaDoc> allDocs, List<LdaTopic> allTopics,
			List<LdaWord> allWords) {
		super();
		this.allDocs = allDocs;
		this.allTopics = allTopics;
		this.allWords = allWords;
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

	public List<LdaWord> getAllWords() {
		return allWords;
	}

	public void setAllWords(List<LdaWord> allWords) {
		this.allWords = allWords;
	}

	@Override
	public String toString() {
		return "LdaGraph [allDocs=" + allDocs + ", allTopics=" + allTopics
				+ ", allWords=" + allWords + "]";
	}

}

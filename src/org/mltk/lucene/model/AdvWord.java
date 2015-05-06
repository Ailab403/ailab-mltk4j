package org.mltk.lucene.model;

public class AdvWord extends Word {

	// TODO more information

	// ДЪад
	private String wordTag;

	public AdvWord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvWord(String wordText, String wordTag) {
		super(wordText);
		this.wordTag = wordTag;
	}

	public String getWordTag() {
		return wordTag;
	}

	public void setWordTag(String wordTag) {
		this.wordTag = wordTag;
	}

	@Override
	public String toString() {
		return "AdvWord [wordTag=" + wordTag + ", wordText=" + wordText + "]";
	}

}

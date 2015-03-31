package org.mltk.lucene.model;

public class Word {

	private String wordText;
	private String wordTag;

	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Word(String wordText, String wordTag) {
		super();
		this.wordText = wordText;
		this.wordTag = wordTag;
	}

	public String getWordText() {
		return wordText;
	}

	public void setWordText(String wordText) {
		this.wordText = wordText;
	}

	public String getWordTag() {
		return wordTag;
	}

	public void setWordTag(String wordTag) {
		this.wordTag = wordTag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wordTag == null) ? 0 : wordTag.hashCode());
		result = prime * result
				+ ((wordText == null) ? 0 : wordText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (wordTag == null) {
			if (other.wordTag != null)
				return false;
		} else if (!wordTag.equals(other.wordTag))
			return false;
		if (wordText == null) {
			if (other.wordText != null)
				return false;
		} else if (!wordText.equals(other.wordText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Word [wordTag=" + wordTag + ", wordText=" + wordText + "]";
	}

}

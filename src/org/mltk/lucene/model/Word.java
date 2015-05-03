package org.mltk.lucene.model;

public class Word {

	protected String wordText;

	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Word(String wordText) {
		super();
		this.wordText = wordText;
	}

	public String getWordText() {
		return wordText;
	}

	public void setWordText(String wordText) {
		this.wordText = wordText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (wordText == null) {
			if (other.wordText != null)
				return false;
		} else if (!wordText.equals(other.wordText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Word [wordText=" + wordText + "]";
	}

}

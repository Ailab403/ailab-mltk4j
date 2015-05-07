package org.mltk.task.t_mcmf.model;

import org.mltk.lucene.model.Word;

public class LdaWord extends Word {

	// 自己的生成概率
	public int wordLdaId;

	public LdaWord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LdaWord(String wordText) {
		super(wordText);
		// TODO Auto-generated constructor stub
	}

	public LdaWord(String wordText, int wordLdaId) {
		super(wordText);
		this.wordLdaId = wordLdaId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + wordLdaId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LdaWord other = (LdaWord) obj;
		if (wordLdaId != other.wordLdaId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LdaWord [wordLdaId=" + wordLdaId + "]";
	}

}

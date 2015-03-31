package org.mltk.lucene.model;

public class IndexDoc {

	private String textName;
	private String textContent;

	public IndexDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IndexDoc(String textName, String textContent) {
		super();
		this.textName = textName;
		this.textContent = textContent;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	@Override
	public String toString() {
		return "IndexDoc [textName=" + textName + ", textContent="
				+ textContent + "]";
	}

}

package org.mltk.lucene.model;

public class Document {

	protected String docName;

	public Document() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Document(String docName) {
		super();
		this.docName = docName;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Document [docName=" + docName + "]";
	}

}

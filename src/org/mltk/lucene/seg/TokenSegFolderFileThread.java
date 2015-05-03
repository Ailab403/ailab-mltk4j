package org.mltk.lucene.seg;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.lucene.analysis.Analyzer;

public class TokenSegFolderFileThread implements Callable<Boolean> {

	private File oriFile;
	private String tagFolderPath;
	private Analyzer a;

	public TokenSegFolderFileThread(File oriFile, String tagFolderPath,
			Analyzer a) {
		super();
		this.oriFile = oriFile;
		this.tagFolderPath = tagFolderPath;
		this.a = a;
	}

	@Override
	public Boolean call() throws Exception {

		String oriFileName = oriFile.getName();
		String tagFilePath = this.tagFolderPath + File.separator + oriFileName;

		Boolean runFlag = TokenSegUtil.tokenFileSeg(oriFile, a, tagFilePath);

		return runFlag;
	}

	public File getOriFile() {
		return oriFile;
	}

	public void setOriFile(File oriFile) {
		this.oriFile = oriFile;
	}

	public String getTagFolderPath() {
		return tagFolderPath;
	}

	public void setTagFolderPath(String tagFolderPath) {
		this.tagFolderPath = tagFolderPath;
	}

	public Analyzer getA() {
		return a;
	}

	public void setA(Analyzer a) {
		this.a = a;
	}

}

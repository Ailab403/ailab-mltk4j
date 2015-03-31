package org.mltk.lucene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.Callable;

import org.apache.lucene.analysis.Analyzer;
import org.mltk.lucene.model.IndexDoc;

public class InitContentIndexThread implements Callable<Boolean> {

	// 需要传入的参数，文件，索引地址，分词器
	private File docFile;
	private String indexPath;
	private Analyzer analyzer;

	// 内部初始化的参数
	private String textName;
	private FileReader textReader;

	// 多线程调用构造函数
	public InitContentIndexThread(File docFile, String indexPath,
			Analyzer analyzer) {
		super();
		this.docFile = docFile;
		this.indexPath = indexPath;
		this.analyzer = analyzer;
	}

	// 常规构造函数
	public InitContentIndexThread(File docFile, String indexPath,
			Analyzer analyzer, String textName, FileReader textReader) {
		super();
		this.docFile = docFile;
		this.indexPath = indexPath;
		this.analyzer = analyzer;
		this.textName = textName;
		this.textReader = textReader;
	}

	public void readFileContent() {
		try {
			setTextName(docFile.getName());
			setTextReader(new FileReader(docFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Boolean call() throws Exception {

		this.readFileContent();

		try {
			IndexDoc doc = WriteDocIntoIndex.writeTextIntoIndex(this.textName,
					this.textReader, this.indexPath, this.analyzer);
			System.out.println(doc.toString());

			this.textReader.close();

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}

	public File getDocFile() {
		return docFile;
	}

	public void setDocFile(File docFile) {
		this.docFile = docFile;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public FileReader getTextReader() {
		return textReader;
	}

	public void setTextReader(FileReader textReader) {
		this.textReader = textReader;
	}

}

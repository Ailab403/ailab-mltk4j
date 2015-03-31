package org.mltk.lucene;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.mltk.lucene.model.IndexDoc;

public class WriteDocIntoIndex {

	/**
	 * 初始化索引目录
	 * 
	 * @param indexPath
	 */
	public synchronized static Directory loadDirectory(String indexPath) {

		Directory directory = null;

		try {

			// 创建索引到硬盘当中
			directory = FSDirectory.open(new File(indexPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return directory;
	}

	/**
	 * 直接传入字符串内容
	 * 
	 * @param textName
	 * @param textContent
	 * @param indexPath
	 * @param analyzer
	 * @return
	 */
	public synchronized static IndexDoc writeTextIntoIndex(String textName,
			String textContent, String indexPath, Analyzer analyzer) {

		Directory directory = loadDirectory(indexPath);

		IndexDoc indexDoc = new IndexDoc();

		IndexWriter writer = null;

		try {

			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			Document doc = new Document();

			// TODO delete print
			System.out.println("正在创建索引：" + textName);

			doc.add(new Field("textName", textName, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("textContent", textContent, Field.Store.NO,
					Field.Index.ANALYZED));

			indexDoc.setTextName(textName);
			indexDoc.setTextContent(textContent);

			writer.addDocument(doc);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 注意关闭索引写入流，否则会产生写锁
				if (writer != null) {
					writer.close();
				}
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return indexDoc;
	}

	/**
	 * 传入文件阅读器
	 * 
	 * @param textName
	 * @param fileReader
	 * @param indexPath
	 * @param analyzer
	 * @return
	 */
	public synchronized static IndexDoc writeTextIntoIndex(String textName,
			Reader fileReader, String indexPath, Analyzer analyzer) {

		Directory directory = loadDirectory(indexPath);

		IndexDoc indexDoc = new IndexDoc();

		IndexWriter writer = null;

		try {

			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			Document doc = new Document();

			// TODO delete print
			System.out.println("正在创建索引：" + textName);

			doc.add(new Field("textName", textName, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("textContent", fileReader));

			indexDoc.setTextName(textName);
			indexDoc.setTextContent("文件读取");

			writer.addDocument(doc);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 注意关闭索引写入流，否则会产生写锁
				if (writer != null) {
					writer.close();
				}
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return indexDoc;
	}
}

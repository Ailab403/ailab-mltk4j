package org.mltk.mongo.service;

import org.mltk.lucene.model.Word;
import org.mltk.mongo.MongoDbBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class WordMongoService {

	// TODO

	private String dbName;

	public WordMongoService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WordMongoService(String dbName) {
		super();
		this.dbName = dbName;
	}

	/**
	 * 插入单个词条
	 * 
	 * @param word
	 * @param collectionName
	 */
	public void insertSingleWord(Word word, String collectionName) {

		// 转换成mongo数据对象
		DBObject wordDbs = new BasicDBObject();

		// 向数据对象中添加数据
		wordDbs.put("wordText", word.getWordText());

		// 存储
		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean(this.dbName);
		mongoDbBean.insert(wordDbs, collectionName);
	}

}

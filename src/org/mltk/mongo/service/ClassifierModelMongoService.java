package org.mltk.mongo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mltk.mongo.MongoDbBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ClassifierModelMongoService {

	private String dbName;

	public ClassifierModelMongoService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassifierModelMongoService(String dbName) {
		super();
		this.dbName = dbName;
	}

	/**
	 * 创建新的集合
	 * 
	 * @param collectionName
	 */
	public void createNewColl(String collectionName) {

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean(this.dbName);
		boolean createCollRes = mongoDbBean.createNewCollection(collectionName);

		if (createCollRes == true) {
			System.out
					.println("ClassifierModelMongoServiceImpl.createNewColl()"
							+ ":创建新集合成功！");
		} else {
			System.err.println("创建新集合失败！");
		}
	}

	/**
	 * 插入单个向量实体
	 * 
	 * @param vecId
	 * @param label
	 * @param featrues
	 * @param collectionName
	 */
	public void insertSingleVector(String vecId, Integer label,
			List<String> featrues, String collectionName) {

		// 转换成mongo数据对象
		DBObject vecDbs = new BasicDBObject();

		// 创建数据对象列表，存储数组
		List<DBObject> featruesDbsList = new ArrayList<DBObject>();
		for (String featrue : featrues) {
			DBObject featrueDbs = new BasicDBObject();

			featrueDbs.put("feature", featrue);
			featruesDbsList.add(featrueDbs);
		}

		// 向数据对象中添加数据
		vecDbs.put("vecId", vecId);
		vecDbs.put("label", label);
		vecDbs.put("featrues", featruesDbsList);

		// 存储
		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean(this.dbName);
		mongoDbBean.insert(vecDbs, collectionName);
	}

	/**
	 * 查询出模型集合中全部的向量
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<Map<String, Object>> findAllVectorsInColl(String collectionName) {

		// 指定返回的向量数据
		List<Map<String, Object>> vectorsInColl = new ArrayList<Map<String, Object>>();

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean(this.dbName);
		for (DBObject vecDbs : mongoDbBean.findByKeyAndRef(null, null,
				collectionName)) {
			vectorsInColl.add(vecDbs.toMap());
		}

		return vectorsInColl;
	}

	/**
	 * 查询出模型集合中的单个向量
	 * 
	 * @param vecId
	 * @param collectionName
	 * @return
	 */
	public Map<String, Object> findSigVectorInCollByVecId(String vecId,
			String collectionName) {

		// 准备返回的向量数据
		Map<String, Object> vectorInColl = new HashMap<String, Object>();

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean(this.dbName);
		// 添加查询条件
		DBObject ref = new BasicDBObject();
		ref.put("vecId", vecId);
		List<DBObject> findResInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		vectorInColl = findResInColl.get(0).toMap();

		return vectorInColl;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}

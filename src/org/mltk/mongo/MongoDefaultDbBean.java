package org.mltk.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * MongoDB数据库基本操作类
 * 
 * @author superhy
 * 
 */
public class MongoDefaultDbBean {

	// 建立MongoDB数据库链接对象
	public static Mongo mongoConnection = null;
	// 创建MongoDB具体数据库的对象
	public static DB dbConnection = null;

	// 单例模式，静态内部类
	private static class MongoDbBeanHolder {
		private static final MongoDefaultDbBean MONGO_DB_BEAN = new MongoDefaultDbBean();
	}

	private MongoDefaultDbBean() {
		try {
			if (mongoConnection == null || dbConnection == null) {
				String connectHost = "127.0.0.1:27017";
				String dbName = "ailab-mltk";

				this.mongoConnection = new Mongo(connectHost);
				this.dbConnection = mongoConnection.getDB(dbName);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 返回类实体
	public static final MongoDefaultDbBean getMongoDbBean() {
		return MongoDbBeanHolder.MONGO_DB_BEAN;
	}

	// MongoDB的基本数据操作

	/**
	 * 创建新的数据库集合
	 */
	public boolean createNewCollection(String collectionName) {
		try {
			DBObject dbCollection = new BasicDBObject();
			this.dbConnection.createCollection(collectionName, dbCollection);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询数据库集合名称
	 * 
	 * code by:dd
	 */
	public Set<String> getCollectionNames() {
		Set<String> colls = this.dbConnection.getCollectionNames();
		return colls;
	}

	/**
	 * 向指定集合中添加数据
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	public boolean insert(DBObject dbs, String collectionName) {

		try {
			// 获得对应集合
			DBCollection collection = this.dbConnection
					.getCollection(collectionName);
			// 插入数据
			collection.insert(dbs);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向指定集合中批量添加数据
	 * 
	 * @param dbses
	 * @param collectionName
	 * @return
	 */
	public boolean batchInsert(List<DBObject> dbses, String collectionName) {
		try {
			// 得到对应集合
			DBCollection collection = this.dbConnection
					.getCollection(collectionName);
			// 批量插入数据
			collection.insert(dbses);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除指定集合中的特定对象
	 * 
	 * @param dbs
	 * @param collectionName
	 * @return
	 */
	public int deleteByDbs(DBObject dbs, String collectionName) {

		// 获得要进行操作的集合
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// 执行删除操作并返回影响的行数
		int effectNum = collection.remove(dbs).getN();

		return effectNum;
	}

	/**
	 * 查询指定集合中的记录条数
	 * 
	 * @param collectionName
	 * @return
	 */
	public int countInColl(String collectionName) {

		// 获得要进行操作的集合
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// 查询出集合中的记录数目
		int collRecCountNum = collection.find().count();

		return collRecCountNum;
	}

	/**
	 * 查询指定集合中符合条件的记录数目
	 * 
	 * @param ref
	 * @param keys
	 * @param collectionName
	 * @return
	 */
	public int countByKeyAndRefInColl(DBObject ref, DBObject keys,
			String collectionName) {
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// 查询出集合中符合条件的记录数目
		int collRecCountNum = collection.find(ref, keys).count();

		return collRecCountNum;
	}

	/**
	 * 更新数据,find查询器,update更新器,upsert更新或插入,multi是否批量更新,collName集合名称,
	 * return返回影响的数据条数
	 * 
	 * @param find
	 * @param update
	 * @param upsert
	 * @param multi
	 * @param collName
	 * @return
	 */
	public int updateValue(DBObject find, DBObject update, boolean upsert,
			boolean multi, String collName) {

		// 得到集合
		DBCollection coll = this.dbConnection.getCollection(collName);

		int count = coll.update(find, update, upsert, multi).getN();
		return count;
	}

	/**
	 * 通用find方法，传入三个参数，第一个是查询的条件，第二个是查询的字段限制，第三个是查询的集合名，返回值是DBObject的映射集合
	 * exp:不带分页的查询
	 * 
	 * @return
	 */
	public List<DBObject> findByKeyAndRef(DBObject ref, DBObject keys,
			String collectionName) {

		// 准备要返回的映射集合
		List<DBObject> dbObjects = new ArrayList<DBObject>();

		// 获得要进行操作的集合
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		// 创建查询的游标
		DBCursor cursor = collection.find(ref, keys);

		// 遍历游标获得查询结果集合
		while (cursor.hasNext()) {
			dbObjects.add(cursor.next());
		}

		return dbObjects;
	}

	/**
	 * 通用find方法，传入三个参数，第一个是查询的条件，第二个是查询的字段限制，第三个是查询的字段开始下标，
	 * 第四个是查询的字段数，第五个是查询的集合名，返回值是DBObject的映射集合 exp:带分页的查询
	 * 
	 * @return
	 */
	public List<DBObject> findByKeyAndRefWithPage(DBObject ref, DBObject keys,
			int skip, int limit, String collectionName) {

		// 准备要返回的映射集合
		List<DBObject> dbObjects = new ArrayList<DBObject>();

		// 获得要进行操作的集合
		DBCollection collection = this.dbConnection
				.getCollection(collectionName);
		DBCursor cursor = collection.find(ref, keys).skip(skip).limit(limit);

		// 遍历游标获得查询结果集合
		while (cursor.hasNext()) {
			dbObjects.add(cursor.next());
		}

		return dbObjects;
	}
}

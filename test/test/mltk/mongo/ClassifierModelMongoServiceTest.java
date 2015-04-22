package test.mltk.mongo;

import org.junit.Test;
import org.mltk.mongo.MongoDbBean;

import com.mongodb.DB;

/**
 * 
 * @author superhy
 *
 */
public class ClassifierModelMongoServiceTest {

	@Test
	public void testMongoDbBeanName() {

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean("ailab-mltk");
		DB db = mongoDbBean.dbConnection;
		String dbName = mongoDbBean.dbConnection.getName();

		System.out.println(db.getCollectionNames());
		System.out.println(dbName);
	}
}

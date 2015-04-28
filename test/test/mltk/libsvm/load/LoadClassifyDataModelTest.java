package test.mltk.libsvm.load;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mltk.mongo.service.ClassifierModelMongoService;

public class LoadClassifyDataModelTest {

	/**
	 * 模拟数据集
	 * 
	 * @return
	 */
	public void createTrainDataToMongo() {

		ClassifierModelMongoService mongoService = new ClassifierModelMongoService(
				"ailab-mltk-cache");

		final String vec1Node1 = "0.0";
		final String vec1Node2 = "0.0";
		final List<String> featrues1 = new ArrayList<String>() {
			{
				add(vec1Node1);
				add(vec1Node2);
			}
		};
		final Integer label1 = -1;
		final String vecId1 = "test01";
		mongoService.insertSingleVector(vecId1, label1, featrues1,
				"LibSVMTrainCache");

		final String vec2Node1 = "1.0";
		final String vec2Node2 = "1.0";
		final List<String> featrues2 = new ArrayList<String>() {
			{
				add(vec2Node1);
				add(vec2Node2);
			}
		};
		final Integer label2 = -1;
		final String vecId2 = "test02";
		mongoService.insertSingleVector(vecId2, label2, featrues2,
				"LibSVMTrainCache");

		final String vec3Node1 = "0.0";
		final String vec3Node2 = "1.0";
		final List<String> featrues3 = new ArrayList<String>() {
			{
				add(vec3Node1);
				add(vec3Node2);
			}
		};
		final Integer label3 = 1;
		final String vecId3 = "test03";
		mongoService.insertSingleVector(vecId3, label3, featrues3,
				"LibSVMTrainCache");

		final String vec4Node1 = "1.0";
		final String vec4Node2 = "2.0";
		final List<String> featrues4 = new ArrayList<String>() {
			{
				add(vec4Node1);
				add(vec4Node2);
			}
		};
		final Integer label4 = 1;
		final String vecId4 = "test04";
		mongoService.insertSingleVector(vecId4, label4, featrues4,
				"LibSVMTrainCache");

	}

	@Test
	public void testLoadTrainData() {

		this.createTrainDataToMongo();
		System.err.println("创建模拟数据成功！");

		/*---------------------华丽的分割线---------------------*/

	}
}

package test.mltk.libsvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.mltk.libsvm.LibSVMClassifier;
import org.mltk.libsvm.model.ClassifyRes;
import org.mltk.libsvm.model.TrainDataSet;

public class LibSVMClassifierTest {

	// 模拟数据集
	public TrainDataSet createTrainData() {

		final String[] vec1Node1 = new String[] { "1", "1.2" };
		final String[] vec1Node2 = new String[] { "2", "1.3" };
		final String[] vec1Node3 = new String[] { "3", "1.0" };
		final String[] vec1Node4 = new String[] { "4", "1.0" };
		final String[] vec1Node5 = new String[] { "5", "1.4" };
		final String[] vec1Node6 = new String[] { "6", "0.9" };
		final List<String[]> vector1 = new ArrayList<String[]>() {
			{
				add(vec1Node1);
				add(vec1Node2);
				add(vec1Node3);
				add(vec1Node4);
				add(vec1Node5);
				add(vec1Node6);
			}
		};

		final String[] vec2Node1 = new String[] { "1", "1.0" };
		final String[] vec2Node2 = new String[] { "2", "1.1" };
		final String[] vec2Node3 = new String[] { "3", "1.2" };
		final String[] vec2Node4 = new String[] { "4", "1.0" };
		final String[] vec2Node5 = new String[] { "5", "0.8" };
		final String[] vec2Node6 = new String[] { "6", "0.9" };
		final List<String[]> vector2 = new ArrayList<String[]>() {
			{
				add(vec2Node1);
				add(vec2Node2);
				add(vec2Node3);
				add(vec2Node4);
				add(vec2Node5);
				add(vec2Node6);
			}
		};

		final String[] vec3Node1 = new String[] { "1", "9.2" };
		final String[] vec3Node2 = new String[] { "2", "9.3" };
		final String[] vec3Node3 = new String[] { "3", "9.0" };
		final String[] vec3Node4 = new String[] { "4", "9.0" };
		final String[] vec3Node5 = new String[] { "5", "9.4" };
		final String[] vec3Node6 = new String[] { "6", "8.9" };
		final List<String[]> vector3 = new ArrayList<String[]>() {
			{
				add(vec3Node1);
				add(vec3Node2);
				add(vec3Node3);
				add(vec3Node4);
				add(vec3Node5);
				add(vec3Node6);
			}
		};

		final String[] vec4Node1 = new String[] { "1", "9.2" };
		final String[] vec4Node2 = new String[] { "2", "9.3" };
		final String[] vec4Node3 = new String[] { "3", "9.0" };
		final String[] vec4Node4 = new String[] { "4", "9.0" };
		final String[] vec4Node5 = new String[] { "5", "8.4" };
		final String[] vec4Node6 = new String[] { "6", "8.9" };
		final List<String[]> vector4 = new ArrayList<String[]>() {
			{
				add(vec4Node1);
				add(vec4Node2);
				add(vec4Node3);
				add(vec4Node4);
				add(vec4Node5);
				add(vec4Node6);
			}
		};

		// 注意，普通的map不允许key值重复，要使用允许key值重复的特殊map
		Map<Double, List<String[]>> trainData = new IdentityHashMap<Double, List<String[]>>() {
			{
				put(1.0, vector1);
				put(1.0, vector2);
				put(2.0, vector3);
				put(2.0, vector4);

			}
		};

		TrainDataSet trainDataSet = new TrainDataSet(trainData, 4, 6);

		return trainDataSet;
	}

	@Test
	public void testClassifierFromMemory() {

		Map<Integer, Double[]> testDataSet = new HashMap<Integer, Double[]>();
		Double[] vector = new Double[] { 1.1, 1.0, 1.1, 1.2, 1.3, 0.9 };
		testDataSet.put(1, vector);

		TrainDataSet trainDataSet = this.createTrainData();

		LibSVMClassifier classifier = new LibSVMClassifier();
		Map<Integer, ClassifyRes> classifyResMap = classifier.exec(
				trainDataSet, testDataSet);

		System.out
				.println("\n--------------------------分界线--------------------------\n");

		for (Entry<Integer, ClassifyRes> resEntry : classifyResMap.entrySet()) {

			ClassifyRes entryRes = resEntry.getValue();

			System.out.println("testIndex:" + resEntry.getKey());
			System.out.println("normalRes:" + entryRes.getNormalRes()
					+ " probilityRes:" + entryRes.getProbilityRes());

			Map<Double, Double> probResDistribution = entryRes
					.getProbResDistribution();
			for (Entry<Double, Double> distributionEntry : probResDistribution
					.entrySet()) {
				System.out.print("label:" + distributionEntry.getKey()
						+ " probility:" + distributionEntry.getValue() + "; ");
			}
		}
	}
}

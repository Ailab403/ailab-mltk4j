package test.mltk.libsvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import libsvm.svm_parameter;

import org.junit.Test;
import org.mltk.libsvm.LibSVMClassifier;
import org.mltk.libsvm.LibSVMParameFactory;
import org.mltk.libsvm.model.ClassifyRes;
import org.mltk.libsvm.model.TrainDataSet;

public class LibSVMClassifierTest {

	// 模拟数据集
	public TrainDataSet createTrainData() {

		final String[] vec1Node1 = new String[] { "1", "0" };
		final String[] vec1Node2 = new String[] { "2", "0" };
		final List<String[]> vector1 = new ArrayList<String[]>() {
			{
				add(vec1Node1);
				add(vec1Node2);
			}
		};

		final String[] vec2Node1 = new String[] { "1", "1" };
		final String[] vec2Node2 = new String[] { "2", "1" };
		final List<String[]> vector2 = new ArrayList<String[]>() {
			{
				add(vec2Node1);
				add(vec2Node2);
			}
		};

		final String[] vec3Node1 = new String[] { "1", "0" };
		final String[] vec3Node2 = new String[] { "2", "1" };
		final List<String[]> vector3 = new ArrayList<String[]>() {
			{
				add(vec3Node1);
				add(vec3Node2);
			}
		};

		final String[] vec4Node1 = new String[] { "1", "1" };
		final String[] vec4Node2 = new String[] { "2", "2" };
		final List<String[]> vector4 = new ArrayList<String[]>() {
			{
				add(vec4Node1);
				add(vec4Node2);
			}
		};

		// 注意，普通的map不允许key值重复，要使用允许key值重复的特殊map
		Map<Double, List<String[]>> trainData = new IdentityHashMap<Double, List<String[]>>() {
			{
				put(-1.0, vector1);
				put(-1.0, vector2);
				put(1.0, vector3);
				put(1.0, vector4);

			}
		};

		TrainDataSet trainDataSet = new TrainDataSet(trainData, 4, 2);

		return trainDataSet;
	}

	@Test
	public void testClassifierFromMemory() {

		Map<Integer, Double[]> testDataSet = new HashMap<Integer, Double[]>();
		Double[] vector = new Double[] { 0.0, 0.0 };
		testDataSet.put(1, vector);

		TrainDataSet trainDataSet = this.createTrainData();

		LibSVMParameFactory parameFactory = new LibSVMParameFactory(
				svm_parameter.C_SVC, svm_parameter.RBF, 1000, 0.0000001, 10, 1,
				1024);
		LibSVMClassifier classifier = new LibSVMClassifier();
		Map<Integer, ClassifyRes> classifyResMap = classifier.exec(
				trainDataSet, testDataSet, parameFactory);

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

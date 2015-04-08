package org.mltk.libsvm;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import libsvm.svm_node;
import libsvm.svm_problem;

public class LibSVMClassifier {

	// TODO 分装完善
	public Map<Integer, Integer> classifyDriver(
			Map<Double, List<String[]>> trainData, Integer trainSetSize,
			Integer trainSetScale, List<String[]> testData) {

		/*
		 * 加载训练集数据，将训练数据整理为LibSVM能够识别的格式
		 */
		svm_problem trainSet = new svm_problem();
		svm_node[][] trainSpace = new svm_node[trainSetSize][trainSetScale];
		double[] trainLabels = new double[trainSetSize];

		int trainEntryNum = 0;
		for (Entry<Double, List<String[]>> trainEntry : trainData.entrySet()) { // 大容量时，建议使用entry遍历map容器
			// 加载数据向量的训练标记
			trainLabels[trainEntryNum] = trainEntry.getKey();

			// 加载数据向量的特征值
			int vectorEleNum = 0;
			for (String[] vectorNode : trainEntry.getValue()) {
				svm_node trainNode = new svm_node();
				trainNode.index = Integer.parseInt(vectorNode[1]);
				trainNode.value = Double.parseDouble(vectorNode[2]);

				trainSpace[trainEntryNum][vectorEleNum] = trainNode;
				vectorEleNum++;
			}
			trainEntryNum++;
		}

		// 将标记数组，向量空间和训练集向量数量载入set模型
		trainSet.x = trainSpace;
		trainSet.y = trainLabels;
		trainSet.l = trainSetSize;
		
		

		return null;
	}
}

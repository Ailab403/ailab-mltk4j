package org.mltk.libsvm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

import org.mltk.libsvm.model.ClassifyRes;
import org.mltk.libsvm.model.TrainDataSet;

public class LibSVMClassifier {

	// 分类数据集
	private svm_problem classifyTrainSet;
	// 分类配置参数
	private svm_parameter classifyParame;

	// 设置分类训练数据集
	public void initClassifyTrainData(Map<Double, List<String[]>> trainData,
			Integer trainSetSize, Integer trainSetScale) throws Exception {

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
				trainNode.index = Integer.parseInt(vectorNode[0]);
				trainNode.value = Double.parseDouble(vectorNode[1]);

				trainSpace[trainEntryNum][vectorEleNum] = trainNode;
				vectorEleNum++;
			}
			trainEntryNum++;
		}

		// 将标记数组，向量空间和训练集向量数量载入set模型
		trainSet.x = trainSpace;
		trainSet.y = trainLabels;
		trainSet.l = trainSetSize;

		for (int i = 0; i < trainSpace.length; i++) {
			System.out.print(trainLabels[i] + ": ");
			for (int j = 0; j < trainSpace[i].length; j++) {
				System.out.print(trainSpace[i][j].value + " ");
			}
			System.out.println();
		}

		setClassifyTrainSet(trainSet);
	}

	// 设置分类配置参数
	public void initClassifyParame(LibSVMParameFactory parameFactory)
			throws Exception {

		svm_parameter svmParame = parameFactory.prodLibSVMParam();
		// 检查参数设置是否合法
		String parameError = svm.svm_check_parameter(getClassifyTrainSet(),
				svmParame);
		if (parameError != null) {
			System.err.println("参数设置错误! " + parameError);
			return;
		}

		setClassifyParame(parameFactory.prodLibSVMParam());
	}

	// LibSVM分类器主功能方法
	public Map<Integer, ClassifyRes> classifyDriver(
			Map<Integer, Double[]> testData) {

		// 准备要返回的数据
		Map<Integer, ClassifyRes> classifyResMap = new HashMap<Integer, ClassifyRes>();
		// 训练分类模型
		svm_model model = svm.svm_train(this.classifyTrainSet,
				this.classifyParame);

		try {
			svm.svm_save_model("svm_model_file", model);
			svm.svm_load_model("svm_model_file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 进行分类预测
		for (Entry<Integer, Double[]> testEntry : testData.entrySet()) {

			ClassifyRes classifyRes = new ClassifyRes();

			int testNodeIndex = testEntry.getKey();
			Double[] testNodeVector = testEntry.getValue();

			svm_node[] testNode = new svm_node[testNodeVector.length];
			for (int i = 0; i < testNodeVector.length; i++) {
				// 不能忘了先对svm_node进行初始化
				testNode[i] = new svm_node();

				testNode[i].index = i + 1;
				testNode[i].value = testNodeVector[i];
			}
			double[] probEstimates = new double[model.label.length];

			// 进行分类预测
			double probilityRes = svm.svm_predict_probability(model, testNode,
					probEstimates);
			double normalRes = svm.svm_predict(model, testNode);

			classifyRes.setProbilityRes(probilityRes);
			classifyRes.setNormalRes(normalRes);
			classifyRes.prodResDistribution(model, probEstimates);

			// 将这一项的预测结果加入结果映射集合
			classifyResMap.put(testNodeIndex, classifyRes);
		}

		return classifyResMap;
	}

	// LibSVM分类器执行接口，带手动参数工厂的执行方法
	public Map<Integer, ClassifyRes> exec(TrainDataSet trainDataSet,
			Map<Integer, Double[]> testDataSet,
			LibSVMParameFactory parameFactory) {

		Map<Integer, ClassifyRes> classifyResMap = new HashMap<Integer, ClassifyRes>();

		try {

			this.initClassifyTrainData(trainDataSet.getTrainData(),
					trainDataSet.getTrainSetSize(),
					trainDataSet.getTrainSetScale());
			this.initClassifyParame(parameFactory);

			classifyResMap = this.classifyDriver(testDataSet);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

		return classifyResMap;
	}

	// LibSVM分类器执行接口，不带手动参数工厂的执行方法，配置参数采用默认设置
	public Map<Integer, ClassifyRes> exec(TrainDataSet trainDataSet,
			Map<Integer, Double[]> testDataSet) {

		Map<Integer, ClassifyRes> classifyResMap = new HashMap<Integer, ClassifyRes>();

		try {

			this.initClassifyTrainData(trainDataSet.getTrainData(),
					trainDataSet.getTrainSetSize(),
					trainDataSet.getTrainSetScale());
			LibSVMParameFactory parameFactory = new LibSVMParameFactory();
			this.initClassifyParame(parameFactory);

			classifyResMap = this.classifyDriver(testDataSet);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

		return classifyResMap;
	}

	public svm_problem getClassifyTrainSet() {
		return classifyTrainSet;
	}

	public void setClassifyTrainSet(svm_problem classifyTrainData) {
		this.classifyTrainSet = classifyTrainData;
	}

	public svm_parameter getClassifyParame() {
		return classifyParame;
	}

	public void setClassifyParame(svm_parameter classifyParame) {
		this.classifyParame = classifyParame;
	}

}

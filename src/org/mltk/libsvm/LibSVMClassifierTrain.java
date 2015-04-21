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

/**
 * 
 * @author superhy
 *
 */
public class LibSVMClassifierTrain {

	// 分类数据集
	private svm_problem classifyTrainSet;
	// 分类配置参数
	private svm_parameter classifyParame;

	private LibSVMParameFactory parameFactory;

	public LibSVMClassifierTrain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LibSVMClassifierTrain(LibSVMParameFactory parameFactory) {
		super();
		this.parameFactory = parameFactory;
	}

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

	// LibSVM分类器训练主方法
	public svm_model classifierTrainDriver(String svmModelDiskPath) {

		// 训练分类模型
		svm_model model = svm.svm_train(this.classifyTrainSet,
				this.classifyParame);

		try {

			svm.svm_save_model(svmModelDiskPath, model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}

	// LibSVM分类器执行接口
	public Map<Integer, ClassifyRes> exec(TrainDataSet trainDataSet) {

		Map<Integer, ClassifyRes> classifyResMap = new HashMap<Integer, ClassifyRes>();

		try {

			this.initClassifyTrainData(trainDataSet.getTrainData(),
					trainDataSet.getTrainSetSize(),
					trainDataSet.getTrainSetScale());

			// 如果没有配置parameFactory，新建默认参数的parameFactory
			if (this.parameFactory == null) {
				setParameFactory(new LibSVMParameFactory());
			}
			this.initClassifyParame(this.parameFactory);

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

	public LibSVMParameFactory getParameFactory() {
		return parameFactory;
	}

	public void setParameFactory(LibSVMParameFactory parameFactory) {
		this.parameFactory = parameFactory;
	}
}

package org.mltk.task.t_mcmf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import libsvm.svm_parameter;

import org.mltk.libsvm.LibSVMClassifierForecast;
import org.mltk.libsvm.LibSVMClassifierTrain;
import org.mltk.libsvm.LibSVMParameFactory;
import org.mltk.libsvm.model.ClassifyRes;
import org.mltk.libsvm.model.TestDataItem;
import org.mltk.libsvm.model.TrainDataSet;

/**
 * 
 * @author superhy
 *
 */
public class LibSVMSentimentAnalysis {

	public TrainDataSet libsvmSentimentTrain(String trainModelPath) {

		try {

			Map<Double, List<String[]>> trainData = new IdentityHashMap<Double, List<String[]>>();
			Integer trainSetSize = 0;
			Integer trainSetScale = 50;

			File trainModelFile = new File(trainModelPath);
			BufferedReader br = new BufferedReader(new FileReader(
					trainModelFile));

			String trainModelLine = "";
			while ((trainModelLine = br.readLine()) != null) {
				List<String[]> trainVector = new ArrayList<String[]>();
				Integer vectorTag = 1;
				for (String item : trainModelLine.substring(0,
						trainModelLine.indexOf("l") - 1).split(" ")) {
					String[] strItem = { vectorTag.toString(), item };

					trainVector.add(strItem);
					vectorTag++;
				}

				String strLabel = trainModelLine.substring(
						trainModelLine.indexOf(":") + 1,
						trainModelLine.length());
				Double label = Double.valueOf(strLabel);

				trainData.put(label, trainVector);

				trainSetSize++;
			}

			TrainDataSet trainDataSet = new TrainDataSet(trainData,
					trainSetSize, trainSetScale);

			br.close();

			return trainDataSet;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			return null;
		}
	}

	public void trainLibsvmModel(String trainModelPath, String libsvmModelPath) {

		TrainDataSet trainDataSet = this.libsvmSentimentTrain(trainModelPath);

		LibSVMParameFactory parameFactory = new LibSVMParameFactory(
				svm_parameter.C_SVC, svm_parameter.RBF, 1000, 0.0000001, 10, 1,
				1024);

		LibSVMClassifierTrain classifierTrain = new LibSVMClassifierTrain(
				parameFactory);
		boolean trainFlag = classifierTrain.exec(libsvmModelPath, trainDataSet);

		System.out.println(trainFlag);
	}

	public List<ClassifyRes> libsvmSentimentAnalysis(String testModelPath,
			String libsvmModelPath) {

		try {

			File testModelFile = new File(testModelPath);
			BufferedReader br = new BufferedReader(
					new FileReader(testModelFile));

			List<Double[]> testVectors = new ArrayList<Double[]>();
			String testModelLine = "";
			while ((testModelLine = br.readLine()) != null) {
				Double[] testVector = new Double[50];
				int i = 0;
				for (String item : testModelLine.substring(0,
						testModelLine.length() - 1).split(" ")) {
					testVector[i++] = Double.valueOf(item);
				}

				testVectors.add(testVector);
			}

			List<ClassifyRes> classifyResList = new ArrayList<ClassifyRes>();
			LibSVMClassifierForecast classifierForecast = new LibSVMClassifierForecast();
			for (Double[] testVector : testVectors) {
				TestDataItem testDataItem = new TestDataItem("1", testVector,
						50);
				classifyResList.add(classifierForecast.exec(libsvmModelPath,
						testDataItem));
			}

			return classifyResList;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			return null;
		}
	}
}

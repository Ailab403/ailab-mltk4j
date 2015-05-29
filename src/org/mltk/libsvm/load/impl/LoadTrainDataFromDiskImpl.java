package org.mltk.libsvm.load.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.mltk.libsvm.model.TrainDataSet;

public class LoadTrainDataFromDiskImpl {

	public TrainDataSet loadTrainData(String trainDataPath) {

		try {
			File trainDataFile = new File(trainDataPath);
			BufferedReader br = new BufferedReader(
					new FileReader(trainDataFile));

			Map<Double, List<String[]>> trainData = new IdentityHashMap<Double, List<String[]>>();

			String trainDataFirstLine = br.readLine();

			// 处理第一行数据，并获得矩阵规模
			String[] trainFirstLineElem = trainDataFirstLine.split(" ");
			double trainFirstLineLabel = Double
					.parseDouble(trainFirstLineElem[0]);
			List<String[]> trainFirstElemList = new ArrayList<String[]>();
			// 获得训练集合规模
			int trainSetScale = trainFirstLineElem.length - 1;

			for (Integer i = 1; i < trainFirstLineElem.length; i++) {
				String[] trainFirstElem = new String[2];
				trainFirstElem[0] = i.toString();
				trainFirstElem[1] = trainFirstLineElem[i].toString();

				trainFirstElemList.add(trainFirstElem);
			}
			// 添加入第一行数据
			trainData.put(trainFirstLineLabel, trainFirstElemList);

			// 处理后面行的数据
			String trainDataLine = "";
			int trainSetSize = 1;
			while ((trainDataLine = br.readLine()) != null) {

				String[] trainLineElem = trainDataLine.split(" ");
				double trainLineLabel = Double.parseDouble(trainLineElem[0]);
				List<String[]> trainElemList = new ArrayList<String[]>();

				for (Integer i = 1; i < trainSetScale + 1; i++) {
					String[] trainElem = new String[2];
					trainElem[0] = i.toString();
					trainElem[1] = trainLineElem[i].toString();

					trainElemList.add(trainElem);
				}

				trainData.put(trainLineLabel, trainElemList);

				trainSetSize++;
			}
			
			TrainDataSet trainDataSet = new TrainDataSet(trainData, trainSetSize, trainSetScale);
			
			return trainDataSet;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
}

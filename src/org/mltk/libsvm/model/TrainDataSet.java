package org.mltk.libsvm.model;

import java.util.List;
import java.util.Map;

public class TrainDataSet {

	private Map<Double, List<String[]>> trainData;

	private Integer trainSetSize;
	private Integer trainSetScale;

	public TrainDataSet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainDataSet(Map<Double, List<String[]>> trainData,
			Integer trainSetSize, Integer trainSetScale) {
		super();
		this.trainData = trainData;
		this.trainSetSize = trainSetSize;
		this.trainSetScale = trainSetScale;
	}

	public Map<Double, List<String[]>> getTrainData() {
		return trainData;
	}

	public void setTrainData(Map<Double, List<String[]>> trainData) {
		this.trainData = trainData;
	}

	public Integer getTrainSetSize() {
		return trainSetSize;
	}

	public void setTrainSetSize(Integer trainSetSize) {
		this.trainSetSize = trainSetSize;
	}

	public Integer getTrainSetScale() {
		return trainSetScale;
	}

	public void setTrainSetScale(Integer trainSetScale) {
		this.trainSetScale = trainSetScale;
	}

	@Override
	public String toString() {
		return "TrainDataSet [trainData=" + trainData + ", trainSetSize="
				+ trainSetSize + ", trainSetScale=" + trainSetScale + "]";
	}

}

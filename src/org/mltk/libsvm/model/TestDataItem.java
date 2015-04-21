package org.mltk.libsvm.model;

import java.util.Arrays;

public class TestDataItem {

	// œÚ¡øid
	private String vecId;

	private Double[] testData;

	private Integer testSetScale;

	public TestDataItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestDataItem(String vecId, Double[] testData, Integer testSetScale) {
		super();
		this.vecId = vecId;
		this.testData = testData;
		this.testSetScale = testSetScale;
	}

	public String getVecId() {
		return vecId;
	}

	public void setVecId(String vecId) {
		this.vecId = vecId;
	}

	public Double[] getTestData() {
		return testData;
	}

	public void setTestData(Double[] testData) {
		this.testData = testData;
	}

	public Integer getTestSetScale() {
		return testSetScale;
	}

	public void setTestSetScale(Integer testSetScale) {
		this.testSetScale = testSetScale;
	}

	@Override
	public String toString() {
		return "TestDataSet [vecId=" + vecId + ", testData="
				+ Arrays.toString(testData) + ", testSetScale=" + testSetScale
				+ "]";
	}

}

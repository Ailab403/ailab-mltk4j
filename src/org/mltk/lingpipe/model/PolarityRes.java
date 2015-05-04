package org.mltk.lingpipe.model;

public class PolarityRes {

	private String fileName;
	private String bestCategory;
	private double polarityScore;

	public PolarityRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PolarityRes(String fileName, String bestCategory,
			double polarityScore) {
		super();
		this.fileName = fileName;
		this.bestCategory = bestCategory;
		this.polarityScore = polarityScore;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBestCategory() {
		return bestCategory;
	}

	public void setBestCategory(String bestCategory) {
		this.bestCategory = bestCategory;
	}

	public double getPolarityScore() {
		return polarityScore;
	}

	public void setPolarityScore(double polarityScore) {
		this.polarityScore = polarityScore;
	}

	@Override
	public String toString() {
		return "PolarityRes [fileName=" + fileName + ", bestCategory="
				+ bestCategory + ", polarityScore=" + polarityScore + "]";
	}

}

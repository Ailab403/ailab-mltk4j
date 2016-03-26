package org.mltk.openNLP;

import opennlp.tools.namefind.NameFinderME;

public class NameEntityFinder {

	// 默认参数
	private double probThreshold = 0.6;

	private String modelPath;

	public NameEntityFinder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NameEntityFinder(String modelPath) {
		super();
		this.modelPath = modelPath;
	}

	public NameEntityFinder(double probThreshold, String modelPath) {
		super();
		this.probThreshold = probThreshold;
		this.modelPath = modelPath;
	}

	/**
	 * 生成NameFinder
	 * 
	 * @return
	 */
	public NameFinderME prodNameFinder() {
		
		return null;
	}

}

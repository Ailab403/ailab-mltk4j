package org.mltk.libsvm.model;

import java.util.HashMap;
import java.util.Map;

import libsvm.svm_model;

/**
 * 
 * @author superhy
 *
 */
public class ClassifyRes {

	// 分类结果对应的向量id
	private String vecId;

	// 概率化分类结果
	private double probilityRes;
	// 常规分类结果（投票分类结果）
	private double normalRes;

	// 概率化分类结果具体的
	private Map<Double, Double> probResDistribution;

	public ClassifyRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 整理形成概率化结果的分布
	public void prodResDistribution(svm_model trainModel,
			double[] basicDistribution) {

		Map<Double, Double> resDistribution = new HashMap<Double, Double>();
		for (int i = 0; i < basicDistribution.length; i++) {
			resDistribution.put((double) trainModel.label[i],
					basicDistribution[i]);
		}

		setProbResDistribution(resDistribution);
	}

	public String getVecId() {
		return vecId;
	}

	public void setVecId(String vecId) {
		this.vecId = vecId;
	}

	public double getProbilityRes() {
		return probilityRes;
	}

	public void setProbilityRes(double probilityRes) {
		this.probilityRes = probilityRes;
	}

	public double getNormalRes() {
		return normalRes;
	}

	public void setNormalRes(double normalRes) {
		this.normalRes = normalRes;
	}

	public Map<Double, Double> getProbResDistribution() {
		return probResDistribution;
	}

	public void setProbResDistribution(Map<Double, Double> probResDistribution) {
		this.probResDistribution = probResDistribution;
	}

	@Override
	public String toString() {
		return "ClassifyRes [vecId=" + vecId + ", probilityRes=" + probilityRes
				+ ", normalRes=" + normalRes + ", probResDistribution="
				+ probResDistribution + "]";
	}

}

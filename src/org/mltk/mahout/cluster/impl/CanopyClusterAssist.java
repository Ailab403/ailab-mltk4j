package org.mltk.mahout.cluster.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.clustering.canopy.Canopy;
import org.apache.mahout.clustering.canopy.CanopyClusterer;
import org.apache.mahout.clustering.dirichlet.UncommonDistributions;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

public class CanopyClusterAssist {

	// 专门为canopy算法准备的vector数据集
	private static List<Vector> canopyVectors = null;

	public CanopyClusterAssist() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CanopyClusterAssist(List<Vector> canopyVectors) {
		super();
		CanopyClusterAssist.canopyVectors = canopyVectors;
	}

	/**
	 * 已经配置T1和T2默认值的canopy生成器
	 * 
	 * @param ponits
	 * @param distanceMeasure
	 * @return
	 */
	public static Map<String, Object> generateClusterCenter(
			List<Vector> ponits, DistanceMeasure distanceMeasure) {

		// 准备返回的组合数据包
		Map<String, Object> canopyResMap = new HashMap<String, Object>();

		// 参数3。0和1，5是canopy算法的阈值参数T1和T2
		List<Canopy> canopies = CanopyClusterer.createCanopies(ponits,
				new EuclideanDistanceMeasure(), 10.0, 9.5);

		List<Vector> pickedCenter = new ArrayList<Vector>();
		for (Canopy canopy : canopies) {

			// TODO delete print
			System.out.println("Canopy id:" + canopy.getId() + " center: "
					+ canopy.getCenter().asFormatString());

			pickedCenter.add(canopy.getCenter());
		}

		canopyResMap.put("k", canopies.size());
		canopyResMap.put("centers", pickedCenter);

		return canopyResMap;
	}

	/**
	 * 自定义T1和T2参数的canopy生成器
	 * 
	 * @param ponits
	 * @param distanceMeasure
	 * @param T1
	 * @param T2
	 * @return
	 */
	public static Map<String, Object> generateClusterCenter(
			List<Vector> ponits, DistanceMeasure distanceMeasure, Double T1,
			Double T2) {

		// 防止T1和T2参数不存在的情况，使用默认值
		if (T1 == null || T2 == null) {
			return generateClusterCenter(ponits, distanceMeasure);
		}

		// 准备返回的组合数据包
		Map<String, Object> canopyResMap = new HashMap<String, Object>();

		List<Canopy> canopies = CanopyClusterer.createCanopies(ponits,
				new EuclideanDistanceMeasure(), T1, T2);

		List<Vector> pickedCenter = new ArrayList<Vector>();
		for (Canopy canopy : canopies) {

			// TODO delete print
			System.out.println("Canopy id:" + canopy.getId() + " center: "
					+ canopy.getCenter().asFormatString());

			pickedCenter.add(canopy.getCenter());
		}

		canopyResMap.put("k", canopies.size());
		canopyResMap.put("centers", pickedCenter);

		return canopyResMap;
	}

	public static List<Vector> getCanopyVectors() {
		return canopyVectors;
	}

	public static void setCanopyVectors(List<Vector> canopyVectors) {
		CanopyClusterAssist.canopyVectors = canopyVectors;
	}

	/*-----------------------------以下为测试内容------------------------------*/

	/**
	 * 生成用于实验的随机节点
	 * 
	 * @param vectors
	 * @param num
	 * @param mx
	 * @param my
	 * @param sd
	 */
	public static void generateSamples(List<Vector> vectors, int num,
			double mx, double my, double mz, double sd) {
		for (int i = 0; i < num; i++) {
			vectors.add(new DenseVector(new double[] {
					UncommonDistributions.rNorm(mx, sd),
					UncommonDistributions.rNorm(my, sd),
					UncommonDistributions.rNorm(mz, sd) }));
		}
	}

	// TODO delete
	/*
	 * 测试主函数
	 */
	public static void main(String[] args) {

		List<Vector> sampleData = new ArrayList<Vector>();

		generateSamples(sampleData, 400, 1, 1, -1, 3);
		generateSamples(sampleData, 300, 1, 0, 2, 0.5);
		generateSamples(sampleData, 300, 0, 2, 0, 0.1);

		// 参数3。0和1，5是canopy算法的阈值参数T1和T2
		List<Canopy> canopies = CanopyClusterer.createCanopies(sampleData,
				new EuclideanDistanceMeasure(), 10.0, 9.5);

		for (Canopy canopy : canopies) {
			System.out.println("Canopy id:" + canopy.getId() + " center: "
					+ canopy.getCenter().asFormatString());
		}
	}
}

package org.mltk.mahout.cluster.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.clustering.dirichlet.UncommonDistributions;
import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansClusterer;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.mltk.mahout.cluster.ClusterDataModel;
import org.mltk.mahout.cluster.ClusterPreUtil;
import org.mltk.mahout.cluster.ClusterDistanceFormat;
import org.mltk.mahout.cluster.DataDistanceFactory;
import org.mltk.mahout.util.RandomPointsUtil;

/**
 * 普通内存处理形式的Kmeans算法实现
 * 
 * @author superhy
 * 
 */
public class KmeansClusterImpl implements ClusterDataModel {

	// kmeans算法簇的个数
	private Integer k;
	private Integer disTypeNum;
	private Integer vecTypeNum;

	public KmeansClusterImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KmeansClusterImpl(Integer k, Integer disTypeNum, Integer vecTypeNum) {
		super();
		this.k = k;
		this.disTypeNum = disTypeNum;
		this.vecTypeNum = vecTypeNum;
	}

	@Override
	public Map<Integer, Integer> clusterDriver(Integer vecTypeNum,
			DistanceMeasure distanceMeasure, List<Vector> pointsVectors) {

		// 聚类返回结果
		Map<Integer, Integer> clusterResMap = new HashMap<Integer, Integer>();

		// 必须先按照目标簇数来对所有点随机选择中心点，聚类才能正常进行
		List<Vector> randomPoints = RandomPointsUtil.chooseRandomPoints(
				pointsVectors, this.k);

		List<Cluster> clusters = new ArrayList<Cluster>();

		int clusterId = 0;
		for (Vector v : randomPoints) {
			clusters.add(new Cluster(v, clusterId++,
					new EuclideanDistanceMeasure()));
		}

		// 前一个数字代表聚类算法迭代次数，后面一个数字代表收敛阈值
		List<List<Cluster>> finalClusters = KMeansClusterer.clusterPoints(
				pointsVectors, clusters, distanceMeasure, 10, 0.001);

		// 获得簇序号和中心点映射集合
		Map<Integer, Vector> clusterIdCenterMap = new HashMap<Integer, Vector>();
		for (Cluster cluster : finalClusters.get(finalClusters.size() - 1)) {
			// TODO delete print
			System.out.println("Cluster id:" + cluster.getId() + " center: "
					+ cluster.getCenter().asFormatString());

			clusterIdCenterMap.put(cluster.getId(), cluster.getCenter());
		}

		// 手动整理出点向量与所属簇的映射关系
		clusterResMap = ClusterDistanceFormat.checkPointBelongCenter(
				pointsVectors, clusterIdCenterMap, distanceMeasure);

		return clusterResMap;
	}

	@Override
	public Map<Integer, Integer> exec(double[][] points) {

		// 聚类点集合
		List<Vector> pointsVectors = ClusterPreUtil.getPoints(vecTypeNum,
				points);

		// 获得距离标准实体
		DistanceMeasure distance = DataDistanceFactory
				.prodDistance(this.disTypeNum);

		Map<Integer, Integer> clusterResMap = this.clusterDriver(
				this.vecTypeNum, distance, pointsVectors);
		return clusterResMap;
	}

	@Override
	public Map<Integer, Integer> exec(List<Vector> pointsVectors) {

		// 获得距离标准实体
		DistanceMeasure distance = DataDistanceFactory
				.prodDistance(this.disTypeNum);

		Map<Integer, Integer> clusterResMap = this.clusterDriver(
				this.vecTypeNum, distance, pointsVectors);
		return clusterResMap;
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

		// generateSamples(sampleData, 400, 1, 1, -1, 3);
		// generateSamples(sampleData, 300, 1, 0, 2, 0.5);
		// generateSamples(sampleData, 300, 0, 2, 0, 0.1);

		double[][] points = { { 1, 1, 1 }, { 1, 2, 2 }, { 3, 3, 2 },
				{ 2, 1, 1 }, { 2, 2, 3 }, { 8, 8, 7 }, { 9, 8, 7 },
				{ 8, 9, 8 }, { 9, 9, 8 } };

		int k = 2;

		// List<Vector> randomPoints = RandomPointsUtil.chooseRandomPoints(
		// sampleData, k);

		// 待处理数据向量列表
		List<Vector> randomPointsPre = ClusterPreUtil.getPoints(1, points);
		// 必须先按照目标簇数来对所有点随机选择中心点，聚类才能正常进行，随即中心点向量列表
		List<Vector> randomPoints = RandomPointsUtil.chooseRandomPoints(
				randomPointsPre, k);

		List<Cluster> clusters = new ArrayList<Cluster>();

		int clusterId = 0;
		for (Vector v : randomPoints) {
			clusters.add(new Cluster(v, clusterId++,
					new EuclideanDistanceMeasure()));
		}

		// 前一个数字代表聚类算法迭代次数，后面一个数字代表收敛阈值
		List<List<Cluster>> finalClusters = KMeansClusterer.clusterPoints(
				randomPointsPre, clusters, new EuclideanDistanceMeasure(), 1,
				0.001);
		for (Cluster cluster : finalClusters.get(finalClusters.size() - 1)) {

			System.out.println("Cluster id:" + cluster.getId() + " center: "
					+ cluster.getCenter().asFormatString());
		}

	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public Integer getDisTypeNum() {
		return disTypeNum;
	}

	public void setDisTypeNum(Integer disTypeNum) {
		this.disTypeNum = disTypeNum;
	}

	public Integer getVecTypeNum() {
		return vecTypeNum;
	}

	public void setVecTypeNum(Integer vecTypeNum) {
		this.vecTypeNum = vecTypeNum;
	}

}

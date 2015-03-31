package org.mltk.mahout.cluster.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansClusterer;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.Vector;
import org.mltk.mahout.cluster.ClusterDataModel;
import org.mltk.mahout.cluster.ClusterPreUtil;
import org.mltk.mahout.cluster.ClusterDistanceFormat;
import org.mltk.mahout.cluster.DataDistanceFactory;

public class CanopyKmeansClusterImpl implements ClusterDataModel {

	// 这里省略了kmeans算法k值的设定

	private Integer disTypeNum;
	private Integer vecTypeNum;
	// 用于限定canopy生成算法的参数T1和T2
	private Double T1;
	private Double T2;

	public CanopyKmeansClusterImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CanopyKmeansClusterImpl(Integer disTypeNum, Integer vecTypeNum) {
		super();
		this.disTypeNum = disTypeNum;
		this.vecTypeNum = vecTypeNum;
	}

	public CanopyKmeansClusterImpl(Integer disTypeNum, Integer vecTypeNum,
			Double t1, Double t2) {
		super();
		this.disTypeNum = disTypeNum;
		this.vecTypeNum = vecTypeNum;
		T1 = t1;
		T2 = t2;
	}

	@Override
	public Map<Integer, Integer> clusterDriver(Integer vecTypeNum,
			DistanceMeasure distanceMeasure, List<Vector> pointsVectors) {

		// 判断canopy专用数据集是否已经事先设定好，需要事先设定canopy数据集，且不能使用接口定义，这是这个实现类的一个缺陷
		if (CanopyClusterAssist.getCanopyVectors() == null) {
			System.err.println("尚未设定canopy数据集，需要设定！error：setCanopyVectors()");
			return null;
		}

		// 聚类返回结果
		Map<Integer, Integer> clusterResMap = new HashMap<Integer, Integer>();

		// 通过canopy算法生成k值和中心点
		Map<String, Object> canopyResMap = CanopyClusterAssist
				.generateClusterCenter(CanopyClusterAssist.getCanopyVectors(),
						distanceMeasure, this.T1, this.T2);

		List<Vector> canopyPoints = (List<Vector>) canopyResMap.get("centers");

		List<Cluster> clusters = new ArrayList<Cluster>();

		int clusterId = 0;
		for (Vector v : canopyPoints) {
			clusters.add(new Cluster(v, clusterId++,
					new EuclideanDistanceMeasure()));
		}

		// 前一个数字代表聚类算法迭代次数，后面一个数字代表收敛阈值
		List<List<Cluster>> finalClusters = KMeansClusterer.clusterPoints(
				pointsVectors, clusters, distanceMeasure, 10, 0.01);

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
		// 需要重新生成canopy聚类点集合，否则canopy引擎会将原来的点集破坏
		CanopyClusterAssist.setCanopyVectors(ClusterPreUtil.getPoints(
				vecTypeNum, points));

		// 获得距离标准实体
		DistanceMeasure distance = DataDistanceFactory
				.prodDistance(this.disTypeNum);

		Map<Integer, Integer> clusterResMap = this.clusterDriver(
				this.vecTypeNum, distance, pointsVectors);
		return clusterResMap;
	}

	@Override
	public Map<Integer, Integer> exec(List<Vector> pointsVectors) {

		// 判断canopy专用数据集是否已经事先设定好，需要事先设定canopy数据集，且不能使用接口定义，这是这个实现类的一个缺陷
		if (CanopyClusterAssist.getCanopyVectors() == null) {
			System.err.println("尚未设定canopy数据集，需要设定！error：setCanopyVectors()");
			return null;
		}

		// 获得距离标准实体
		DistanceMeasure distance = DataDistanceFactory
				.prodDistance(this.disTypeNum);

		Map<Integer, Integer> clusterResMap = this.clusterDriver(
				this.vecTypeNum, distance, pointsVectors);
		return clusterResMap;
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

	public Double getT1() {
		return T1;
	}

	public void setT1(Double t1) {
		T1 = t1;
	}

	public Double getT2() {
		return T2;
	}

	public void setT2(Double t2) {
		T2 = t2;
	}

}

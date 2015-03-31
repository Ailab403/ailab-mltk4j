package org.mltk.mahout.cluster;

import java.util.List;
import java.util.Map;

import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

/**
 * 
 * @author superhy
 * 
 */
public interface ClusterDataModel {

	// 用于HDFS模式的聚类文件路径
	public static String CLUSTER_ALL_FOLDER = ".\\file\\cluster";
	public static String CLUSTER_TESTDATA_DIR = ".\\file\\cluster\\testdata";
	public static String CLUSTER_OUTPUT_DIR = ".\\file\\cluster\\output";
	public static String CLUSTER_CLUSTERS_DIR = ".\\file\\cluster\\testdata\\clusters";
	public static String CLUSTER_POINTS_DIR = ".\\file\\cluster\\testdata\\points";

	/**
	 * 实现聚类引擎
	 * 
	 * @param vecTypeNum
	 * @param distance
	 * @param points
	 * @return
	 */
	public Map<Integer, Integer> clusterDriver(Integer vecTypeNum,
			DistanceMeasure distance, List<Vector> pointsVectors);

	/**
	 * 执行聚类引擎
	 * 
	 * @param points
	 * @return
	 */
	public Map<Integer, Integer> exec(double[][] points);

	/**
	 * 执行聚类引擎，直接传入vector
	 * 
	 * @param pointsVectors
	 * @return
	 */
	@Deprecated
	public Map<Integer, Integer> exec(List<Vector> pointsVectors);

}

package org.mltk.mahout.cluster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

public class ClusterDistanceFormat {

	/**
	 * 计算连个向量之间的距离
	 * 
	 * @param vectorA
	 * @param vectorB
	 * @param distanceMeasure
	 */
	public static double cmptVectorDistance(Vector vectorA, Vector vectorB,
			DistanceMeasure distanceMeasure) {

		return distanceMeasure.distance(vectorA, vectorB);
	}

	/**
	 * 计算每个点与簇中心的距离已确定所属簇
	 * 
	 * @param pointsVectors
	 * @param clusterIdCenterMap
	 * @return
	 */
	public static Map<Integer, Integer> checkPointBelongCenter(
			List<Vector> pointsVectors,
			Map<Integer, Vector> clusterIdCenterMap,
			DistanceMeasure distanceMeasure) {

		Map<Integer, Integer> clusterResMap = new HashMap<Integer, Integer>();

		// 遍历每个簇中心选择最近的中心并归属于该簇
		int pointId = 0;
		
		for (Vector point : pointsVectors) {

			int centerId = 0;
			double minDistance = cmptVectorDistance(point,
					clusterIdCenterMap.get(centerId), distanceMeasure);
			for (Map.Entry<Integer, Vector> entry : clusterIdCenterMap
					.entrySet()) {

				double pointsDistance = cmptVectorDistance(point,
						entry.getValue(), distanceMeasure);
				if (minDistance > pointsDistance) {
					centerId = entry.getKey();
					minDistance = pointsDistance;
				}
			}

			clusterResMap.put(pointId++, centerId);
		}

		return clusterResMap;
	}
}

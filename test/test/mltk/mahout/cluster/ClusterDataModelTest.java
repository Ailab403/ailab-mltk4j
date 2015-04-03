package test.mltk.mahout.cluster;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.mahout.math.Vector;
import org.junit.Test;
import org.mltk.mahout.cluster.ClusterDataModel;
import org.mltk.mahout.cluster.ClusterPreUtil;
import org.mltk.mahout.cluster.impl.CanopyClusterAssist;
import org.mltk.mahout.cluster.impl.CanopyKmeansClusterImpl;
import org.mltk.mahout.cluster.impl.HDFSKmeansClusterImpl;
import org.mltk.mahout.cluster.impl.KmeansClusterImpl;

public class ClusterDataModelTest {

	@Test
	public void testKmeansCluster() {

		double[][] points = { { 1, 1, 0, 0 }, { 1, 2, 0, 0 }, { 2, 1, 0, 1 },
				{ 2, 2, 1, 1 }, { 3, 3, 2, 1 }, { 8, 8, 7, 2 }, { 9, 8, 9, 2 },
				{ 8, 9, 9, 3 }, { 9, 9, 8, 3 } };

		ClusterDataModel clusterDriver = new KmeansClusterImpl(2, 1, 3);
		Map<Integer, Integer> clusterResMap = clusterDriver.exec(points);
		// 遍历结果键值对，输出结果
		Iterator<Integer> iterator = clusterResMap.keySet().iterator();
		while (iterator.hasNext()) {
			Integer key = iterator.next();
			Integer value = clusterResMap.get(key);

			System.out.println(key.toString() + " belongs to cluster "
					+ value.toString());
		}
	}

	@Test
	public void testHDFSKmeansCluster() {
		// ClusterDataModelPre testObj = new ClusterDataModelPre();
		// testObj.kmeansCluster();

		double[][] points = { { 1, 1, 0, 0 }, { 1, 2, 0, 0 }, { 2, 1, 0, 1 },
				{ 2, 2, 1, 1 }, { 3, 3, 2, 1 }, { 8, 8, 7, 2 }, { 9, 8, 9, 2 },
				{ 8, 9, 9, 3 }, { 9, 9, 8, 3 } };

		ClusterDataModel clusterDriver = new HDFSKmeansClusterImpl(2, 1, 3);
		Map<Integer, Integer> clusterResMap = clusterDriver.exec(points);
		// 遍历结果键值对，输出结果
		Iterator<Integer> iterator = clusterResMap.keySet().iterator();
		while (iterator.hasNext()) {
			Integer key = iterator.next();
			Integer value = clusterResMap.get(key);

			System.out.println(key.toString() + " belongs to cluster "
					+ value.toString());
		}
	}

	@Test
	public void testCanopyKmeansCluster() {
		// ClusterDataModelPre testObj = new ClusterDataModelPre();
		// testObj.kmeansCluster();

		double[][] points = { { 1, 1, 0, 0 }, { 1, 2, 0, 0 }, { 2, 1, 0, 1 },
				{ 2, 2, 1, 1 }, { 3, 3, 2, 1 }, { 8, 8, 7, 2 }, { 9, 8, 9, 2 },
				{ 8, 9, 9, 3 }, { 9, 9, 8, 3 } };

		ClusterDataModel clusterDriver = new CanopyKmeansClusterImpl(1, 3,
				10.0, 9.5);
		List<Vector> pointsVectors = ClusterPreUtil.getPoints(1, points);
		CanopyClusterAssist.setCanopyVectors(ClusterPreUtil
				.getPoints(1, points));
		Map<Integer, Integer> clusterResMap = clusterDriver.exec(pointsVectors);

		// System.out.println(clusterResMap.size());

		// 遍历结果键值对，输出结果
		Iterator<Integer> iterator = clusterResMap.keySet().iterator();
		while (iterator.hasNext()) {
			Integer key = iterator.next();
			Integer value = clusterResMap.get(key);

			System.out.println(key.toString() + " belongs to cluster "
					+ value.toString());
		}
	}

}

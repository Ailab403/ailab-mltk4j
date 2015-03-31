package org.mltk.mahout.cluster;

import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.TanimotoDistanceMeasure;

public class DataDistanceFactory {

	/**
	 * 生产距离量化衡量实体，默认配置
	 * 
	 * @return
	 */
	public synchronized static DistanceMeasure prodDistance() {

		DistanceMeasure distance = null;

		try {
			distance = new EuclideanDistanceMeasure();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return distance;
	}

	/**
	 * 生产距离量化衡量实体，传入对应的实体编号：欧氏距离：1，平方欧式距离：2，曼哈顿距离：3，余弦距离：4，谷本距离：5
	 * 
	 * @param disTypeNum
	 */
	public synchronized static DistanceMeasure prodDistance(Integer disTypeNum) {

		DistanceMeasure distance = null;

		if (disTypeNum == null || (disTypeNum < 1 || disTypeNum > 5)) {
			disTypeNum = 1;
		}
		
		try {
			switch (disTypeNum) {
			case 1:
				distance = new EuclideanDistanceMeasure();
				break;
			case 2:
				distance = new SquaredEuclideanDistanceMeasure();
				break;
			case 3:
				distance = new ManhattanDistanceMeasure();
				break;
			case 4:
				distance = new CosineDistanceMeasure();
				break;
			case 5:
				distance = new TanimotoDistanceMeasure();
			default:
				// 默认使用欧氏距离标准
				distance = new EuclideanDistanceMeasure();
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return distance;
	}
}

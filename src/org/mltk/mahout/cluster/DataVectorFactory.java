package org.mltk.mahout.cluster;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;

public class DataVectorFactory {

	/**
	 * 生产向量转化工具，默认配置
	 * 
	 * @param arrayLength
	 * @return
	 */
	public synchronized static Vector prodVector(Integer arrayLength) {

		Vector vec = null;

		try {
			vec = new SequentialAccessSparseVector(arrayLength);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec;
	}

	/**
	 * 生产向量转化工具，传入对应的实体编号：密集向量空间：1，稀疏向量：2，优化的随机访问向量空间：3
	 * 
	 * @param arrayLength
	 * @param vecTypeNum
	 * @return
	 */
	public synchronized static Vector prodVector(Integer arrayLength,
			Integer vecTypeNum) {

		Vector vec = null;

		if (vecTypeNum == null || (vecTypeNum < 1 || vecTypeNum > 3)) {
			vecTypeNum = 3;
		}

		try {
			switch (vecTypeNum) {
			case 1:
				vec = new DenseVector(arrayLength);
				break;
			case 2:
				vec = new RandomAccessSparseVector(arrayLength);
				break;
			case 3:
				vec = new SequentialAccessSparseVector(arrayLength);
				break;
			default:
				// 默认使用优化的随机访问向量空间
				vec = new SequentialAccessSparseVector(arrayLength);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec;
	}
}

package org.mltk.mahout.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.clustering.dirichlet.UncommonDistributions;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

public class RandomPointsUtil {

	public static List<Vector> chooseRandomPoints(List<Vector> srcVector, int k) {
		List<Vector> destVector = new ArrayList<Vector>();
		for (int i = 0; i < k; i++) {
			int index = (int) (Math.random() * srcVector.size());
			destVector.add(srcVector.get(index));
		}
		return destVector;
	}

	private static void generateSamples(List<Vector> sampleData, int num,
			double mx, double my, double sd, int card) {
		for (int i = 0; i < num; i++) {
			DenseVector v = new DenseVector(card);
			for (int j = 0; j < card; j++) {
				v.set(j, UncommonDistributions.rNorm(mx, sd));
			}
			sampleData.add(v);
		}
	}

	/**
	 * Generate 2-d samples for backwards compatibility with existing tests25 * @param
	 * numint number of samples to generate
	 * 
	 * @param mx
	 *            double x-value of the sample mean
	 * @param mydouble
	 *            y-value of the sample mean
	 * @param sddouble
	 *            standard deviation of the samples
	 */
	public static void generateSamples(List<Vector> sampleData, int num,
			double mx, double my, double sd) {

		generateSamples(sampleData, num, mx, my, sd, 2);
	}
}

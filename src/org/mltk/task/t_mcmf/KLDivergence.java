package org.mltk.task.t_mcmf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mltk.task.t_mcmf.model.AllLdaItemSet;

/**
 * 
 * @author dd
 *
 */
public class KLDivergence {

	/**
	 * 
	 * TODO 传Map，主题的词分布
	 * 
	 * @author dd
	 * @param distribute1
	 * @param distribute2
	 * @return
	 */
	public static double compTopicsGenKL(Map<Integer, Double> distribute1,
			Map<Integer, Double> distribute2) {

		List<String> wordTextList = new ArrayList<String>();
		for (Entry<Integer, Double> dist : distribute1.entrySet()) {
			wordTextList.add(AllLdaItemSet.findWordText(dist.getKey()));
		}
		for (Entry<Integer, Double> dist : distribute2.entrySet()) {
			wordTextList.add(AllLdaItemSet.findWordText(dist.getKey()));
		}

		List<Double> themeProValueA = new ArrayList<Double>();
		List<Double> themeProValueB = new ArrayList<Double>();

		for (String wordText : wordTextList) {
			boolean flagA = false;
			for (Entry<Integer, Double> dist : distribute1.entrySet()) {
				if (AllLdaItemSet.findWordText(dist.getKey()).equals(wordText)) {
					themeProValueA.add(dist.getValue());
					flagA = true;
					break;
				}
			}
			if (!flagA) {
				// add smoothing factor
				themeProValueA.add(0.0001);
			}

			boolean flagB = false;
			for (Entry<Integer, Double> dist : distribute2.entrySet()) {
				if (AllLdaItemSet.findWordText(dist.getKey()).equals(wordText)) {
					themeProValueB.add(dist.getValue());
					flagB = true;
					break;
				}
			}
			if (!flagB) {
				// add smoothing factor
				themeProValueB.add(0.0001);
			}
		}

		double KLDis = 0;
		double KLSingleA = 0;
		double KLSingleB = 0;

		// 传List进去
		for (int i = 0; i < themeProValueB.size(); i++) {
			double klDistanceA = Math.log(themeProValueA.get(i)
					/ themeProValueB.get(i))
					* themeProValueA.get(i);
			KLSingleA += klDistanceA;
			double klDistanceB = Math.log(themeProValueB.get(i)
					/ themeProValueA.get(i))
					* themeProValueB.get(i);
			KLSingleB += klDistanceB;
		}

		KLDis = (KLSingleA + KLSingleB) / (2 * themeProValueB.size());
		// System.out.println("KL散度为：" + KLDis);

		return KLDis;

	}
}

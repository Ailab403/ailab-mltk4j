package org.mltk.task.t_mcmf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author dd
 *
 */
public class KLDivergence {

	/**
	 * 传Map
	 * 
	 * @author dd
	 * @param distribute1
	 * @param distribute2
	 * @return
	 */
	public static double compTopicsGenKL(Map<Integer, Double> distribute1,
			Map<Integer, Double> distribute2) {

		/*String keyStringA = "";
		String keyStringB = "";
		List<Integer> themeProKeyA = new ArrayList<Integer>();
		List<Integer> themeProKeyB = new ArrayList<Integer>();
		// 用户存储所有的key
		List<Integer> themeProKey = new ArrayList<Integer>();

		double KLDis = 0;

		for (Entry<Integer, Double> themeMapA : distribute1.entrySet()) {
			int themeKeyA = themeMapA.getKey();
			themeProKeyA.add(themeKeyA);
			// 先把主题概率A中的key赋给themeProKey
			themeProKey.add(themeKeyA);
			keyStringA += String.valueOf(themeKeyA) + ",";
			// double themeProANumber = themeMapA.getValue();

		}

		for (Entry<Integer, Double> themeMapB : distribute2.entrySet()) {
			int themeKeyB = themeMapB.getKey();
			themeProKeyB.add(themeKeyB);
			keyStringB += String.valueOf(themeKeyB) + ",";
		}

		// 判别key是否存在themeProB中
		for (Integer keyA : themeProKeyA) {
			// 若不存在 则在themeProB中加入值
			if (!keyStringB.contains(String.valueOf(keyA))) {
				distribute2.put(keyA, (double) 0);

			}
		}

		// 同理 判断key是否存在themeProA中
		for (Integer keyB : themeProKeyB) {
			if (!keyStringA.contains(String.valueOf(keyB))) {
				distribute1.put(keyB, (double) 0);
				// 把主题概率A中没有的key添加进去
				themeProKey.add(keyB);
			}

		}

		// 通过统一的key得到value来计算ks散度
		for (Integer key : themeProKey) {
			double themeProAValue = distribute1.get(key);
			double themeProBValue = distribute2.get(key);
			// 有的value值为0
			if (themeProBValue == 0) {
				break;
			} else {
				double klSingle = Math.log(themeProAValue / themeProBValue)
						* themeProAValue;
				KLDis = +klSingle;
			}

		}

		return KLDis;*/
		
		return 0.5;
	}
}

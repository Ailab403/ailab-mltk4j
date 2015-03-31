package org.mltk.mahout.taste;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class DataSimilarityFactory {

	/**
	 * 生产基于用户的相似度实体，默认配置
	 * 
	 * @param model
	 * @return
	 */
	public synchronized static UserSimilarity prodUserSimilarity(DataModel model) {

		UserSimilarity userSim = null;

		try {
			userSim = new PearsonCorrelationSimilarity(model,
					Weighting.WEIGHTED);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userSim;
	}

	/**
	 * 生产基于用户的相似度实体，传入对应实体的编号
	 * 带权值的皮尔逊相似度：1，欧氏距离：2，余弦相似度：3，斯皮尔曼相似度：4，Jaccard相似度：5，对数似然比相似度：6
	 * 
	 * @param model
	 * @param simTypeNum
	 * @return
	 */
	public synchronized static UserSimilarity prodUserSimilarity(
			DataModel model, Integer simTypeNum) {

		UserSimilarity userSim = null;

		if (simTypeNum == null || (simTypeNum < 1 || simTypeNum > 6)) {
			simTypeNum = 1;
		}

		try {
			switch (simTypeNum) {
			case 1:
				userSim = new PearsonCorrelationSimilarity(model,
						Weighting.WEIGHTED);
				break;
			case 2:
				userSim = new EuclideanDistanceSimilarity(model);
				break;
			case 3:
				// 余弦相似度就是不带权重的皮尔逊相似度
				userSim = new PearsonCorrelationSimilarity(model);
				break;
			case 4:
				userSim = new SpearmanCorrelationSimilarity(model);
				break;
			case 5:
				userSim = new TanimotoCoefficientSimilarity(model);
				break;
			case 6:
				userSim = new LogLikelihoodSimilarity(model);
				break;
			default:
				// 默认使用带权值的皮尔逊相似度
				userSim = new PearsonCorrelationSimilarity(model,
						Weighting.WEIGHTED);
				break;
			}
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userSim;
	}

	/**
	 * 生产基于物品的相似度实体，传入对应实体的编号
	 * 
	 * @param model
	 * @param simTypeNum
	 * @return
	 */
	public synchronized static ItemSimilarity prodItemSimilarity(
			DataModel model, Integer simTypeNum) {

		return null;
	}

}

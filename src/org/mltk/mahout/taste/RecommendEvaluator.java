package org.mltk.mahout.taste;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.common.RandomUtils;
import org.mltk.mahout.model.TasteEvalScore;

/**
 * 
 * @author superhy
 * 
 *         推荐引擎评测工具
 * 
 */
public class RecommendEvaluator {

	public static TasteEvalScore recommenderEvalProd(DataModel testModel,
			final RecommendDataModel testRecommendDataModel, final Integer N,
			double b, Boolean flagF1ValueBySelf) {

		// 生成可重复的结果
		RandomUtils.useTestSeed();

		// 载入评价器
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		GenericRecommenderIRStatsEvaluator irstatsEvaluator = new GenericRecommenderIRStatsEvaluator();

		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {

			@Override
			public Recommender buildRecommender(DataModel model)
					throws TasteException {
				return testRecommendDataModel.recommenderProd(model);
			}
		};

		// 测评指标
		TasteEvalScore tasteScore = null;

		try {
			double difference = evaluator.evaluate(recommenderBuilder, null,
					testModel, 0.90, 1.0);

			// 评估推荐两个结果时的P和R
			IRStatistics stats = irstatsEvaluator.evaluate(recommenderBuilder,
					null, testModel, null, 2,
					GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
			double precision = stats.getPrecision();
			double recall = stats.getRecall();
			double f1 = stats.getF1Measure(); // 默认的f1值，这里可以选择使用手动调节参数计算出的f1值

			tasteScore = new TasteEvalScore(difference, precision, recall);
			// 判断是否手动计算f1值
			if (flagF1ValueBySelf == true) {
				tasteScore.calculateF1Value(b);
			} else {
				tasteScore.setF1(f1);
			}

		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return tasteScore;
		}

		return tasteScore;
	}

}

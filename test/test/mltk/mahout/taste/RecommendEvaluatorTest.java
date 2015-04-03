package test.mltk.mahout.taste;

import org.junit.Test;
import org.mltk.mahout.model.TasteEvalScore;
import org.mltk.mahout.taste.RecommendEvaluator;
import org.mltk.mahout.taste.impl.NearestUserNeighborhoodRecommendImpl;
import org.mltk.mahout.taste.impl.SlopeOneRecommendImpl;
import org.mltk.mahout.util.DataModelUtil;

public class RecommendEvaluatorTest {

	@Test
	public void testNearestUserNeighborhoodEvaluator() {
		String filePath = ".\\file\\recom\\ua.base";

		NearestUserNeighborhoodRecommendImpl testObj = new NearestUserNeighborhoodRecommendImpl(
				3, true);
		testObj.setModel(DataModelUtil.loadDataModelFromFile(filePath));

		TasteEvalScore tasteEvalScore = RecommendEvaluator.recommenderEvalProd(
				testObj.getModel(), testObj, 2, 0.8, true);

		System.out
				.println(testObj.getModel() + "\n" + testObj.getRecommender());
		System.out.println(tasteEvalScore.toString());
	}

	@Test
	public void testSlopeOneEvaluator() {
		String filePath = ".\\file\\recom\\intro.csv";

		SlopeOneRecommendImpl testObj = new SlopeOneRecommendImpl();
		testObj.setModel(DataModelUtil.loadDataModelFromFile(filePath));

		TasteEvalScore tasteEvalScore = RecommendEvaluator.recommenderEvalProd(
				testObj.getModel(), testObj, 2, 0.8, true);

		System.out
				.println(testObj.getModel() + "\n" + testObj.getRecommender());
		System.out.println(tasteEvalScore.toString());
	}

}

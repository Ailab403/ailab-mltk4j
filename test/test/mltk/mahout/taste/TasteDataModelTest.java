package test.mltk.mahout.taste;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.junit.Test;
import org.mltk.mahout.taste.impl.NearestUserNeighborhoodRecommendImpl;
import org.mltk.mahout.taste.impl.SlopeOneRecommendImpl;
import org.mltk.mahout.util.DataModelUtil;

public class TasteDataModelTest {

	@Test
	public void testNearestUserNeighborhoodRec() {
		String filePath = ".\\file\\recom\\ua.base";

		NearestUserNeighborhoodRecommendImpl testObj = new NearestUserNeighborhoodRecommendImpl(
				1, true);
		testObj.setModel(DataModelUtil.loadDataModelFromFile(filePath));
		List<RecommendedItem> recItems = testObj.exec(4, 10);
		for (RecommendedItem recommendedItem : recItems) {
			System.out.println(recommendedItem.getItemID() + "->"
					+ recommendedItem.getValue());
		}
	}

	@Test
	public void testSlopeOneRec() {
		String filePath = ".\\file\\recom\\intro.csv";

		SlopeOneRecommendImpl testObj = new SlopeOneRecommendImpl();
		testObj.setModel(DataModelUtil.loadDataModelFromFile(filePath));
		List<RecommendedItem> recItems = testObj.exec(4, 10);
		for (RecommendedItem recommendedItem : recItems) {
			System.out.println(recommendedItem.getItemID() + "->"
					+ recommendedItem.getValue());
		}
	}
}

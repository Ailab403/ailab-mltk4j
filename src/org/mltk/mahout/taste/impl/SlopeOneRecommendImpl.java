package org.mltk.mahout.taste.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.mltk.mahout.taste.RecommendDataModel;

/**
 * 
 * @author superhy
 * 
 *         一键式推荐引擎实现类
 * 
 */
public class SlopeOneRecommendImpl extends RecommendDataModel {

	public SlopeOneRecommendImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SlopeOneRecommendImpl(DataModel model, Recommender recommender) {
		super(model, recommender);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 生成推荐引擎
	 * 
	 * @param dataModel
	 */
	public Recommender recommenderProd(DataModel dataModel) {

		try {

			// 生成推荐引擎
			Recommender recommender = new SlopeOneRecommender(dataModel);

			return recommender;
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 执行推荐引擎，并获得推荐结果
	 * 
	 * @param userNo
	 * @param itemNum
	 * @return
	 */
	public List<RecommendedItem> exec(int userNo, int itemNum) {

		List<RecommendedItem> recItemList = new ArrayList<RecommendedItem>();

		try {

			// 需要提前载入dataModel
			// 检查dataModel是否成功载入，如果没有成功载入，提前退出引擎
			if (super.model == null) {
				System.err.println("dataModel丢失或者载入失败，请检查！");
				return null;
			}
			// 生成推荐引擎
			Recommender recommender = this.recommenderProd(super.model);
			super.setRecommender(recommender);

			// 刷新推荐器
			this.recommender.refresh(null);

			// 为指定用户，推荐制定条目
			recItemList = this.recommender.recommend(userNo, itemNum);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return recItemList;
	}

}

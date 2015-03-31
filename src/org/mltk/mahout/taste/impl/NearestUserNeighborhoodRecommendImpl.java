package org.mltk.mahout.taste.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.mltk.mahout.taste.DataSimilarityFactory;
import org.mltk.mahout.taste.RecommendDataModel;

/**
 * 
 * @author superhy
 * 
 *         运行基于用户的推荐引擎，需要传入相似度计算方法对应编号， 还需传入是否为相似度计算引入缓存机制
 * 
 */
public class NearestUserNeighborhoodRecommendImpl extends RecommendDataModel {

	private Integer simTypeNum;
	private Boolean flagCachingSim;

	public NearestUserNeighborhoodRecommendImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NearestUserNeighborhoodRecommendImpl(DataModel model,
			Recommender recommender) {
		super(model, recommender);
		// TODO Auto-generated constructor stub
	}

	public NearestUserNeighborhoodRecommendImpl(Integer simTypeNum,
			Boolean flagCachingSim) {
		super();
		this.simTypeNum = simTypeNum;
		this.flagCachingSim = flagCachingSim;
	}

	public NearestUserNeighborhoodRecommendImpl(DataModel model,
			Recommender recommender, Integer simTypeNum, Boolean flagCachingSim) {
		super(model, recommender);
		this.simTypeNum = simTypeNum;
		this.flagCachingSim = flagCachingSim;
	}

	/**
	 * 生成推荐引擎
	 * 
	 * @param dataModel
	 */
	public Recommender recommenderProd(DataModel dataModel) {

		try {

			/*
			 * 获得相似度计算结果实体
			 */

			if (this.flagCachingSim == null) {
				this.flagCachingSim = false;
			}
			UserSimilarity userSim = null;

			if (this.flagCachingSim) {
				// 为相似度计算引入缓存机制
				userSim = new CachingUserSimilarity(
						DataSimilarityFactory.prodUserSimilarity(dataModel,
								this.simTypeNum), dataModel);
			} else {
				// 直接进行相似度计算
				userSim = DataSimilarityFactory.prodUserSimilarity(dataModel,
						this.simTypeNum);
			}

			// 前面的数字是邻居集合大小
			UserNeighborhood userNbh = new NearestNUserNeighborhood(20,
					userSim, dataModel);

			// 生成推荐引擎
			Recommender recommender = new GenericUserBasedRecommender(dataModel,
					userNbh, userSim);

			return recommender;
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * 执行推荐引擎，并获得推荐结果
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

	public Integer getSimTypeNum() {
		return simTypeNum;
	}

	public void setSimTypeNum(Integer simTypeNum) {
		this.simTypeNum = simTypeNum;
	}

	public Boolean getFlagCachingSim() {
		return flagCachingSim;
	}

	public void setFlagCachingSim(Boolean flagCachingSim) {
		this.flagCachingSim = flagCachingSim;
	}

}

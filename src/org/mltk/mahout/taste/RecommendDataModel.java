package org.mltk.mahout.taste;

import java.util.List;

import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

public abstract class RecommendDataModel {

	// 推荐数据集，需要使用DataModel类中的静态方法提前载入
	protected DataModel model;
	protected Recommender recommender;

	public RecommendDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecommendDataModel(DataModel model, Recommender recommender) {
		super();
		this.model = model;
		this.recommender = recommender;
	}

	/**
	 * 生成推荐引擎，抽象类方法
	 * 
	 * @param dataModel
	 */
	public Recommender recommenderProd(DataModel dataModel) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 执行推荐引擎，并获得推荐结果，抽象类方法
	 * 
	 * @param userNo
	 * @param itemNum
	 * @return
	 */
	public List<RecommendedItem> exec(int userNo, int itemNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataModel getModel() {
		return model;
	}

	public void setModel(DataModel model) {
		this.model = model;
	}

	public Recommender getRecommender() {
		return recommender;
	}

	public void setRecommender(Recommender recommender) {
		this.recommender = recommender;
	}

}

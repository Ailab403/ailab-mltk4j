package org.mltk.task.t_mcmf;

import java.util.List;
import java.util.Map;

import org.mltk.task.t_mcmf.model.LdaTopic;
import org.mltk.task.t_mcmf.model.LdaWord;

public class CrossDomainDiffSpace {

	/**
	 * sWord-tWord cost arc
	 * 
	 * @author superhy
	 *
	 */
	class klArc {
		LdaWord sWord;
		LdaWord tWord;
		double arcCost;

		public klArc(LdaWord sWord, LdaWord tWord, double arcCost) {
			super();
			this.sWord = sWord;
			this.tWord = tWord;
			this.arcCost = arcCost;
		}

		@Override
		public String toString() {
			return "klArc [sWord=" + sWord + ", tWord=" + tWord + ", arcCost="
					+ arcCost + "]";
		}
	}

	private List<klArc> klArcs;

	public CrossDomainDiffSpace() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CrossDomainDiffSpace(List<klArc> klArcs) {
		super();
		this.klArcs = klArcs;
	}

	public List<klArc> getKlArcs() {
		return klArcs;
	}

	public void setKlArcs(List<klArc> klArcs) {
		this.klArcs = klArcs;
	}

	@Override
	public String toString() {
		return "CrossDomainDiffSpace [klArcs=" + klArcs + "]";
	}

	/**
	 * 从主题层节点上转移kl散度到特征层节点上
	 * 
	 * @param sTopic
	 * @param tTopic
	 * @param kl
	 */
	public void transKltoWords(List<LdaWord> sWords, List<LdaWord> tWords) {

		System.out.println("正在转移网络代价值。。。");

		// kl散度计算工具类
		KLDivergence klDivergence = new KLDivergence();

		for (LdaWord sWord : sWords) {
			for (LdaWord tWord : tWords) {

				double S_kl = 0.0;
				for (Map<LdaTopic, Double> sBelongTopic : sWord
						.getBelongTopics()) {
					for (Map<LdaTopic, Double> tBelongTopic : tWord
							.getBelongTopics()) {
						// 遍历所属主题集合

						LdaTopic sTopic = sBelongTopic.keySet().iterator()
								.next();
						LdaTopic tTopic = tBelongTopic.keySet().iterator()
								.next();
						double sBelongProb = sBelongTopic.values().iterator()
								.next();
						double tBelongProb = tBelongTopic.values().iterator()
								.next();

						double d_kl = klDivergence.compTopicsKL(
								sTopic.getGenerateWords(),
								tTopic.getGenerateWords());
						S_kl += (d_kl * sBelongProb * tBelongProb);
					}
				}

				// 归一化
				double s_kl = S_kl
						/ (sWord.getBelongTopics().size() * tWord
								.getBelongTopics().size());

				// 加入费用弧集合
				this.klArcs.add(new klArc(sWord, tWord, s_kl));
			}
		}
	}
}

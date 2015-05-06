package org.mltk.task.t_mcmf;

import java.util.List;

import org.mltk.hankcs.lda.model.LdaWord;

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
		
		for (LdaWord sWord : sWords) {
			for (LdaWord tWord : tWords) {
				
				double S_kl = 0.0;
				
			}
		}
	}

}

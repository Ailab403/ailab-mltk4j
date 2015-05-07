package org.mltk.task.t_mcmf;

import java.util.ArrayList;
import java.util.List;

import org.mltk.task.t_mcmf.model.LdaGraph;
import org.mltk.task.t_mcmf.model.LdaTopic;

public class CrossDomainDiffSpace {

	/**
	 * sWord-tWord cost arc
	 * 
	 * @author superhy
	 *
	 */
	class klArc {
		public LdaTopic sWord;
		public LdaTopic tWord;
		public double arcCost;

		public klArc(LdaTopic sWord, LdaTopic tWord, double arcCost) {
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

	private List<klArc> klArcs = new ArrayList<CrossDomainDiffSpace.klArc>();

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
	 * 从主题层节点上建立kl散度跨领域空间
	 * 
	 * @param sTopic
	 * @param tTopic
	 * @param kl
	 */
	public void buildSpace(LdaGraph sLdaGraph, LdaGraph tLdaGraph) {

		System.out.println("正在计算网络代价值...");

		// kl散度计算工具类
		KLDivergence klDivergence = new KLDivergence();

		for (LdaTopic sTopic : sLdaGraph.allTopics) {
			for (LdaTopic tTopic : tLdaGraph.allTopics) {
				double arcCost = klDivergence.compTopicsGenKL(
						sTopic.generateWords, tTopic.generateWords);
				this.klArcs.add(new klArc(sTopic, tTopic, arcCost));
			}
		}
	}
}

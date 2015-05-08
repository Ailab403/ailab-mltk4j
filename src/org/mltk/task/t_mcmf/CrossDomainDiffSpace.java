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
		public LdaTopic sTopic;
		public LdaTopic tTopic;
		public double arcCost;

		public klArc(LdaTopic sTopic, LdaTopic tTopic, double arcCost) {
			super();
			this.sTopic = sTopic;
			this.tTopic = tTopic;
			this.arcCost = arcCost;
		}

		@Override
		public String toString() {
			return "klArc [sTopic=" + sTopic + ", tTopic=" + tTopic
					+ ", arcCost=" + arcCost + "]";
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

		for (LdaTopic sTopic : sLdaGraph.allTopics) {
			for (LdaTopic tTopic : tLdaGraph.allTopics) {
				double arcCost = KLDivergence.compTopicsGenKL(
						sTopic.generateWords, tTopic.generateWords);
				this.klArcs.add(new klArc(sTopic, tTopic, arcCost));
			}
		}
	}
}

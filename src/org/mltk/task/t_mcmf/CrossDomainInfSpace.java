package org.mltk.task.t_mcmf;

import java.util.ArrayList;
import java.util.List;

import org.mltk.task.t_mcmf.model.LdaGraph;
import org.mltk.task.t_mcmf.model.LdaTopic;

public class CrossDomainInfSpace {

	/**
	 * sWord-tWord weight arc
	 * 
	 * @author superhy
	 *
	 */
	class miArc {
		LdaTopic sWord;
		LdaTopic tWord;
		double arcWeight;

		public miArc(LdaTopic sWord, LdaTopic tWord, double arcWeight) {
			super();
			this.sWord = sWord;
			this.tWord = tWord;
			this.arcWeight = arcWeight;
		}

		@Override
		public String toString() {
			return "miArc [sWord=" + sWord + ", tWord=" + tWord
					+ ", arcWeight=" + arcWeight + "]";
		}
	}

	private List<miArc> miArcs = new ArrayList<CrossDomainInfSpace.miArc>();

	public CrossDomainInfSpace() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CrossDomainInfSpace(List<miArc> miArcs) {
		super();
		this.miArcs = miArcs;
	}

	public List<miArc> getMiArcs() {
		return miArcs;
	}

	public void setMiArcs(List<miArc> miArcs) {
		this.miArcs = miArcs;
	}

	@Override
	public String toString() {
		return "CrossDomainInfSpace [miArcs=" + miArcs + "]";
	}

	/**
	 * 从主题节点上建立互信息跨领域空间
	 * 
	 * @param sLdaGraph
	 * @param tLdaGraph
	 */
	public void buildSpace(LdaGraph sLdaGraph, LdaGraph tLdaGraph) {

		for (LdaTopic sTopic : sLdaGraph.allTopics) {
			for (LdaTopic tTopic : tLdaGraph.allTopics) {
				double arcWeight = MI.compTopicsMI(sTopic, tTopic);
				this.miArcs.add(new miArc(sTopic, tTopic, arcWeight));
			}
		}
	}

}

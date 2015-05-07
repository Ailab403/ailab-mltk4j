package org.mltk.task.t_mcmf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.mltk.task.t_mcmf.model.LdaDoc;
import org.mltk.task.t_mcmf.model.LdaGraph;
import org.mltk.task.t_mcmf.model.LdaTopic;
import org.mltk.task.t_mcmf.model.LdaWord;

public class TopicHierarchicalSpace {

	/**
	 * doc-topic weight arc
	 * 
	 * @author superhy
	 *
	 */
	class dtArc {
		LdaDoc ldaDoc;
		LdaTopic ldaTopic;
		double arcWeight;

		public dtArc(LdaDoc ldaDoc, LdaTopic ldaTopic, double arcWeight) {
			super();
			this.ldaDoc = ldaDoc;
			this.ldaTopic = ldaTopic;
			this.arcWeight = arcWeight;
		}

		@Override
		public String toString() {
			return "dtArc [ldaDoc=" + ldaDoc + ", ldaTopic=" + ldaTopic
					+ ", arcWeight=" + arcWeight + "]";
		}
	}

	/**
	 * topic-word weight arc
	 * 
	 * @author superhy
	 *
	 */
	class twArc {
		LdaTopic ldaTopic;
		LdaWord ldaWord;
		double arcWeight;

		@Override
		public String toString() {
			return "twArc [ldaTopic=" + ldaTopic + ", ldaWord=" + ldaWord
					+ ", arcWeight=" + arcWeight + "]";
		}
	}

	private List<dtArc> dtArcs = new ArrayList<TopicHierarchicalSpace.dtArc>();
	private List<dtArc> tdArcs = new ArrayList<TopicHierarchicalSpace.dtArc>();

	private List<twArc> twArcs = new ArrayList<TopicHierarchicalSpace.twArc>();

	public TopicHierarchicalSpace() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TopicHierarchicalSpace(List<dtArc> dtArcs, List<dtArc> tdArcs,
			List<twArc> twArcs) {
		super();
		this.dtArcs = dtArcs;
		this.tdArcs = tdArcs;
		this.twArcs = twArcs;
	}

	public List<dtArc> getDtArcs() {
		return dtArcs;
	}

	public void setDtArcs(List<dtArc> dtArcs) {
		this.dtArcs = dtArcs;
	}

	/**
	 * @return the tdArcs
	 */
	public List<dtArc> getTdArcs() {
		return tdArcs;
	}

	/**
	 * @param tdArcs
	 *            the tdArcs to set
	 */
	public void setTdArcs(List<dtArc> tdArcs) {
		this.tdArcs = tdArcs;
	}

	public List<twArc> getTwArcs() {
		return twArcs;
	}

	public void setTwArcs(List<twArc> twArcs) {
		this.twArcs = twArcs;
	}

	@Override
	public String toString() {
		return "TopicHierarchicalSpace [dtArcs=" + dtArcs + ", twArcs="
				+ twArcs + "]";
	}

	/**
	 * 建立源领域内部空间
	 * 
	 * @param sLdaGraph
	 */
	public void buildSourceSpace(LdaGraph sLdaGraph) {

		for (LdaDoc sDoc : sLdaGraph.allDocs) {
			for (Entry<Integer, Double> sGenTopic : sDoc.generateTopics
					.entrySet()) {
				int topicId = sGenTopic.getKey();
				double topicProb = sGenTopic.getValue();
				for (LdaTopic sTopic : sLdaGraph.allTopics) {
					if (topicId == sTopic.topicLdaId) {
						this.dtArcs.add(new dtArc(sDoc, sTopic, topicProb));
					}
				}
			}
		}
	}

	/**
	 * 建立目标领域内部空间
	 * 
	 * @param tLdaGraph
	 */
	public void buildTagetSpace(LdaGraph tLdaGraph) {

		for (LdaDoc sDoc : tLdaGraph.allDocs) {
			for (Entry<Integer, Double> sGenTopic : sDoc.generateTopics
					.entrySet()) {
				int topicId = sGenTopic.getKey();
				double topicProb = sGenTopic.getValue();
				for (LdaTopic sTopic : tLdaGraph.allTopics) {
					if (topicId == sTopic.topicLdaId) {
						this.tdArcs.add(new dtArc(sDoc, sTopic, topicProb));
					}
				}
			}
		}
	}
}

package org.mltk.task.t_mcmf;

import java.util.List;

import org.mltk.hankcs.lda.model.LdaDoc;
import org.mltk.hankcs.lda.model.LdaTopic;
import org.mltk.hankcs.lda.model.LdaWord;

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

	private List<dtArc> dtArcs;
	private List<twArc> twArcs;

	public TopicHierarchicalSpace() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TopicHierarchicalSpace(List<dtArc> dtArcs, List<twArc> twArcs) {
		super();
		this.dtArcs = dtArcs;
		this.twArcs = twArcs;
	}

	public List<dtArc> getDtArcs() {
		return dtArcs;
	}

	public void setDtArcs(List<dtArc> dtArcs) {
		this.dtArcs = dtArcs;
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

}

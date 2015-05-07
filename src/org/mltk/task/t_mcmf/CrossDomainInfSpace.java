package org.mltk.task.t_mcmf;

import java.util.List;

import org.mltk.task.t_mcmf.model.LdaTopic;
import org.mltk.task.t_mcmf.model.LdaWord;

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

	private List<miArc> miArcs;

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

}

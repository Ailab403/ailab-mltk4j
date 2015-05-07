package org.mltk.task.t_mcmf;

import java.util.ArrayList;
import java.util.List;

import org.mltk.task.t_mcmf.model.LdaDoc;
import org.mltk.task.t_mcmf.model.LdaGraph;
import org.mltk.task.t_mcmf.model.LdaTopic;

public class NetworkFlowGraph {

	private TopicHierarchicalSpace topicHierarchicalSpace;
	private CrossDomainInfSpace crossDomainInfSpace;
	private CrossDomainDiffSpace crossDomainDiffSpace;

	private List<Integer> sDocPoints = new ArrayList<Integer>();
	private List<Integer> sTopicPoints = new ArrayList<Integer>();
	private List<Integer> tDocPoints = new ArrayList<Integer>();
	private List<Integer> tTopicPoints = new ArrayList<Integer>();

	private LdaGraph sGraph;
	private LdaGraph tGraph;

	public NetworkFlowGraph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NetworkFlowGraph(LdaGraph sGraph, LdaGraph tGraph) {
		super();
		this.sGraph = sGraph;
		this.tGraph = tGraph;
	}

	/**
	 * build cross domain cost space
	 * 
	 * @param sGraph
	 * @param tGraph
	 */
	public void buildCrsDomainDSpace(LdaGraph sGraph, LdaGraph tGraph) {

		CrossDomainDiffSpace crossDomainDiffSpace = new CrossDomainDiffSpace();
		crossDomainDiffSpace.buildSpace(sGraph, tGraph);

		setCrossDomainDiffSpace(crossDomainDiffSpace);
	}

	/**
	 * build cross domain volume space
	 * 
	 * @param sGraph
	 * @param tGraph
	 */
	public void buildCrsDomainISpace(LdaGraph sGraph, LdaGraph tGraph) {

		CrossDomainInfSpace crossDomainInfSpace = new CrossDomainInfSpace();
		crossDomainInfSpace.buildSpace(sGraph, tGraph);

		setCrossDomainInfSpace(crossDomainInfSpace);
	}

	/**
	 * build domain inner volume space
	 * 
	 * @param sGraph
	 * @param tGraph
	 */
	public void buildHierarchicalSpace(LdaGraph sGraph, LdaGraph tGraph) {

		TopicHierarchicalSpace topicHierarchicalSpace = new TopicHierarchicalSpace();
		topicHierarchicalSpace.buildSourceSpace(sGraph);
		topicHierarchicalSpace.buildTagetSpace(tGraph);

		setTopicHierarchicalSpace(topicHierarchicalSpace);
	}

	/**
	 * init points number
	 * 
	 * @param sGraph
	 * @param tGraph
	 */
	public void initGraphPoints(LdaGraph sGraph, LdaGraph tGraph) {

		List<Integer> sDocPoints = new ArrayList<Integer>();
		List<Integer> sTopicPoints = new ArrayList<Integer>();
		for (LdaDoc sDoc : sGraph.allDocs) {
			sDocPoints.add(sDoc.docLdaId);
		}
		for (LdaTopic sTopic : sGraph.allTopics) {
			tTopicPoints.add(sTopic.topicLdaId);
		}

		List<Integer> tDocPoints = new ArrayList<Integer>();
		List<Integer> tTopicPoints = new ArrayList<Integer>();
		for (LdaDoc tDoc : tGraph.allDocs) {
			tDocPoints.add(tDoc.docLdaId);
		}
		for (LdaTopic tTopic : tGraph.allTopics) {
			tTopicPoints.add(tTopic.topicLdaId);
		}

		setsDocPoints(sDocPoints);
		setsTopicPoints(sTopicPoints);
		settDocPoints(tDocPoints);
		settTopicPoints(tTopicPoints);

	}

	/**
	 * ≥ı ºªØ’˚Õº
	 */
	public void initNetworkFlowGraph() {

		this.buildCrsDomainDSpace(this.sGraph, this.tGraph);
		this.buildCrsDomainISpace(this.sGraph, this.tGraph);
		this.buildHierarchicalSpace(this.sGraph, this.tGraph);

		this.initGraphPoints(this.sGraph, this.tGraph);
	}

	/**
	 * @return the topicHierarchicalSpace
	 */
	public TopicHierarchicalSpace getTopicHierarchicalSpace() {
		return topicHierarchicalSpace;
	}

	/**
	 * @param topicHierarchicalSpace
	 *            the topicHierarchicalSpace to set
	 */
	public void setTopicHierarchicalSpace(
			TopicHierarchicalSpace topicHierarchicalSpace) {
		this.topicHierarchicalSpace = topicHierarchicalSpace;
	}

	/**
	 * @return the crossDomainInfSpace
	 */
	public CrossDomainInfSpace getCrossDomainInfSpace() {
		return crossDomainInfSpace;
	}

	/**
	 * @param crossDomainInfSpace
	 *            the crossDomainInfSpace to set
	 */
	public void setCrossDomainInfSpace(CrossDomainInfSpace crossDomainInfSpace) {
		this.crossDomainInfSpace = crossDomainInfSpace;
	}

	/**
	 * @return the crossDomainDiffSpace
	 */
	public CrossDomainDiffSpace getCrossDomainDiffSpace() {
		return crossDomainDiffSpace;
	}

	/**
	 * @param crossDomainDiffSpace
	 *            the crossDomainDiffSpace to set
	 */
	public void setCrossDomainDiffSpace(
			CrossDomainDiffSpace crossDomainDiffSpace) {
		this.crossDomainDiffSpace = crossDomainDiffSpace;
	}

	/**
	 * @return the sDocPoints
	 */
	public List<Integer> getsDocPoints() {
		return sDocPoints;
	}

	/**
	 * @param sDocPoints
	 *            the sDocPoints to set
	 */
	public void setsDocPoints(List<Integer> sDocPoints) {
		this.sDocPoints = sDocPoints;
	}

	/**
	 * @return the sTopicPoints
	 */
	public List<Integer> getsTopicPoints() {
		return sTopicPoints;
	}

	/**
	 * @param sTopicPoints
	 *            the sTopicPoints to set
	 */
	public void setsTopicPoints(List<Integer> sTopicPoints) {
		this.sTopicPoints = sTopicPoints;
	}

	/**
	 * @return the tDocPoints
	 */
	public List<Integer> gettDocPoints() {
		return tDocPoints;
	}

	/**
	 * @param tDocPoints
	 *            the tDocPoints to set
	 */
	public void settDocPoints(List<Integer> tDocPoints) {
		this.tDocPoints = tDocPoints;
	}

	/**
	 * @return the tTopicPoints
	 */
	public List<Integer> gettTopicPoints() {
		return tTopicPoints;
	}

	/**
	 * @param tTopicPoints
	 *            the tTopicPoints to set
	 */
	public void settTopicPoints(List<Integer> tTopicPoints) {
		this.tTopicPoints = tTopicPoints;
	}

}

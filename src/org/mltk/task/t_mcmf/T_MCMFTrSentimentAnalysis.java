package org.mltk.task.t_mcmf;

import java.util.List;

/**
 * 
 * @author superhy
 *
 */
public class T_MCMFTrSentimentAnalysis {

	public void trSentimentAnalysis(NetworkFlowGraph networkFlowGraph) {

		// 统计点数和边数
		int n = networkFlowGraph.getsDocPoints().size()
				+ networkFlowGraph.getsTopicPoints().size()
				+ networkFlowGraph.gettDocPoints().size()
				+ networkFlowGraph.gettTopicPoints().size();
		int m = networkFlowGraph.getCrossDomainDiffSpace().getKlArcs().size()
				+ networkFlowGraph.getCrossDomainInfSpace().getMiArcs().size()
				+ networkFlowGraph.getTopicHierarchicalSpace().getDtArcs()
						.size()
				+ networkFlowGraph.getTopicHierarchicalSpace().getTdArcs()
						.size();

		CrossDomainDiffSpace crossDomainDiffSpace = networkFlowGraph
				.getCrossDomainDiffSpace();
		CrossDomainInfSpace crossDomainInfSpace = networkFlowGraph
				.getCrossDomainInfSpace();
		TopicHierarchicalSpace topicHierarchicalSpace = networkFlowGraph
				.getTopicHierarchicalSpace();
		List<Integer> sDocPoints = networkFlowGraph.getsDocPoints();
		List<Integer> sTopicPoints = networkFlowGraph.getsTopicPoints();
		List<Integer> tDocPoints = networkFlowGraph.gettDocPoints();
		List<Integer> tTopicPoints = networkFlowGraph.gettTopicPoints();

		MinCostMaxFlow minCostMaxFlow = new MinCostMaxFlow(n, m);
		int s = 0, t = n + 1;
		for (int i = 0; i < crossDomainDiffSpace.getKlArcs().size(); i++) {

			int u = crossDomainDiffSpace.getKlArcs().get(i).sWord.topicLdaId, v = crossDomainDiffSpace
					.getKlArcs().get(i).tWord.topicLdaId; // 起始点和终点
			double volume = crossDomainInfSpace.getMiArcs().get(i).arcWeight; // 容量
			double cost = crossDomainDiffSpace.getKlArcs().get(i).arcCost; // 费用

			minCostMaxFlow.addEdge(u, v, volume, cost);
		}
		for (int i = 0; i < topicHierarchicalSpace.getDtArcs().size(); i++) {

			int u = topicHierarchicalSpace.getDtArcs().get(i).ldaDoc.docLdaId, v = topicHierarchicalSpace
					.getDtArcs().get(i).ldaTopic.topicLdaId; // 起始点和终点
			double volume = topicHierarchicalSpace.getDtArcs().get(i).arcWeight; // 容量
			double cost = 0; // 费用

			minCostMaxFlow.addEdge(u, v, volume, cost);
		}
		for (int i = 0; i < topicHierarchicalSpace.getTdArcs().size(); i++) {

			int u = topicHierarchicalSpace.getTdArcs().get(i).ldaTopic.topicLdaId, v = topicHierarchicalSpace
					.getTdArcs().get(i).ldaDoc.docLdaId; // 起始点和终点
			double volume = topicHierarchicalSpace.getTdArcs().get(i).arcWeight; // 容量
			double cost = 0; // 费用

			minCostMaxFlow.addEdge(u, v, volume, cost);
		}
		for (Integer sDocPoint : sDocPoints) {

			minCostMaxFlow.addEdge(s, sDocPoint, 1, 0);
		}
		for (Integer tDocPoint : tDocPoints) {

			minCostMaxFlow.addEdge(tDocPoint, t, 1, 0);
		}

		// 执行最小费用最大流
		minCostMaxFlow.exec();

		// TODO 检查边流量，建立VSM，写入文件

	}

}

package org.mltk.task.t_mcmf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.mltk.task.t_mcmf.model.LdaGraph;

/**
 * 
 * @author superhy
 *
 */
public class T_MCMFTrSentimentModel {

	private String trainFolderPath;
	private String testFolderPath;

	public T_MCMFTrSentimentModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public T_MCMFTrSentimentModel(String trainFolderPath,
			String testFolderPath) {
		super();
		this.trainFolderPath = trainFolderPath;
		this.testFolderPath = testFolderPath;
	}

	/**
	 * @param networkFlowGraph
	 * @param lenda
	 * @throws Exception
	 */
	public void execMinCostMaxFlow(NetworkFlowGraph networkFlowGraph,
			double lenda) throws Exception {

		System.out.println("正在建立流量网络图...");

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

		// 记录网络流模型信息
		File NetWorkModelFile = new File(
				".\\file\\sentiment\\model\\network.hy");
		if (!NetWorkModelFile.exists()) {
			NetWorkModelFile.createNewFile();
		}
		BufferedWriter bwTest = new BufferedWriter(new FileWriter(
				NetWorkModelFile));
		String NetWorkModelText = "";

		NetWorkModelText += "sDocPoints: ";
		for (Integer sDocPoint : sDocPoints) {
			NetWorkModelText += (sDocPoint + " ");
		}
		NetWorkModelText += "\r\n";
		NetWorkModelText += "sTopicPoints: ";
		for (Integer sTopicPoint : sTopicPoints) {
			NetWorkModelText += (sTopicPoint + " ");
		}
		NetWorkModelText += "\r\n";
		NetWorkModelText += "tTopicPoints: ";
		for (Integer tTopicPoint : tTopicPoints) {
			NetWorkModelText += (tTopicPoint + " ");
		}
		NetWorkModelText += "\r\n";
		NetWorkModelText += "tDocPoints: ";
		for (Integer tDocPoint : tDocPoints) {
			NetWorkModelText += (tDocPoint + " ");
		}
		NetWorkModelText += "\r\n";

		MinCostMaxFlow minCostMaxFlow = new MinCostMaxFlow(n, m);
		int s = 0, t = n + 1;

		System.out.println("st-tt:" + crossDomainDiffSpace.getKlArcs().size());

		for (int i = 0; i < crossDomainDiffSpace.getKlArcs().size(); i++) {

			int u = crossDomainDiffSpace.getKlArcs().get(i).sTopic.topicLdaId, v = crossDomainDiffSpace
					.getKlArcs().get(i).tTopic.topicLdaId; // 起始点和终点
			double volume = crossDomainInfSpace.getMiArcs().get(i).arcWeight; // 容量
			double cost = crossDomainDiffSpace.getKlArcs().get(i).arcCost; // 费用

			minCostMaxFlow.addEdge(u, v, volume, cost);

			NetWorkModelText += ("st-tt: u=" + u + " v=" + v + " volume="
					+ volume + " cost=" + cost + "\r\n");
		}

		System.out
				.println("sd-st:" + topicHierarchicalSpace.getDtArcs().size());

		for (int i = 0; i < topicHierarchicalSpace.getDtArcs().size(); i++) {

			int u = topicHierarchicalSpace.getDtArcs().get(i).ldaDoc.docLdaId, v = topicHierarchicalSpace
					.getDtArcs().get(i).ldaTopic.topicLdaId; // 起始点和终点
			double volume = topicHierarchicalSpace.getDtArcs().get(i).arcWeight; // 容量
			double cost = 0; // 费用

			minCostMaxFlow.addEdge(u, v, volume, cost);

			NetWorkModelText += ("sd-st: u=" + u + " v=" + v + " volume="
					+ volume + " cost=" + cost + "\r\n");
		}

		System.out
				.println("tt-td:" + topicHierarchicalSpace.getTdArcs().size());

		for (int i = 0; i < topicHierarchicalSpace.getTdArcs().size(); i++) {

			int u = topicHierarchicalSpace.getTdArcs().get(i).ldaTopic.topicLdaId, v = topicHierarchicalSpace
					.getTdArcs().get(i).ldaDoc.docLdaId; // 起始点和终点
			double volume = topicHierarchicalSpace.getTdArcs().get(i).arcWeight; // 容量
			double cost = 0; // 费用

			minCostMaxFlow.addEdge(u, v, volume, cost);

			NetWorkModelText += ("tt-td: u=" + u + " v=" + v + " volume="
					+ volume + " cost=" + cost + "\r\n");
		}
		for (Integer sDocPoint : sDocPoints) {

			minCostMaxFlow.addEdge(s, sDocPoint, 1, 0);

			NetWorkModelText += ("s-sd u=" + s + " v=" + sDocPoint + " volume="
					+ 1 + " cost=" + 0 + "\r\n");
		}
		for (Integer tDocPoint : tDocPoints) {

			minCostMaxFlow.addEdge(tDocPoint, t, 1, 0);

			NetWorkModelText += ("td-t u=" + tDocPoint + " v=" + t + " volume="
					+ 1 + " cost=" + 0 + "\r\n");
		}

		bwTest.write(NetWorkModelText);
		bwTest.close();

		System.out.println("正在运行最小费用最大流算法...");

		// 执行最小费用最大流
		minCostMaxFlow.exec();

		/*
		 * for (int i = 0; i < minCostMaxFlow.eCnt; i += 2) {
		 * System.out.println(minCostMaxFlow.edges[i]); }
		 */

		/*
		 * 检查边流量，建立VSM，写入文件
		 */

		System.out.println("节点数：" + sDocPoints.size() + " "
				+ sTopicPoints.size() + " " + tDocPoints.size() + " "
				+ tTopicPoints.size());
		System.out.println("正在生成model信息。。。");

		// 记录执行后流量信息
		File ArcFile = new File(".\\file\\sentiment\\model\\arc.hy");
		if (!ArcFile.exists()) {
			ArcFile.createNewFile();
		}
		BufferedWriter bwArc = new BufferedWriter(new FileWriter(ArcFile));
		String arcText = "";

		// 处理源领域训练语料
		List<String> trainFileLines = new ArrayList<String>();
		int trainNum = 1;
		for (Integer sDocPoint : sDocPoints) {
			String trainFileLine = "";
			for (Integer sTopicPoint : sTopicPoints) {

				double resFlow = 0;
				for (int i = 0; i < minCostMaxFlow.eCnt; i += 2) {
					if (sDocPoint.equals((Integer) minCostMaxFlow.edges[i].u)
							&& sTopicPoint
									.equals((Integer) minCostMaxFlow.edges[i].v)) {
						double flowRatio = minCostMaxFlow.edges[i].flow
								* 1.0
								/ (minCostMaxFlow.edges[i].volume + minCostMaxFlow.edges[i].flow);

						arcText += (minCostMaxFlow.edges[i].toString() + "\r\n");

						if (lenda <= flowRatio) {
							resFlow = minCostMaxFlow.edges[i].flow;

							// System.out.println(minCostMaxFlow.edges[i]);

							break;
						}
					}
				}
				trainFileLine += (resFlow + " ");
			}
			// change 50
			if (trainNum <= 100) {
				trainFileLine += "label:1";
			} else {
				trainFileLine += "label:0";
			}
			trainFileLines.add(trainFileLine);
			trainNum++;
		}
		List<String> testFileLines = new ArrayList<String>();
		int testNum = 1;
		for (Integer tDocPoint : tDocPoints) {
			String testFileLine = "";
			for (Integer tTopicPoint : tTopicPoints) {

				double resFlow = 0;
				for (int i = 0; i < minCostMaxFlow.eCnt; i += 2) {
					if (tTopicPoint.equals((Integer) minCostMaxFlow.edges[i].u)
							&& tDocPoint
									.equals((Integer) minCostMaxFlow.edges[i].v)) {
						double flowRatio = minCostMaxFlow.edges[i].flow
								* 1.0
								/ (minCostMaxFlow.edges[i].volume + minCostMaxFlow.edges[i].flow);

						arcText += (minCostMaxFlow.edges[i].toString() + "\r\n");

						if (lenda <= flowRatio) {
							resFlow = minCostMaxFlow.edges[i].flow;

							// System.out.println(resFlow);

							break;
						}
					}
				}
				testFileLine += (resFlow + " ");
			}
			// change 50
			/*
			 * if (testNum <= 50) { testFileLine += "label:1"; } else {
			 * testFileLine += "label:0"; }
			 */
			testFileLines.add(testFileLine);
			testNum++;
		}

		bwArc.write(arcText);
		bwArc.close();

		System.out.println("正在向磁盘中写入model...");

		// 将模型写入文件
		String trainFileText = "";
		for (String line : trainFileLines) {
			trainFileText += (line + "\r\n");
		}
		File trainModelFile = new File(".\\file\\sentiment\\model\\train.hy");
		if (!trainModelFile.exists()) {
			trainModelFile.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(trainModelFile));
		bw.write(trainFileText);
		bw.close();

		String testFileText = "";
		for (String line : testFileLines) {
			testFileText += (line + "\r\n");
		}
		File testModelFile = new File(".\\file\\sentiment\\model\\test.hy");
		if (!testModelFile.exists()) {
			testModelFile.createNewFile();
		}
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(testModelFile));
		bw2.write(testFileText);
		bw2.close();
	}

	/**
	 * 执行跨领域情感分析模型建立
	 * 
	 * @param topicNum
	 * @param wordNum
	 * @param lenda
	 */
	public void trSentimentAnalysis(int topicNum, int genWordNum, double lenda) {

		try {

			LDALevelModel sLdaLevelModel = new LDALevelModel(
					this.trainFolderPath, topicNum, genWordNum);
			LDALevelModel tLdaLevelModel = new LDALevelModel(
					this.testFolderPath, topicNum, genWordNum);
			LdaGraph sGraph = sLdaLevelModel.getLdaLevelGraph();
			LdaGraph tGraph = tLdaLevelModel.getLdaLevelGraph();
			NetworkFlowGraph networkFlowGraph = new NetworkFlowGraph(sGraph,
					tGraph);
			networkFlowGraph.initNetworkFlowGraph();
			this.execMinCostMaxFlow(networkFlowGraph, lenda);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String getTrainFolderPath() {
		return trainFolderPath;
	}

	public void setTrainFolderPath(String trainFolderPath) {
		this.trainFolderPath = trainFolderPath;
	}

	public String getTestFolderPath() {
		return testFolderPath;
	}

	public void setTestFolderPath(String testFolderPath) {
		this.testFolderPath = testFolderPath;
	}

}

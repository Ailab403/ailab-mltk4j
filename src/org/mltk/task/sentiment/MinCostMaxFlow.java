package org.mltk.task.sentiment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MinCostMaxFlow {
	private int INF;
	private int maxFlow;
	private int capacity[][];
	private double[][] cost;
	private int rescap[][]; // 残量网络流量
	private double rescost[][]; // 残量网络费用
	private int flow[][]; // 求最大流时的流量
	private boolean visited[];
	private int pre[]; // 最大流时的增广前驱
	private int[] p; // 增广路前驱
	private double[] d; // s-t路径最小耗费
	private boolean[] inq; // 队列标记
	private int curr_flow; // 当前流量
	private int nodes;
	private int src;
	private int des;
	private double minimumcost;

	public MinCostMaxFlow(int asrc, int ades, int[][] acapacity,
			double[][] acost, int anodes, int ainf) {
		this.nodes = anodes;
		src = asrc;
		des = ades;
		INF = ainf;
		minimumcost = 0;
		maxFlow = 0;
		curr_flow = 0;
		this.capacity = new int[nodes][nodes];
		this.rescap = new int[nodes][nodes];
		cost = new double[nodes][nodes];
		this.rescost = new double[nodes][nodes];
		this.flow = new int[nodes][nodes];
		this.pre = new int[nodes];
		this.p = new int[nodes];
		this.d = new double[nodes];
		this.inq = new boolean[nodes];
		this.visited = new boolean[nodes];
		for (int i = 0; i < nodes; i++) {
			pre[i] = -1;
			p[i] = -1;
		}
		for (int i = 0; i < nodes; i++) {
			for (int j = 0; j < nodes; j++) {
				rescap[i][j] = capacity[i][j] = acapacity[i][j];
				if (capacity[i][j] > 0) {
					cost[i][j] = acost[i][j];
					cost[j][i] = -acost[i][j];
				}
			}
		}
		for (int i = 0; i < nodes; i++)
			for (int j = 0; j < nodes; j++) {
				if (rescap[i][j] > 0 || i == j)
					rescost[i][j] = acost[i][j];
				else
					rescost[i][j] = INF;
			}

	}

	/*
	 * step 0：先求出最大流
	 */
	public void maxFlow() {
		for (int i = 0; i < nodes; i++)
			for (int j = 0; j < nodes; j++)
				flow[i][j] = 0;

		while (true)// find a augment path
		{
			for (int i = 0; i < nodes; i++) {
				visited[i] = false;
			}
			pre[src] = -1;

			if (!BFS()) {// the BFS
				break;
			}

			/*
			 * DFS(src,des);//DFS 44. if(!visited[des]) 45. break;
			 */

			int increment = Integer.MAX_VALUE;
			for (int i = des; pre[i] >= src; i = pre[i]) {
				// find the min flow of the path
				increment = Math.min(increment, capacity[pre[i]][i]
						- flow[pre[i]][i]);
			}

			// update the flow
			for (int i = des; pre[i] >= src; i = pre[i]) {
				flow[pre[i]][i] += increment;
				flow[i][pre[i]] -= increment;
			}
			// increase the maxFow with the increment
			maxFlow += increment;
		}
	}

	// public void DFS(int src, int des){
	// visited[src] = true;
	// for(int i = 0; i < nodes; i++){
	// if(!visited[i] && ( capacity[src][i] - flow[src][i] > 0) ){
	// pre[i] = src;//record the augment path
	// visited[i] = true;
	// DFS(i,des);
	// }
	// }
	// }
	//
	public boolean BFS() {
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(src);
		visited[src] = true;
		while (!queue.isEmpty()) {
			int node = queue.poll();
			for (int i = 0; i < nodes; i++) {
				if (!visited[i] && (capacity[node][i] - flow[node][i] > 0)) {
					queue.add(i);
					visited[i] = true;
					pre[i] = node;// record the augment path
				}
			}
		}

		return visited[des];
	}

	public void minCmaxF() {
		Queue<Integer> q = new LinkedList<Integer>();
		while (curr_flow < maxFlow) {
			for (int k = 0; k < nodes; k++)
				inq[k] = false;
			for (int i = 0; i < nodes; i++) {
				d[i] = i == src ? 0 : INF;
			}
			q.offer(src);
			inq[src] = true;
			while (!q.isEmpty()) {
				int u = q.poll();// int u=q.front(); q.pop();
				for (int v = 0; v < nodes; v++) {
					if (rescap[u][v] > 0 && d[v] > d[u] + rescost[u][v]) {
						d[v] = d[u] + rescost[u][v];
						p[v] = u;
						if (!inq[v]) {
							inq[v] = true;
							q.offer(v);
						}
					}
				}
				inq[u] = false;

				System.out.println("size " + q.size());
				if (q.size() > 0)
					System.out.println(" " + q.peek());
				System.out.println("loop in finding the shortest path to src");
			}
			for (int u = des; u != src; u = p[u]) {
				System.out.print(" " + u);
				System.out.println("loop in the print shortest path");
			}
			System.out.println();
			boolean flag = true;
			// 判断是否有负环路
			// for(int i=1; i<nodes; i++)
			// {
			//
			// if(rescap[p[i]][i]>0&&d[i] > d[p[i]] + rescost[p[i]][i])
			// {
			// flag = false;
			// break;
			// }
			// }
			int augflow = INF;
			for (int u = des; u != src; u = p[u]) {
				if (rescap[p[u]][u] < augflow) {
					augflow = rescap[p[u]][u];
				}
				System.out.println("loop in finding the augflow   " + flag);
			}
			for (int u = des; u != src; u = p[u]) {
				rescap[p[u]][u] -= augflow;
				rescap[u][p[u]] += augflow;
				System.out.println("loop in augflow");

			}
			System.out.println("current_augflow" + augflow);
			this.minimumcost += d[des] * augflow;
			curr_flow += augflow;
			System.out.println("current_flow" + curr_flow);
			for (int i = 0; i < nodes; i++) {
				for (int j = 0; j < nodes; j++) {
					if (rescap[i][j] > 0 || i == j)
						rescost[i][j] = cost[i][j];
					else
						rescost[i][j] = INF;
				}
			} // System.out.println("loop for computing the flow");
		}

	}

	public double getMiniCost() {
		return this.minimumcost;
	}

	public int getMaxFlow() {
		return this.maxFlow;
	}

	public static void main(String[] args) {

		int nodes = 50;
		int src = 0;
		int des = nodes - 1;
		int INF = 1000;
		Random r = new Random();
		int[][] capacity = new int[nodes][nodes]; // {{0,3,4,0},{0,0,1,4},{0,0,0,5},{0,0,0,0}};
		double[][] cost = new double[nodes][nodes];// {{0,-1.2,-2.1,0},{0,0,-2.8,-5.1},{0,0,0,-1.7},{0,0,0,0}};
		for (int i = 0; i < nodes; i++) {
			for (int j = i + 1; j < nodes; j++) {
				if (r.nextDouble() < 0.3) {
					capacity[i][j] = r.nextInt(5) + 1;
					cost[i][j] = r.nextDouble() * 5;
				}
			}
		}
		System.out.println("the programe is running...");
		MinCostMaxFlow mincost_maxflow = new MinCostMaxFlow(src, des, capacity,
				cost, nodes, INF);
		mincost_maxflow.maxFlow(); // 先计算该网络中的最大可行流
		System.out.println("最大流: " + mincost_maxflow.getMaxFlow());
		mincost_maxflow.minCmaxF();
		System.out.println("最小费用" + mincost_maxflow.getMiniCost());
		System.out.println("The programe has finished");
	}

}
package org.mltk.task.t_mcmf;

import java.util.Scanner;

/**
 * 
 * @author superhy
 *
 */
public class MinCostMaxFlow {

	static double INF = 0xffffff * 1.0;
	static int MAXN = 50000;
	static int MAXM = 1000000;

	// 定义边
	public class Edge {

		int u, v; // 起始点和终点
		double volume; // 容量
		double flow; // 流量
		double cost; // 费用
		int next; // 后向邻接弧
		int re; // 逆边的下标

		public Edge() {
			super();
			// TODO Auto-generated constructor stub

			this.flow = 0;
		}

		@Override
		public String toString() {
			return "Edge [u=" + u + ", v=" + v + ", volume=" + volume
					+ ", flow=" + flow + ", cost=" + cost + ", next=" + next
					+ ", re=" + re + "]";
		}

	}

	// 边的集合
	Edge[] edges;
	// 点数量，弧数量，以及最终流量大小
	int n, m;
	double finalCost;
	// 边的计数器
	int eCnt;
	// 边的标记下标
	int[] edgeHead;
	// 计算参数
	int[] que, pre = new int[MAXN];
	double[] dis = new double[MAXN];
	// 访问标记
	boolean[] vis = new boolean[MAXN];
	// 源点，汇点
	int s, t;

	public MinCostMaxFlow(int n, int m) {
		super();
		// TODO Auto-generated constructor stub

		this.edges = new Edge[MAXM];
		this.n = n;
		this.m = m;
		this.edgeHead = new int[MAXM];
		this.que = new int[MAXN];
		this.pre = new int[MAXN];
		this.dis = new double[MAXN];
		this.vis = new boolean[MAXN];
		this.s = 0;
		this.t = this.n + 1;

		// init
		finalCost = 0;

		eCnt = 0;
		for (int i = 0; i < MAXM; i++) {
			edgeHead[i] = -1;
		}
	}

	/**
	 * 添加弧
	 * 
	 * @param u
	 * @param v
	 * @param volume
	 * @param cost
	 */
	public void addEdge(int u, int v, double volume, double cost) {

		this.edges[eCnt] = new Edge();
		// 正向弧
		this.edges[eCnt].u = u;
		this.edges[eCnt].v = v;
		this.edges[eCnt].volume = volume;
		this.edges[eCnt].cost = cost;
		this.edges[eCnt].next = edgeHead[u];
		this.edges[eCnt].re = eCnt + 1;
		this.edgeHead[u] = eCnt++;

		// System.out.println(this.edges[eCnt - 1]);

		this.edges[eCnt] = new Edge();
		// 逆向弧
		this.edges[eCnt].v = u;
		this.edges[eCnt].u = v;
		this.edges[eCnt].volume = 0;
		this.edges[eCnt].cost = -cost;
		this.edges[eCnt].next = edgeHead[v];
		this.edges[eCnt].re = eCnt - 1;
		this.edgeHead[v] = eCnt++;

		// System.out.println(this.edges[eCnt - 1]);
	}

	/**
	 * 最短路径算法寻找增广路
	 * 
	 * @return
	 */
	public boolean spfa() { // 源点为0，汇点为n+1

		int i, head = 0, tail = 1;
		for (i = 0; i <= t; i++) {
			dis[i] = INF;
			vis[i] = false;
		}
		dis[0] = 0;
		que[0] = 0;

		vis[0] = true;
		while (head < tail) { // 采用广度优先搜索搜索增广路径
			int u = que[head++];

			// System.out.println(edgeHead[u]);

			for (i = edgeHead[u]; i != -1; i = edges[i].next) {
				/*
				 * System.out.println(i); System.out.println(edges[i]);
				 */
				int v = edges[i].v;
				if (edges[i].volume != 0 && dis[v] > dis[u] + edges[i].cost) {
					dis[v] = dis[u] + edges[i].cost;
					pre[v] = i;
					if (vis[v] == false) {
						vis[v] = true;
						que[tail++] = v;
					}
				}
			}
			vis[u] = false;
		}

		if (dis[t] == INF) {
			// System.out.println(n);
			return false;
		}

		return true;
	}

	/**
	 * 计算最小费用最大流
	 */
	public void mcmf() {

		int u, p;
		double sum = INF;
		for (u = this.t; u != this.s; u = edges[edges[p].re].v) {
			p = pre[u];
			sum = Math.min(sum, edges[p].volume);
		}
		for (u = this.t; u != this.s; u = edges[edges[p].re].v) {
			p = pre[u];
			edges[p].volume -= sum;
			edges[p].flow += sum;
			edges[edges[p].re].volume += sum;
			edges[edges[p].re].flow -= sum;
			// System.out.println(sum + " " + edges[p].cost);
			finalCost += (sum * edges[p].cost); // cost是单位流量费用

			// System.out.println("costNow:" + finalCost);
		}
	}

	/**
	 * 算法执行方法
	 */
	public void exec() {

		System.out.println("mcmf...");
		while (spfa() == true) {
			mcmf();
		}
		System.out.println("mcmf over");
	}

	/**************************** 以下为测试 ******************************/

	/**
	 * 测试主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);
		// 输入点数和边数
		int n, m;
		n = cin.nextInt();
		m = cin.nextInt();

		MinCostMaxFlow mcmf = new MinCostMaxFlow(n, m);

		// System.out.println(mcmf.n + " " + mcmf.m);

		for (int i = 0; i < mcmf.m; i++) {
			int u, v;
			double volume, cost;
			u = cin.nextInt();
			v = cin.nextInt();
			volume = cin.nextDouble();
			cost = cin.nextDouble();

			// System.out.println(u + " " + v + " " + flow + " " + cost);

			mcmf.addEdge(u, v, volume, cost);
		}
		// System.out.println(mcmf.m);

		mcmf.exec();

		System.out.println(mcmf.finalCost + "\n");

		for (int i = 0; i < mcmf.eCnt; i += 2) {
			System.out.println(mcmf.edges[i]);
		}
	}

	/*
	 * 测试数据1
	 * 
	 * 2 4 0 1 3.0 4.0 0 2 1.0 1.0 1 3 2.0 2.0 2 3 3.0 1.0
	 */

	/*
	 * 测试数据2
	 * 
	 * 5 9 0 1 3.0 4.0 0 2 1.0 1.0 0 3 1.0 2.0 1 4 5.0 10.0 1 5 2.0 2.0 2 5 3.0
	 * 1.0 3 5 4.0 2.0 4 6 1.0 3.0 5 6 3.0 4.0
	 */
}

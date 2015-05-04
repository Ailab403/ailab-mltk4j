package org.mltk.task.t_mcmf;

public class MinCostMaxFlow {

	static double INF = 0xffffff * 1.0;

	// 定义边
	public class Edge {

		int u, v; // 起始点和终点
		double flow; // 流量
		double cost; // 费用
		int next; // 后向邻接弧
		int re; // 逆边的下标
	}

	// 边的集合
	Edge[] edges;
	// 点数量，弧数量，以及最终流量大小
	int n, m, finalCost;
	// 边的计数器
	int eCnt;
	// 边的标记下标
	int[] edgeHead;
	// 计算参数
	int[] que, pre;
	double[] dis;
	// 访问标记
	boolean[] vis;

	public void addEdge(int u, int v, int flow, int cost) {
		// 正向弧
		edges[eCnt].u = u;
		edges[eCnt].v = v;
		edges[eCnt].flow = flow;
		edges[eCnt].cost = cost;
		edges[eCnt].next = edgeHead[u];
		edges[eCnt].re = eCnt + 1;
		edgeHead[u] = eCnt++;

		// 逆向弧
		edges[eCnt].v = u;
		edges[eCnt].u = v;
		edges[eCnt].flow = 0;
		edges[eCnt].cost = -cost;
		edges[eCnt].next = edgeHead[v];
		edges[eCnt].re = eCnt - 1;
		edgeHead[v] = eCnt++;
	}

	public boolean spfa() {
		int i, head = 0, tail = 1;
		for (i = 0; i <= n; i++) {
			dis[i] = INF;
			vis[i] = false;
		}
		dis[0] = 0;
		que[0] = 0;

		vis[0] = true;
		while (head < tail) {
			int u = que[head++];
			for (i = edgeHead[u]; i != -1; i = edges[i].next) {
				int v = edges[i].v;
				if (edges[i].flow != 0 && dis[v] > dis[u] + edges[i].cost) {
					dis[v] = dis[u] + edges[i].cost;
					pre[v] = i;
					if (vis[v] == false) {
						vis[v] = true;
						que[tail++] = v;
					}
				}
			}
		}

		return true;
	}

	public void test() {
		Edge ed = new Edge();
		ed.v = 0;

	}
}

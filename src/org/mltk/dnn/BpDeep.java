package org.mltk.dnn;

import java.util.Random;

/**
 * 
 * @author zhoujd from csdn
 *
 */

public class BpDeep {

	public double[][] layer;// 神经网络各层节点
	public double[][] layerErr;// 神经网络各节点误差
	public double[][][] layer_weight;// 各层节点权重
	public double[][][] layer_weight_delta;// 各层节点权重动量
	public double mobp;// 动量系数
	public double rate;// 学习系数

	public BpDeep(int[] layernum, double rate, double mobp) {
		this.mobp = mobp;
		this.rate = rate;
		layer = new double[layernum.length][];
		layerErr = new double[layernum.length][];
		layer_weight = new double[layernum.length][][];
		layer_weight_delta = new double[layernum.length][][];
		Random random = new Random();
		for (int l = 0; l < layernum.length; l++) {
			layer[l] = new double[layernum[l]];
			layerErr[l] = new double[layernum[l]];
			if (l + 1 < layernum.length) {
				layer_weight[l] = new double[layernum[l] + 1][layernum[l + 1]];
				layer_weight_delta[l] = new double[layernum[l] + 1][layernum[l + 1]];
				for (int j = 0; j < layernum[l] + 1; j++)
					for (int i = 0; i < layernum[l + 1]; i++)
						layer_weight[l][j][i] = random.nextDouble();// 随机初始化权重
			}
		}
	}

	// 逐层向前计算输出
	public double[] computeOut(double[] in) {
		for (int l = 1; l < layer.length; l++) {
			for (int j = 0; j < layer[l].length; j++) {
				double z = layer_weight[l - 1][layer[l - 1].length][j];
				for (int i = 0; i < layer[l - 1].length; i++) {
					layer[l - 1][i] = l == 1 ? in[i] : layer[l - 1][i];
					z += layer_weight[l - 1][i][j] * layer[l - 1][i];
				}
				layer[l][j] = 1 / (1 + Math.exp(-z));
			}
		}
		return layer[layer.length - 1];
	}

	// 逐层反向计算误差并修改权重
	public void updateWeight(double[] tar) {
		int l = layer.length - 1;
		for (int j = 0; j < layerErr[l].length; j++)
			layerErr[l][j] = layer[l][j] * (1 - layer[l][j])
					* (tar[j] - layer[l][j]);

		while (l-- > 0) {
			for (int j = 0; j < layerErr[l].length; j++) {
				double z = 0.0;
				for (int i = 0; i < layerErr[l + 1].length; i++) {
					z = z + l > 0 ? layerErr[l + 1][i] * layer_weight[l][j][i]
							: 0;
					layer_weight_delta[l][j][i] = mobp
							* layer_weight_delta[l][j][i] + rate
							* layerErr[l + 1][i] * layer[l][j];// 隐含层动量调整
					layer_weight[l][j][i] += layer_weight_delta[l][j][i];// 隐含层权重调整
					if (j == layerErr[l].length - 1) {
						layer_weight_delta[l][j + 1][i] = mobp
								* layer_weight_delta[l][j + 1][i] + rate
								* layerErr[l + 1][i];// 截距动量调整
						layer_weight[l][j + 1][i] += layer_weight_delta[l][j + 1][i];// 截距权重调整
					}
				}
				layerErr[l][j] = z * layer[l][j] * (1 - layer[l][j]);// 记录误差
			}
		}
	}

	public void train(double[] in, double[] tar) {
		double[] out = computeOut(in);
		updateWeight(tar);
	}
}

package test.mltk.dnn;

/**
 * @author zhoujd from csdn
 */

import java.util.Arrays;

import org.mltk.dnn.BpDeep;

public class BpDeepTest {

	public static void main(String[] args) {
		// 初始化神经网络的基本配置
		// 第一个参数是一个整型数组，表示神经网络的层数和每层节点数，比如{3,10,10,10,10,2}表示输入层是3个节点，输出层是2个节点，中间有4层隐含层，每层10个节点
		// 第二个参数是学习步长，第三个参数是动量系数
		BpDeep bp = new BpDeep(new int[] { 2, 10, 2 }, 0.15, 0.8);

		// 设置样本数据，对应上面的4个二维坐标数据
		double[][] data = new double[][] { { 1, 2 }, { 2, 2 }, { 1, 1 },
				{ 2, 1 } };
		// 设置目标数据，对应4个坐标数据的分类
		double[][] target = new double[][] { { 1, 0 }, { 0, 1 }, { 0, 1 },
				{ 1, 0 } };

		// 迭代训练5000次
		for (int n = 0; n < 5000; n++)
			for (int i = 0; i < data.length; i++)
				bp.train(data[i], target[i]);

		// 根据训练结果来检验样本数据
		for (int j = 0; j < data.length; j++) {
			double[] result = bp.computeOut(data[j]);
			System.out.println(Arrays.toString(data[j]) + ":"
					+ Arrays.toString(result));
		}

		// 根据训练结果来预测一条新数据的分类
		double[] x = new double[] { 3, 1 };
		double[] result = bp.computeOut(x);
		System.out.println(Arrays.toString(x) + ":" + Arrays.toString(result));
	}
}

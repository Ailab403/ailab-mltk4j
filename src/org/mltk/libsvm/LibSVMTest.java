package org.mltk.libsvm;

import java.io.IOException;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class LibSVMTest {

	public static void main(String[] args) {
		svm_problem sp = new svm_problem();
		svm_node[][] x = new svm_node[4][2];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				x[i][j] = new svm_node();
			}
		}
		x[0][0].index = 1;
		x[0][0].value = 0;
		x[0][1].index = 2;
		x[0][1].value = 0;

		x[1][0].index = 1;
		x[1][0].value = 1;
		x[1][1].index = 2;
		x[1][1].value = 1;

		x[2][0].index = 1;
		x[2][0].value = 0;
		x[2][1].index = 2;
		x[2][1].value = 1;

		x[3][0].index = 1;
		x[3][0].value = 1;
		x[3][1].value = 0;
		x[3][1].index = 2;

		double[] labels = new double[] { -1, -1, 1, 1 };
		sp.x = x;
		sp.y = labels;
		sp.l = 4;
		svm_parameter prm = new svm_parameter();
		prm.svm_type = svm_parameter.C_SVC;
		prm.kernel_type = svm_parameter.RBF;
		prm.C = 1000;
		prm.eps = 0.0000001;
		prm.gamma = 10;
		prm.probability = 1;
		prm.cache_size = 1024;
		/*
		 * svm_check_parameter 参数可行返回null，否则返回错误信息
		 */
		System.out.println("Param Check "
				+ (svm.svm_check_parameter(sp, prm) == null));
		svm_model model = svm.svm_train(sp, prm); // 训练分类
		try {
			svm.svm_save_model("svm_model_file", model);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			svm.svm_load_model("svm_model_file");
		} catch (IOException e) {
			e.printStackTrace();
		}
		svm_node[] test = new svm_node[] { new svm_node(), new svm_node() };
		test[0].index = 1;
		test[0].value = 0;
		test[1].index = 2;
		test[1].value = 0;
		double[] l = new double[2];
		double result_prob = svm.svm_predict_probability(model, test, l); // 测试1，带预测概率的分类测试
		double result_normal = svm.svm_predict(model, test); // 测试2 不带概率的分类测试
		System.out.println("Result with prob " + result_prob);
		System.out.println("Result normal " + result_normal);
		System.out.println("Probability " + model.label[0] + ": " + l[0] + "\t"
				+ model.label[1] + ": " + l[1]);
	}
}
package org.mltk.libsvm;

import libsvm.svm_parameter;

public class LibSVMParameFactory {

	// svm算法的类型，默认为c_svc
	private int svmType = svm_parameter.C_SVC;
	// 核函数的类型，默认使用径向基核函数
	private int kernelType = svm_parameter.RBF;
	// 损失函数惩罚值
	private double cost = 10;
	// 迭代的收敛条件
	private double eps = 0.00001;
	// 核函数的gamma函数值
	private double gamma = 0;
	// 等于1代表模型的分布概率已知
	private int probability = 0;
	// 缓存空间的大小，默认1G
	private double cacheSize = 1024;

	public LibSVMParameFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LibSVMParameFactory(int svmType, int kernelType, double cost, double eps,
			double gamma, int probability, double cacheSize) {
		super();
		this.svmType = svmType;
		this.kernelType = kernelType;
		this.cost = cost;
		this.eps = eps;
		this.gamma = gamma;
		this.probability = probability;
		this.cacheSize = cacheSize;
	}

	public svm_parameter prodLibSVMParam() {

		svm_parameter svmParame = new svm_parameter();
		
		svmParame.svm_type = this.svmType;
		svmParame.kernel_type = this.kernelType;
		svmParame.C = this.cost;
		svmParame.eps = this.eps;
		svmParame.gamma = this.gamma;
		svmParame.probability = this.probability;
		svmParame.cache_size = this.cacheSize;

		return svmParame;
	}

	public int getSvmType() {
		return svmType;
	}

	public void setSvmType(int svmType) {
		this.svmType = svmType;
	}

	public int getKernelType() {
		return kernelType;
	}

	public void setKernelType(int kernelType) {
		this.kernelType = kernelType;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getEps() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public double getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(double cacheSize) {
		this.cacheSize = cacheSize;
	}

	@Override
	public String toString() {
		return "LibSVMParame [svmType=" + svmType + ", kernelType="
				+ kernelType + ", cost=" + cost + ", eps=" + eps + ", gamma="
				+ gamma + ", probability=" + probability + ", cacheSize="
				+ cacheSize + "]";
	}

}

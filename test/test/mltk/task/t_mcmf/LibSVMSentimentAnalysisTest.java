package test.mltk.task.t_mcmf;

import java.util.List;

import org.ansj.util.ClassifyEvaluation;
import org.mltk.libsvm.model.ClassifyRes;
import org.mltk.task.t_mcmf.LibSVMSentimentAnalysis;

/**
 * 
 * @author superhy
 *
 */
public class LibSVMSentimentAnalysisTest {

	public static void main(String[] args) {

		LibSVMSentimentAnalysis libSVMSentimentAnalysis = new LibSVMSentimentAnalysis();
		String libsvmModelPath = "./file/sentiment/model/libsvmModel.hy";
		String trainModelPath = "./file/sentiment/model/train.hy";
		String testModelPath = "./file/sentiment/model/test.hy";

		libSVMSentimentAnalysis.trainLibsvmModel(trainModelPath,
				libsvmModelPath);
		List<ClassifyRes> classifyResList = libSVMSentimentAnalysis
				.libsvmSentimentAnalysis(testModelPath, libsvmModelPath);

		/*
		 * svm_type c_svc kernel_type rbf gamma 10.0 nr_class 2 total_sv 183 rho
		 * -0.3359877695622573 label 1 0 probA -2.988586914322829 probB
		 * 0.06357078309697942 nr_sv 97 86 SV
		 */

		int probNum = 0, normNum = 0;
		int textNum = 0;
		for (ClassifyRes classifyRes : classifyResList) {
			if ((classifyRes.getProbilityRes() == 0.0 && textNum >= ClassifyEvaluation.POS_TAG)
					|| (classifyRes.getProbilityRes() == 1.0 && textNum < ClassifyEvaluation.NEG_TAG)) {
				probNum++;
			}
			if ((classifyRes.getNormalRes() == 0.0 && textNum >= ClassifyEvaluation.POS_TAG)
					|| (classifyRes.getNormalRes() == 1.0 && textNum < ClassifyEvaluation.NEG_TAG)) {
				normNum++;
			}
			textNum++;
		}

		double probResAc = probNum * 1.0 / 200;
		double normResAc = normNum * 1.0 / 200;

		System.out.println("\nprobResAc: " + probResAc + " normResAc: "
				+ normResAc);
	}
}

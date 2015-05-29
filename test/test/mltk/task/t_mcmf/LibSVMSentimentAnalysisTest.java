package test.mltk.task.t_mcmf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libsvm.eval.ClassifyEvaluation;

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

		/**
		 * evalution
		 */

		Map<Integer, Double> evalProbRes = new HashMap<Integer, Double>();
		Map<Integer, Double> evalNormRes = new HashMap<Integer, Double>();

		int textNum = 0;

		for (ClassifyRes classifyRes : classifyResList) {
			evalProbRes.put(textNum, classifyRes.getProbilityRes());
			evalNormRes.put(textNum, classifyRes.getNormalRes());

			textNum++;
		}
		
		double probResAc = ClassifyEvaluation.parse_evalution(evalProbRes);
		double normResAc = ClassifyEvaluation.parse_evalution(evalNormRes);
		
		System.out.println("\nprobResAc: " + probResAc + " normResAc: "
				+ normResAc);
	}
}

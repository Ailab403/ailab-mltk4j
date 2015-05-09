package test.mltk.task.t_mcmf;

import java.util.List;

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
		String libsvmModelPath = ".\\file\\sentiment\\model\\libsvmModel.hy";
		String trainModelPath = ".\\file\\sentiment\\model\\train.hy";
		String testModelPath = ".\\file\\sentiment\\model\\test.hy";

		libSVMSentimentAnalysis.trainLibsvmModel(trainModelPath,
				libsvmModelPath);
		List<ClassifyRes> classifyResList = libSVMSentimentAnalysis
				.libsvmSentimentAnalysis(testModelPath, libsvmModelPath);
		int probNum = 0, normNum = 0;
		for (ClassifyRes classifyRes : classifyResList) {
			if (classifyRes.getProbilityRes() == 0.0) {
				probNum++;
			}
			if (classifyRes.getNormalRes() == 0.0) {
				normNum++;
			}
		}

		double probResAc = probNum * 1.0 / 100;
		double normResAc = normNum * 1.0 / 100;

		System.out.println("\nprobResAc: " + probResAc + " normResAc: "
				+ normResAc);
	}
}

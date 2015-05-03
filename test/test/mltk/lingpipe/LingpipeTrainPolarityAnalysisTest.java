package test.mltk.lingpipe;

import java.util.Scanner;

import org.mltk.lingpipe.polarity.LingpipeTrainPolarityAnalysis;

public class LingpipeTrainPolarityAnalysisTest {

	public static void main(String[] args) {
		int nGram = new Scanner(System.in).nextInt();

		LingpipeTrainPolarityAnalysis lingpipeTrainPolarityAnalysis = new LingpipeTrainPolarityAnalysis(
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYTRAIN_DIR,
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYCLASSIFIER_MODEL_PATH,
				nGram);

		lingpipeTrainPolarityAnalysis.trainPolarityAnalysisModel();
	}
}

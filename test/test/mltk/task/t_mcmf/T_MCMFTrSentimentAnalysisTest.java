package test.mltk.task.t_mcmf;

import org.mltk.task.t_mcmf.T_MCMFTrSentimentAnalysis;

public class T_MCMFTrSentimentAnalysisTest {

	public static void main(String[] args) {

		String TRAIN_CORPUS_PATH = ".\\file\\sentiment\\seg\\dianyingall";
		String TEST_CORPUS_PATH = ".\\file\\sentiment\\seg\\dianziall";
		int topicNum = 200;
		int genWordNum = 100;
		int lenda = 0;

		T_MCMFTrSentimentAnalysis sentimentAnalysis = new T_MCMFTrSentimentAnalysis(
				TRAIN_CORPUS_PATH, TEST_CORPUS_PATH);
		sentimentAnalysis.trSentimentAnalysis(topicNum, genWordNum, lenda);
	}
}

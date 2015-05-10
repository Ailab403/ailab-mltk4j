package test.mltk.task.t_mcmf;

import org.mltk.task.t_mcmf.T_MCMFTrSentimentModel;

/**
 * 
 * @author superhy
 *
 */
public class T_MCMFTrSentimentModelTest {

	public static void main(String[] args) {

		String TRAIN_CORPUS_PATH = ".\\file\\sentiment\\seg\\dianyingall";
		String TEST_CORPUS_PATH = ".\\file\\sentiment\\seg\\dianziall";
		int topicNum = 50;
		int genWordNum = 80;
		double gama = 0.15;

		T_MCMFTrSentimentModel sentimentAnalysis = new T_MCMFTrSentimentModel(
				TRAIN_CORPUS_PATH, TEST_CORPUS_PATH);
		sentimentAnalysis.trSentimentAnalysis(topicNum, genWordNum, gama);
	}
}

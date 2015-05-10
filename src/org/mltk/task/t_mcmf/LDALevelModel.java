package org.mltk.task.t_mcmf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mltk.task.t_mcmf.model.AllLdaItemSet;
import org.mltk.task.t_mcmf.model.LdaDoc;
import org.mltk.task.t_mcmf.model.LdaGraph;
import org.mltk.task.t_mcmf.model.LdaTopic;
import org.mltk.task.t_mcmf.model.LdaWord;

import com.hankcs.lda.Corpus;
import com.hankcs.lda.LdaGibbsSampler;
import com.hankcs.lda.LdaUtil;

/**
 * 
 * @author superhy
 *
 */
public class LDALevelModel {

	private String corpusPath;
	private int topicNum;
	private int genWordNum;

	public LDALevelModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LDALevelModel(String corpusPath, int topicNum, int genWordNum) {
		super();
		this.corpusPath = corpusPath;
		this.topicNum = topicNum;
		this.genWordNum = genWordNum;
	}

	/**
	 * 执行lda核心算法
	 * 
	 * @param corpusPath
	 * @param topicNum
	 * @param genWordNum
	 * @return
	 */
	public Map<String, Double>[] runLdaAlgorithm(String corpusPath,
			int topicNum, int genWordNum) {

		try {

			// 1. Load corpus from disk
			Corpus corpus = Corpus.load(corpusPath);
			// 2. Create a LDA sampler
			LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(
					corpus.getDocument(), corpus.getVocabularySize());
			// 3. Train it
			ldaGibbsSampler.gibbs(topicNum);
			// 4. The phi matrix is a LDA model, you can use LdaUtil to explain
			// it.
			double[][] phi = ldaGibbsSampler.getPhi();
			Map<String, Double>[] topicMap = LdaUtil.translate(phi,
					corpus.getVocabulary(), genWordNum);
			LdaUtil.explain(topicMap);

			return topicMap;
		} catch (Exception e) {
			// TODO: handle exception

			return null;
		}
	}

	/**
	 * 挖掘lda模型中的层次关系
	 * 
	 * @param corpusPath
	 * @param topicMaps
	 * @return
	 * @throws Exception
	 */
	public LdaGraph buildLdaGraph(String corpusPath,
			Map<String, Double>[] topicMaps) throws Exception {

		List<LdaDoc> docs = new ArrayList<LdaDoc>();
		List<LdaTopic> topics = new ArrayList<LdaTopic>();

		List<String> allLdaWordText = new ArrayList<String>();

		for (Map<String, Double> topicMap : topicMaps) {

			LdaTopic topic = new LdaTopic();
			for (Entry<String, Double> genWord : topicMap.entrySet()) {
				String wordText = genWord.getKey();
				double wordProb = genWord.getValue();

				if (AllLdaItemSet.allLdaWordsText.contains(wordText)) {
					int genWordId = AllLdaItemSet.findWordId(wordText);
					if (genWordId != -1) {
						topic.generateWords.put(genWordId, wordProb);
					}
				} else {
					int genWordId = ++AllLdaItemSet.wordsNum;
					AllLdaItemSet.allLdaWordsText.add(wordText);
					// AllLdaItemSet.allLdaWords.add(new LdaWord(wordText,
					// genWordId));
					AllLdaItemSet.allLdaWords.put(genWordId, wordText);
					topic.generateWords.put(genWordId, wordProb);

					allLdaWordText.add(wordText);
				}
			}

			int topicId = ++AllLdaItemSet.docsTopicsNum;
			topic.topicLdaId = topicId;
			AllLdaItemSet.allLdaTopics.add(topic);
			topics.add(topic);
		}

		File corpusFolder = new File(corpusPath);
		for (File file : corpusFolder.listFiles()) {

			LdaDoc doc = new LdaDoc();

			BufferedReader br = new BufferedReader(new FileReader(file));
			String corpusText = br.readLine();

			int corpusWordNum = 0;
			List<String> corpusCacheWords = new ArrayList<String>();
			for (String corpusWord : corpusText.split(" ")) {
				if (allLdaWordText.contains(corpusWord)) {
					corpusWordNum++;
				}
				corpusCacheWords.add(corpusWord);
			}

			for (LdaTopic topic : topics) {

				int topicWordNum = 0;
				for (Entry<Integer, Double> topicGenWord : topic.generateWords
						.entrySet()) {
					int wordId = topicGenWord.getKey();
					if (corpusCacheWords.contains(AllLdaItemSet
							.findWordText(wordId))) {
						topicWordNum++;
					}
				}

				double topicProb = topicWordNum < corpusWordNum ? (topicWordNum * 1.0 / (corpusWordNum + 1))
						: 0;
				if (topicProb > 0) {
					doc.generateTopics.put(topic.topicLdaId, topicProb);
				}
			}

			int docId = ++AllLdaItemSet.docsTopicsNum;
			doc.docLdaId = docId;
			AllLdaItemSet.allLdaDocs.add(doc);
			docs.add(doc);
		}

		LdaGraph ldaGraph = new LdaGraph(docs, topics);

		return ldaGraph;
	}

	public LdaGraph getLdaLevelGraph() {

		// 执行lda核心程序
		Map<String, Double>[] topicMap = this.runLdaAlgorithm(this.corpusPath,
				this.topicNum, this.genWordNum);

		LdaGraph levelGraph;
		try {

			System.out.println("正在统计层次关系...");

			levelGraph = this.buildLdaGraph(this.corpusPath, topicMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

		return levelGraph;
	}

	public String getCorpusPath() {
		return corpusPath;
	}

	public void setCorpusPath(String corpusPath) {
		this.corpusPath = corpusPath;
	}

	public int getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}

	public int getGenWordNum() {
		return genWordNum;
	}

	public void setGenWordNum(int genWordNum) {
		this.genWordNum = genWordNum;
	}

}

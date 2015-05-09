package org.mltk.task.t_mcmf;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.mltk.lucene.SearchInLocalContent;
import org.mltk.lucene.model.Word;
import org.mltk.task.t_mcmf.model.LdaTopic;

public class MI {

	// 语料索引路径
	private String indexPath;
	// 总语料数量
	private int corpusNum;
	// 分词器
	private Analyzer analyzer;

	public double compWordsMI(Word word1, Word word2) {

		SearchInLocalContent searchInLocalContent = new SearchInLocalContent(
				this.indexPath, true);

		double pWord1 = (searchInLocalContent.phraseQuerySearcher(
				word1.getWordText(), this.corpusNum + 1, this.analyzer).size());
		double pWord2 = (searchInLocalContent.phraseQuerySearcher(
				word2.getWordText(), this.corpusNum + 1, this.analyzer).size());
		double pMutual = (searchInLocalContent.phraseQuerySearcher(
				word1.getWordText() + " " + word2.getWordText(),
				this.corpusNum + 1, this.analyzer).size());

		// 计算 log_2
		double MutualInfomation = Math.log(pMutual / (pWord1 * pWord2))
				/ Math.log((double) 2);

		return MutualInfomation;
	}

	public static double compTopicsMI(LdaTopic topic1, LdaTopic topic2) {

		Map<Integer, Double> topic1GenWord = topic1.generateWords;
		Map<Integer, Double> topic2GenWord = topic2.generateWords;

		Set<Integer> allSet = new HashSet<Integer>();
		int sameNum = 0;
		Set<Integer> topic1Set = topic1GenWord.keySet();
		Set<Integer> topic2Set = topic2GenWord.keySet();
		for (Integer wordId : topic1Set) {
			if (topic2Set.contains(wordId)) {
				sameNum++;
			}
		}
		for (Integer word1Id : topic1Set) {
			allSet.add(word1Id);
			for (Integer word2Id : topic2Set) {
				allSet.add(word2Id);
			}
		}
		int allNum = allSet.size();

		double MutualInfomation = sameNum < allNum ? ((sameNum * 1.0 + 1) / allNum)
				: 1;

		return MutualInfomation;

	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public int getCorpusNum() {
		return corpusNum;
	}

	public void setCorpusNum(int corpusNum) {
		this.corpusNum = corpusNum;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

}

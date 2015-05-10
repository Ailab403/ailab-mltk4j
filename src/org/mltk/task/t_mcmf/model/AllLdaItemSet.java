package org.mltk.task.t_mcmf.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author superhy
 *
 */
public class AllLdaItemSet {

	public static int wordsNum = 0;

	public static Map<Integer, String> allLdaWords = new HashMap<Integer, String>();

	public static Set<String> allLdaWordsText = new HashSet<String>();

	public static Set<LdaTopic> allLdaTopics = new HashSet<LdaTopic>();

	public static Set<LdaDoc> allLdaDocs = new HashSet<LdaDoc>();

	public static int docsTopicsNum = 0;

	public static int findWordId(String wordText) {
		for (Entry<Integer, String> ldaWord : allLdaWords.entrySet()) {
			if (ldaWord.getValue().equals(wordText)) {
				return ldaWord.getKey();
			}
		}

		return -1;
	}

	public static String findWordText(int wordId) {
		return allLdaWords.get(wordId);
	}
}

package org.mltk.task.t_mcmf.model;

import java.util.HashSet;
import java.util.Set;

public class AllLdaItemSet {

	public static int wordsNum = 0;

	public static Set<LdaWord> allLdaWords = new HashSet<LdaWord>();

	public static Set<String> allLdaWordsText = new HashSet<String>();

	public static Set<LdaTopic> allLdaTopics = new HashSet<LdaTopic>();

	public static Set<LdaDoc> allLdaDocs = new HashSet<LdaDoc>();

	public static int docsTopicsNum = 0;

	public static int findWordId(String wordText) {
		for (LdaWord ldaWord : allLdaWords) {
			if (ldaWord.getWordText().equals(wordText)) {
				return ldaWord.wordLdaId;
			}
		}

		return -1;
	}

	public static String findWordText(int wordId) {
		for (LdaWord ldaWord : allLdaWords) {
			if (ldaWord.wordLdaId == wordId) {
				return ldaWord.getWordText();
			}
		}

		return null;
	}
}

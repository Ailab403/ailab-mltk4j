package test.mltk.lucene;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.ansj.lucene3.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.mltk.lucene.SearchInLocalContent;
import org.mltk.lucene.cache.IndexDirectoryLocPath;

public class SearchInLocalContentTest {

	@Test
	public void testPhraseQuerySearcher() {
		org.mltk.lucene.SearchInLocalContent searchInLocalContent = new SearchInLocalContent(
				IndexDirectoryLocPath.ALL_INDEX);
		
		System.out.println(searchInLocalContent.getFlagAndSearch());

		String keyValue = new Scanner(System.in).next();

		// µÃµ½·Ö´ÊÆ÷
		Analyzer analyzer = new AnsjAnalysis();

		List<Map<String, Object>> resMaps = searchInLocalContent
				.phraseQuerySearcher(keyValue, 60, analyzer);

		System.out.println(resMaps.size());
		for (Map<String, Object> map : resMaps) {
			System.out.println(map.toString());
		}
	}
}

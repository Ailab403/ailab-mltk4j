package test.mltk.lucene;

import org.ansj.lucene3.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.mltk.lucene.InitAllContentIndex;
import org.mltk.lucene.cache.IndexDirectoryLocPath;

public class InitAllContentIndexTest {

	public static void main(String[] args) {

		InitAllContentIndex testObj = new InitAllContentIndex();

		Analyzer analyzer = new AnsjAnalysis();
		testObj.execInitContentIndexThread(analyzer,
				IndexDirectoryLocPath.FILE_EXAMPLE,
				IndexDirectoryLocPath.ALL_INDEX);
	}
}

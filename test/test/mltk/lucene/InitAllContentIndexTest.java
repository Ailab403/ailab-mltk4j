package test.mltk.lucene;

import org.ansj.lucene3.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.mltk.lucene.InitAllContentIndex;

public class InitAllContentIndexTest {

	public static void main(String[] args) {

		InitAllContentIndex testObj = new InitAllContentIndex();
		
		Analyzer analyzer = new AnsjAnalysis();
		testObj.execInitContentIndexThread(analyzer);
	}
}

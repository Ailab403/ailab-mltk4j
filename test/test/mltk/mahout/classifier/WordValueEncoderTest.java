package test.mltk.mahout.classifier;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.mltk.mahout.classifier.TextWordValueEncoder;

public class WordValueEncoderTest {

	@Test
	public void testEncodeBasicTextWordsValue() {

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		String encodeStr = "text to magically vectorize";

		TextWordValueEncoder encoder = new TextWordValueEncoder();
		try {
			encoder.encodeBasicTextWordsValue(analyzer, encodeStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package org.mltk.mahout.classifier;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.mahout.classifier.sgd.L1;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.vectorizer.encoders.ConstantValueEncoder;
import org.apache.mahout.vectorizer.encoders.Dictionary;
import org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder;
import org.apache.mahout.vectorizer.encoders.StaticWordValueEncoder;

public class TextWordValueEncoder {

	/**
	 * 向量编码器测试demo
	 * 
	 * @param analyzer
	 * @param encodeStr
	 * @throws IOException
	 */
	public void encodeBasicTextWordsValue(Analyzer analyzer, String encodeStr)
			throws IOException {
		FeatureVectorEncoder encoder = new StaticWordValueEncoder("text");

		StringReader in = new StringReader(encodeStr);
		TokenStream ts = analyzer.tokenStream("body", in);
		CharTermAttribute termAttribute = ts
				.addAttribute(CharTermAttribute.class);

		Vector vector = new RandomAccessSparseVector(100);
		while (ts.incrementToken()) {
			char[] termBuffer = termAttribute.buffer();
			int termLen = termAttribute.length();

			String w = new String(termBuffer, 0, termLen);
			encoder.addToVector(w, 1, vector);
		}

		System.out.println(new SequentialAccessSparseVector(vector));
	}

	/**
	 * SGD分类器训练 测试demo
	 */
	public void SGDExample() {
		/*
		 * 建立向量编码器
		 */
		Map<String, Set<Integer>> traceDictionary = new TreeMap<String, Set<Integer>>();
		FeatureVectorEncoder encoder = new StaticWordValueEncoder("body");
		encoder.setProbes(2);
		encoder.setTraceDictionary(traceDictionary);
		FeatureVectorEncoder bias = new ConstantValueEncoder("Intercept");
		bias.setTraceDictionary(traceDictionary);
		FeatureVectorEncoder lines = new ConstantValueEncoder("Lines");
		lines.setTraceDictionary(traceDictionary);
		Dictionary newGroups = new Dictionary();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);

		/*
		 * 配置学习算法
		 */
		OnlineLogisticRegression learningAlgorithm = new OnlineLogisticRegression(
				20, 1000, new L1()).alpha(1).stepOffset(1000)
				.decayExponent(0.9).lambda(3.0e-5).learningRate(20);
	}
	
	/*
	 * 访问数据文件
	 */
	
}

package org.mltk.openNLP;

import java.io.File;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

import opennlp.tools.cmdline.namefind.TokenNameFinderModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

public class NameEntityFindTester {

	// 默认参数
	private double probThreshold = 0.6;

	private String modelPath;
	private String testFileDirPath;

	public NameEntityFindTester() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NameEntityFindTester(String modelPath, String testFileDirPath) {
		super();
		this.modelPath = modelPath;
		this.testFileDirPath = testFileDirPath;
	}

	public NameEntityFindTester(double probThreshold, String modelPath,
			String testFileDirPath) {
		super();
		this.probThreshold = probThreshold;
		this.modelPath = modelPath;
		this.testFileDirPath = testFileDirPath;
	}

	/**
	 * 生成NameFinder
	 * 
	 * @return
	 */
	public NameFinderME prodNameFinder() {
		NameFinderME finder = new NameFinderME(
				new TokenNameFinderModelLoader().load(new File(modelPath)));

		return finder;
	}

	/**
	 * 计算基本命名实体概率
	 * 
	 * @param finder
	 *            命名实体识别模型
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> cptBasicNameProb(NameFinderME finder)
			throws Exception {
		Map<String, String> basicNameProbResMap = new IdentityHashMap<String, String>();
		String testContent = NameEntityTextFactory.loadFileTextDir(this
				.getTestFileDirPath());

		// TODO 大文本情况下，消耗内存大，需要改写成分批处理模式（把一个大文件分成多个小文件再批量处理）
		Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
		// 待测词，测试结果，概率
		String[] tokens = tokenizer.tokenize(testContent);
		Span[] names = finder.find(tokens);
		double[] nameSpanProbs = finder.probs(names);

		System.out.println("tokens size: " + tokens.length);
		System.out.println("names size: " + names.length);
		System.out.println("name_span_probs size: " + nameSpanProbs.length);

		for (int i = 0; i < names.length; i++) {
			String testToken = "";
			for (int j = names[i].getStart(); j <= names[i].getEnd() - 1; j++) {
				testToken += tokens[j];
			}
			String testRes = names[i].getType() + ":"
					+ Double.toString(nameSpanProbs[i]);

			// TODO delete print
			System.out.println("find name: \"" + testToken + "\" has res: "
					+ testRes);

			basicNameProbResMap.put(testToken, testRes);
		}

		return basicNameProbResMap;
	}

	/**
	 * 过滤除去概率值过低的识别结果
	 * 
	 * @param basicNameProbResMap
	 * @return
	 */
	public Map<String, String> filterNameProbRes(
			Map<String, String> basicNameProbResMap) {
		Map<String, String> filttedNameProbResMap = new HashMap<String, String>();
		for (Entry<String, String> entry : basicNameProbResMap.entrySet()) {
			String token = entry.getKey();
			String res = basicNameProbResMap.get(token);

			if (Double.parseDouble(res.split(":")[1]) >= this
					.getProbThreshold()) {
				filttedNameProbResMap.put(token, res);
			}
		}

		return filttedNameProbResMap;
	}

	/**
	 * 预测组件总调用方法
	 * 
	 * @return
	 */
	public Map<String, String> execNameFindTester() {
		try {
			NameFinderME finder = this.prodNameFinder();
			Map<String, String> basicNameProbResMap = this
					.cptBasicNameProb(finder);
			Map<String, String> nameProbResMap = this
					.filterNameProbRes(basicNameProbResMap);

			return nameProbResMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * @return the probThreshold
	 */
	public double getProbThreshold() {
		return probThreshold;
	}

	/**
	 * @param probThreshold
	 *            the probThreshold to set
	 */
	public void setProbThreshold(double probThreshold) {
		this.probThreshold = probThreshold;
	}

	/**
	 * @return the modelPath
	 */
	public String getModelPath() {
		return modelPath;
	}

	/**
	 * @param modelPath
	 *            the modelPath to set
	 */
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	/**
	 * @return the testFileDirPath
	 */
	public String getTestFileDirPath() {
		return testFileDirPath;
	}

	/**
	 * @param testFileDirPath
	 *            the testFileDirPath to set
	 */
	public void setTestFileDirPath(String testFileDirPath) {
		this.testFileDirPath = testFileDirPath;
	}

}

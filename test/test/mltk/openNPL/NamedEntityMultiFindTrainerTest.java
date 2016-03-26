package test.mltk.openNPL;

import java.util.Map;
import java.util.Map.Entry;

import org.ansj.lucene3.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.mltk.lucene.seg.TokenSegFolderFileRecursion;
import org.mltk.openNLP.NameEntityFindTester;
import org.mltk.openNLP.NamedEntityMultiFindTrainer;

public class NamedEntityMultiFindTrainerTest {

	@Test
	public void testSegWord() {
		Analyzer ansjAnalyzer = new AnsjAnalysis();

		String oriFolderPath = ".\\file\\name_find\\train";
		String tagFolderPath = ".\\file\\name_find\\seg";

		TokenSegFolderFileRecursion tokenSegFolderFileRecursion = new TokenSegFolderFileRecursion(
				ansjAnalyzer);
		tokenSegFolderFileRecursion.execTokenSegRecursion(oriFolderPath,
				tagFolderPath);
	}

	@Test
	public void testExecFindTrainer() {
		String nameWordsPath = ".\\file\\name_find\\name_words";
		String dataPath = ".\\file\\name_find\\seg\\train";
		String modelPath = ".\\file\\name_find\\model\\multi_name_model.bin";

		NamedEntityMultiFindTrainer trainer = new NamedEntityMultiFindTrainer(
				nameWordsPath, dataPath, modelPath);
		boolean succFlag = trainer.execNameFindTrainer();
		
		System.out.println(succFlag);
	}
	
	@Test
	public void testExecNameFindTester() {
		String modelPath = ".\\file\\name_find\\model\\multi_name_model.bin";
		String testFileDirPath = ".\\file\\name_find\\seg\\test";
		
		NameEntityFindTester tester = new NameEntityFindTester(modelPath, testFileDirPath);
		Map<String, String> nameProbResMap = tester.execNameFindTester();
		
		for (Entry<String, String> nameProbRes : nameProbResMap.entrySet()) {
			System.out.println(nameProbRes.getKey() + " -> " + nameProbRes.getValue());
		}
	}
}

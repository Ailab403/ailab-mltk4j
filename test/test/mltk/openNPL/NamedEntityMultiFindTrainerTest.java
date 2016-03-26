package test.mltk.openNPL;

import java.io.File;
import java.util.Date;

import org.ansj.lucene3.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.mltk.lucene.seg.TokenSegFolderFileRecursion;

public class NamedEntityMultiFindTrainerTest {

	@Test
	public void testSegWord() {
		Analyzer ansjAnalyzer = new AnsjAnalysis();

		String oriFolderPath = ".\\file\\name_find\\test";
		String tagFolderPath = ".\\file\\name_find\\seg";

		TokenSegFolderFileRecursion tokenSegFolderFileRecursion = new TokenSegFolderFileRecursion(
				ansjAnalyzer);
		tokenSegFolderFileRecursion.execTokenSegRecursion(oriFolderPath,
				tagFolderPath);
	}

	@Test
	public void testExecFindTrainer() {

		File trainFile = new File(".\\file\\name_find\\name_words\\person.txt");
		System.out.println(trainFile.getName());

		System.out.println(new Date().toString().replaceAll(" ", "")
				.replaceAll(":", ""));
	}
}

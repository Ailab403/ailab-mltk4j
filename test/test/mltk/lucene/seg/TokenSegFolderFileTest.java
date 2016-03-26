package test.mltk.lucene.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import org.ansj.lucene3.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.mltk.lucene.model.Word;
import org.mltk.lucene.seg.TokenSegFolderFileMP;
import org.mltk.lucene.seg.TokenSegFolderFileRecursion;

public class TokenSegFolderFileTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String oriFolderPath = ".\\file\\sentiment\\ori\\dianying\\pos";
		String tagFolderPath = ".\\file\\sentiment\\seg\\dianying\\pos";

		Analyzer a = new AnsjAnalysis();
		TokenSegFolderFileMP tokenSegFolderFile = new TokenSegFolderFileMP(a);
		tokenSegFolderFile.execTokenSegThread(oriFolderPath, tagFolderPath);
	}

	@Test
	public void testTokenSegFolderFileRecursion() {
		String oriFolderPath = ".\\file\\sentiment\\ori\\dianzi";
		String tagFolderPath = ".\\file\\sentiment\\seg";

		Analyzer a = new AnsjAnalysis();
		TokenSegFolderFileRecursion tokenSegFolderFileRecursion = new TokenSegFolderFileRecursion(
				a);
		tokenSegFolderFileRecursion.execTokenSegRecursion(oriFolderPath,
				tagFolderPath);
	}

	// º∆À„word ˝¡ø
	@Test
	public void testCountWordNum() throws Exception {

		String segFolderPath = ".\\file\\sentiment\\seg\\dianying\\pos";

		File folder = new File(segFolderPath);
		Set<Word> wordSet = new HashSet<Word>();
		for (File file : folder.listFiles()) {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				for (String wordText : words) {
					if (!wordText.equals("")) {
						Word word = new Word(wordText);
						wordSet.add(word);
					}
				}
			}
		}

		for (Word word : wordSet) {
			System.out.println(word.getWordText());
		}

		System.out.println(wordSet.size());
	}

}

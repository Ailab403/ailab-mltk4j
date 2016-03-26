package test.mltk.openNPL;

import java.io.File;
import java.util.Date;

import org.junit.Test;

public class NamedEntityMultiFindTrainerTest {

	@Test
	public void testExecFindTrainer() {

		File trainFile = new File("/file/name_find/name_words/person.txt");
		System.out.println(trainFile.getName());

		System.out.println(new Date().toString().replaceAll(" ", "").replaceAll(":", ""));
	}
}

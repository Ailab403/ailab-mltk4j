package test.mltk.lingpipe;

import org.mltk.lingpipe.polarity.LingpipePolarityClassifyText;

public class LingpipePolarityClassifyTextTest {

	public static void main(String[] args) {
		LingpipePolarityClassifyText lingpipePolarityClassifyText = new LingpipePolarityClassifyText(
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYCLASSIFIER_MODEL_PATH,
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYTEST_FOLDER);

		lingpipePolarityClassifyText.polarityClassifyText();
	}
}

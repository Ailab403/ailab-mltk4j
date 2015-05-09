package test.mltk.lingpipe;

import java.util.List;

import org.mltk.lingpipe.model.PolarityRes;
import org.mltk.lingpipe.polarity.LingpipePolarityClassifyText;

public class LingpipePolarityClassifyTextTest {

	public static void main(String[] args) {
		LingpipePolarityClassifyText lingpipePolarityClassifyText = new LingpipePolarityClassifyText(
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYCLASSIFIER_MODEL_PATH,
				LingpipeAnalysisDataPath.LINGPIPE_POLARITYTEST_FOLDER);

		List<PolarityRes> polarityResList = lingpipePolarityClassifyText
				.polarityClassifyText();

		int correctNum = 0;
		for (PolarityRes polarityRes : polarityResList) {
			if ((polarityRes.getBestCategory().equals("neg") && polarityRes
					.getFileName().contains("n"))
					|| (polarityRes.getBestCategory().equals("pos") && polarityRes
							.getFileName().contains("p"))) {
				correctNum++;
			}
		}

		// 打印大致的准确率
		System.out.println("ac: " + correctNum * 1.0 / polarityResList.size());
	}
}

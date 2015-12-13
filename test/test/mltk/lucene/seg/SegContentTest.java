package test.mltk.lucene.seg;

import java.util.List;

import org.ansj.domain.Term;
import org.ansj.lucene3.AnsjAnalysis;
import org.ansj.recognition.NatureRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;

public class SegContentTest {

	@Test
	public void testSplitWord() {
		Analyzer a1 = new AnsjAnalysis();

		String txt = "绵竹市汉旺镇桑家大院前的千年紫薇本来枝繁叶茂花开灿烂，却在2013年的10月10日晚被盗(本报曾报道)。当晚，四名盗树贼便获得了29万元的报酬。第二天，紫薇从绵竹偷运到成都，在绕城高速的一个出口处盗卖者梁某以58万元的价格出售给张某，赚了29万元。张某并没有让紫薇树在自己手里停留，并当天就把树卖给了三圣花乡的一家园艺公司，卖出了88万元的价格。";
		AnalyzerUtils.displayToken(txt, a1);
	}

	@Test
	public void testSplitWordRef() {

		// 分词
		List<Term> parse = ToAnalysis
				.parse("坚持到今天，你已经是考研的成功者了！很多年之后，当我们回顾这段奋斗的时光，所有的绝望、痛苦和挣扎都是青春的色彩，都是成功必须付出的代价！充满信心的上考场吧，相信自己，祝同学们成功！");
		// 词性标注
		new NatureRecognition(parse).recognition();

		String splitStr = "";
		for (Term term : parse) {
			splitStr += (term + " ");
		}
		System.out.println(splitStr);
	}
}

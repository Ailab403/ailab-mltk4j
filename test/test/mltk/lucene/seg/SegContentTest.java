package test.mltk.lucene.seg;

import org.ansj.lucene3.AnsjAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;

public class SegContentTest {

	@Test
	public void testSplitWord() {
		Analyzer a1 = new AnsjAnalysis();

		String txt = "绵竹市汉旺镇桑家大院前的千年紫薇本来枝繁叶茂花开灿烂，却在2013年的10月10日晚被盗(本报曾报道)。当晚，四名盗树贼便获得了29万元的报酬。第二天，紫薇从绵竹偷运到成都，在绕城高速的一个出口处盗卖者梁某以58万元的价格出售给张某，赚了29万元。张某并没有让紫薇树在自己手里停留，并当天就把树卖给了三圣花乡的一家园艺公司，卖出了88万元的价格。";
		AnalyzerUtils.displayToken(txt, a1);
	}
}

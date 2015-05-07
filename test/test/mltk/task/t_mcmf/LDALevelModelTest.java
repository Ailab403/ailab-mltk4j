package test.mltk.task.t_mcmf;

import java.util.List;

import org.junit.Test;
import org.mltk.task.t_mcmf.LDALevelModel;
import org.mltk.task.t_mcmf.model.AllLdaItemSet;
import org.mltk.task.t_mcmf.model.LdaDoc;
import org.mltk.task.t_mcmf.model.LdaGraph;
import org.mltk.task.t_mcmf.model.LdaTopic;

public class LDALevelModelTest {

	@Test
	public void testGetLevelGraph() {

		String CORPUS_PATH = ".\\file\\sentiment\\seg\\dianying\\pos";
		int topicNum = 10;
		int genWordNum = 100;

		LDALevelModel ldaLevelModel = new LDALevelModel(CORPUS_PATH, topicNum,
				genWordNum);

		LdaGraph ldaGraph = ldaLevelModel.getLdaLevelGraph();
		List<LdaDoc> ldaDocs = ldaGraph.allDocs;
		List<LdaTopic> ldaTopics = ldaGraph.allTopics;

		System.out.println("\n文档层----------------------------");
		for (LdaDoc doc : ldaDocs) {
			System.out.println("生成主题数目：" + doc.generateTopics.size() + " ");
		}
		System.out.println("\n主题层----------------------------");
		for (LdaTopic topic : ldaTopics) {
			System.out.println("生成词条数目：" + topic.generateWords.size());
		}
		
		System.out.println(AllLdaItemSet.topicsNum + " " + AllLdaItemSet.docsNum);
	}
}

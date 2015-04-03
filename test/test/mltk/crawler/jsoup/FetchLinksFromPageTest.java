package test.mltk.crawler.jsoup;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.mltk.crawler.cache.CrawlSeedParam;
import org.mltk.crawler.jsoup.FetchLinksFromPage;
import org.mltk.crawler.model.ExactLinks;

public class FetchLinksFromPageTest {

	@Test
	public void testGetAllUrlInPage() {

		FetchLinksFromPage testObj = new FetchLinksFromPage();
		Set<ExactLinks> resLinks = new HashSet<ExactLinks>();
		resLinks = testObj.getAllUrl(CrawlSeedParam.SINA_NEWS);

		for (ExactLinks exactLinks : resLinks) {
			System.out.println(exactLinks.getUrl());
		}
	}
}

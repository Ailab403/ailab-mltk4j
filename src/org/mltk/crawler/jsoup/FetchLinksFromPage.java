package org.mltk.crawler.jsoup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mltk.crawler.cache.CrawlUnitParam;
import org.mltk.crawler.model.ExactLinks;

public class FetchLinksFromPage {

	// 获得所有的链接
	public Set<ExactLinks> getAllUrl(String seedUrl) {
		Set<ExactLinks> urlSet = new HashSet<ExactLinks>();

		try {
			Document docPage = JsoupGetDocument
					.getDocumentByJsoupBasic(seedUrl);
			Elements eleLinks = docPage.select("a[href]");
			for (Element eleLink : eleLinks) {
				String url = eleLink.attr("abs:href");
				String urlMD5 = new TransMD5().getMD5Code(url);

				urlSet.add(new ExactLinks(url, urlMD5));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return urlSet;
	}

	// 过滤出有用的url
	public Set<ExactLinks> filterUrl(String seedUrl) {

		Set<ExactLinks> urlSet = this.getAllUrl(seedUrl);
		Set<ExactLinks> filterLinks = new HashSet<ExactLinks>();

		for (String urlModel : CrawlUnitParam.SINA_URL_MODEL) {

			for (ExactLinks links : urlSet) {
				if (links.getUrl().contains(urlModel)) {
					filterLinks.add(links);
				}
			}
		}

		return filterLinks;
	}
}

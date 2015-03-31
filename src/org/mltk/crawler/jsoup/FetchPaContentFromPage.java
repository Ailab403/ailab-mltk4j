package org.mltk.crawler.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author superhy
 * 
 */
public class FetchPaContentFromPage {

	public String getParagraphContent(String pageUrl) {

		String paragraphContent = "";

		try {
			Document docPage = JsoupGetDocument
					.getDocumentByJsoupBasic(pageUrl);
			Elements eleParagraphs = docPage.select("p");
			for (Element eleParagraph : eleParagraphs) {
				String content = eleParagraph.text();

				paragraphContent += content;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

		return paragraphContent;
	}
}

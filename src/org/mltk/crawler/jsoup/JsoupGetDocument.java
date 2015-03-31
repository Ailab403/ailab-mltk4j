package org.mltk.crawler.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupGetDocument {

	public static Document getDocumentByJsoupBasic(String url) {
		try {

			// 设置连接超时和读数超时
			// 设置忽略过期页面
			return Jsoup.connect(url).timeout(120000).ignoreHttpErrors(true)
					.ignoreContentType(true).get();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}
}

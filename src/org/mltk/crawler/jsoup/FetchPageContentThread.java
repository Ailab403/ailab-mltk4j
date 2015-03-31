package org.mltk.crawler.jsoup;

import java.util.concurrent.Callable;

import org.mltk.crawler.model.ExactLinks;

public class FetchPageContentThread implements Callable<Boolean> {

	private ExactLinks links;
	private String folderPath;

	public FetchPageContentThread(ExactLinks links, String folderPath) {
		super();
		this.links = links;
		this.folderPath = folderPath;
	}

	@Override
	public Boolean call() throws Exception {

		System.out.println(this.links.getUrl());
		String content = new FetchPaContentFromPage()
				.getParagraphContent(this.links.getUrl());

		Boolean flagSucc = false;
		if (!content.equals("") && content != null) {
			String filePath = this.folderPath + links.getUrlMD5() + ".txt";
			flagSucc = new WriteContentIntoFile().writeContent(filePath,
					content);
		}

		return flagSucc;
	}

}

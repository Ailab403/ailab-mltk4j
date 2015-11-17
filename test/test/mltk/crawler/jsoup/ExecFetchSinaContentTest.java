package test.mltk.crawler.jsoup;

import java.util.Scanner;

import org.mltk.crawler.cache.CrawlSeedParam;
import org.mltk.crawler.jsoup.ExecFetchSinaNewsContent;

public class ExecFetchSinaContentTest {

	public static void main(String[] args) {

		Scanner cin = new Scanner(System.in);
		int threadNum = cin.nextInt();
		String seedUrl = CrawlSeedParam.SINA_NEWS;
		String fileFolderPath = "./file/crawler_test/";

		ExecFetchSinaNewsContent testObj = new ExecFetchSinaNewsContent();
		testObj.execFetchSinaContentThread(seedUrl, fileFolderPath, threadNum);
	}
}

package test.mltk.crawler.taobao;

import java.util.Date;

import org.junit.Test;
import org.mltk.crawler.taobao.TraderatesCrawler;

public class TraderatesCrawlerTest {

	@Test
	public void testGetTradeRatesXML() {

		String fields = "tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid";
		String rate_type = "get";
		String role = "seller";
		String result = null;
		Long page_no = null;
		Long page_size = null;
		Date start_date = null;
		Date end_date = null;
		Long tid = null;
		Boolean use_has_next = null;
		Long num_iid = 40816472159L;

		TraderatesCrawler testObj = new TraderatesCrawler();
		String resultStr = testObj.getTradeRatesXML(fields, rate_type, role,
				result, page_no, page_size, start_date, end_date, tid,
				use_has_next, num_iid);

		System.out.println(resultStr);
	}
}

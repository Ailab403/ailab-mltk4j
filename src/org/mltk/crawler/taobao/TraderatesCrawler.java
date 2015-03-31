package org.mltk.crawler.taobao;

import java.util.Date;

import org.mltk.crawler.cache.TaobaoKeyParam;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;

public class TraderatesCrawler {

	public String getTradeRatesXML(String fields, String rate_type,
			String role, String result, Long page_no, Long page_size,
			Date start_date, Date end_date, Long tid, Boolean use_has_next,
			Long num_iid) {

		String resultStr = null;

		try {
			TaobaoClient client = new DefaultTaobaoClient(
					TaobaoKeyParam.SERVER_URL, TaobaoKeyParam.APP_KEY,
					TaobaoKeyParam.APP_SECRET);
			TraderatesGetRequest req = new TraderatesGetRequest();
			req.setFields(fields);
			req.setRateType(rate_type);
			req.setRole(role);
			if (result != null) {
				req.setResult(result);
			}
			if (page_no != null) {
				req.setPageNo(page_no);
			}
			if (page_size != null) {
				req.setPageSize(page_size);
			}
			if (start_date != null) {
				req.setStartDate(start_date);
			}
			if (end_date != null) {
				req.setEndDate(end_date);
			}
			if (tid != null) {
				req.setTid(tid);
			}
			if (use_has_next != null) {
				req.setUseHasNext(use_has_next);
			}
			if (num_iid != null) {
				req.setNumIid(num_iid);
			}

			TraderatesGetResponse response = client.execute(req,
					TaobaoKeyParam.SESSION_KEY);

			resultStr = response.getBody();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultStr;
	}
}

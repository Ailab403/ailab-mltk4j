package org.mltk.crawler.weibo;

import java.util.ArrayList;
import java.util.List;

import org.mltk.crawler.cache.WeiboKeyParam;

import weibo4j.Timeline;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

public class FetchWeiboStatuses {

	/**
	 * 获取最新的公共微博
	 * 
	 * @param count
	 *            请求返回的微博数量
	 * @return
	 */
	public List<Status> getPublicTimeline(int count) {

		List<Status> publicStatusList = new ArrayList<Status>();

		try {

			Timeline tm = new Timeline(WeiboKeyParam.ACCESS_TOKEN);
			StatusWapper status = tm.getPublicTimeline(count, 0);

			System.out.println(status.getTotalNumber());

			List<Status> statusList = status.getStatuses();
			for (Status eachStatus : statusList) {
				publicStatusList.add(eachStatus);
			}

			// TODO delete print
			// System.out.println(publicJsonList);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

		return publicStatusList;
	}

	/**
	 * 根据用户昵称返回用户的微博（只能获取授权用户的微博，目前只有自己的微博）
	 * 
	 * @param screen_name
	 *            用户昵称
	 * @param since_id
	 *            起始微博id
	 * @param max_id
	 *            结束微博id
	 * @param count
	 *            单页请求返回的微博数量
	 * @param page
	 *            返回结果的页码
	 * @return
	 */
	@Deprecated
	public List<Status> getUserTimelineByName(String screen_name, int since_id,
			int max_id, int count, int page) {

		List<Status> userStatusList = new ArrayList<Status>();

		try {

			// 设置部分参数的默认值
			count = (count == 0 ? 20 : count);
			page = (page == 0 ? 1 : page);

			// 配置默认参数
			Timeline tm = new Timeline(WeiboKeyParam.ACCESS_TOKEN);
			Paging paging = new Paging();
			if (since_id != 0) {
				paging.setSinceId(since_id);
			}
			if (max_id != 0) {
				paging.setMaxId(max_id);
			}
			if (count != 0) {
				paging.setCount(count);
			}
			if (page != 0) {
				paging.setPage(page);
			}

			// 获取查询结果
			StatusWapper status = tm.getUserTimelineByName(screen_name, paging,
					0, 0);

			System.out.println("结果总量：" + status.getTotalNumber());

			List<Status> statusList = status.getStatuses();
			for (Status eachStatus : statusList) {
				userStatusList.add(eachStatus);
			}

		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userStatusList;
	}

	/**
	 * 获取帐号关注用户的最新微博（全部获得，后期再安用户唯一标识过滤）
	 * 
	 * @param since_id
	 *            起始微博id
	 * @param max_id
	 *            结束微博id
	 * @param count
	 *            单页请求返回的微博数量
	 * @param page
	 *            返回结果的页码
	 * @return
	 */
	public List<Status> getHomeTimeLine(int since_id, int max_id, int count,
			int page) {

		List<Status> userStatusList = new ArrayList<Status>();

		try {
			// 设置部分参数的默认值
			count = (count == 0 ? 20 : count);
			page = (page == 0 ? 1 : page);

			// 配置默认参数
			Timeline tm = new Timeline(WeiboKeyParam.ACCESS_TOKEN);
			Paging paging = new Paging();
			if (since_id != 0) {
				paging.setSinceId(since_id);
			}
			if (max_id != 0) {
				paging.setMaxId(max_id);
			}
			if (count != 0) {
				paging.setCount(count);
			}
			if (page != 0) {
				paging.setPage(page);
			}

			// 获取查询结果
			StatusWapper status = tm.getHomeTimeline(0, 0, paging);

			System.out.println("结果总量：" + status.getTotalNumber());

			List<Status> statusList = status.getStatuses();
			for (Status eachStatus : statusList) {
				userStatusList.add(eachStatus);
			}

		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userStatusList;
	}
}

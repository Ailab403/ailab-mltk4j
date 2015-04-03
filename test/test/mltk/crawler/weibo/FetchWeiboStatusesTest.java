package test.mltk.crawler.weibo;

import java.util.List;

import org.junit.Test;
import org.mltk.crawler.weibo.FetchWeiboStatuses;

import weibo4j.model.Status;

public class FetchWeiboStatusesTest {

	@Test
	public void testGetPublicTimeline() {
		FetchWeiboStatuses testObj = new FetchWeiboStatuses();
		List<Status> reStatus = testObj.getPublicTimeline(200);

		for (Status status : reStatus) {
			System.out.println(status.getText());
		}
	}

	@Test
	public void testGetUserTimeline() {
		FetchWeiboStatuses testObj = new FetchWeiboStatuses();

		String screen_name = "ailab403";
		@SuppressWarnings("deprecation")
		List<Status> reStatus = testObj.getUserTimelineByName(screen_name, 0,
				0, 100, 3);

		for (Status status : reStatus) {
			System.out.println(status.getText());
		}
	}

	@Test
	public void testGetUserHomeTimeLine() {
		FetchWeiboStatuses testObj = new FetchWeiboStatuses();
		List<Status> reStatus = testObj.getHomeTimeLine(0, 0, 100, 1);

		for (Status status : reStatus) {
			System.out.print(status.getUser().getName() + " หตฃบ ");
			System.out.println(status.getText());
		}
	}
}

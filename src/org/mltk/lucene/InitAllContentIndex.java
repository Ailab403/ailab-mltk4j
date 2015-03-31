package org.mltk.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.lucene.analysis.Analyzer;
import org.mltk.lucene.cache.IndexDirectoryLocPath;

public class InitAllContentIndex {

	private List<File> fileList;
	private String allIndexPath;

	public void InitIndexProdData() {
		setAllIndexPath(IndexDirectoryLocPath.ALL_INDEX);

		File fileFolder = new File(IndexDirectoryLocPath.FILE_EXAMPLE);
		List<File> fileList = new ArrayList<File>();
		for (File f : fileFolder.listFiles()) {
			fileList.add(f);
		}
		setFileList(fileList);
	}

	/**
	 * 执行多线程创建索引
	 * 
	 * @param analyzer
	 */
	public void execInitContentIndexThread(Analyzer analyzer) {

		this.InitIndexProdData();

		// 创建线程池
		ExecutorService exes = Executors.newFixedThreadPool(5);
		Set<Future<Boolean>> setThreads = new HashSet<Future<Boolean>>();
		for (File file : getFileList()) {
			// 创建线程任务
			InitContentIndexThread initContentIndexThread = new InitContentIndexThread(
					file, getAllIndexPath(), analyzer);

			// 提交线程任务
			setThreads.add(exes.submit(initContentIndexThread));
		}
		// 执行多线程任务
		for (Future<Boolean> future : setThreads) {

			try {
				Boolean flagSucc = future.get();

				// TODO delete print
				// System.out.println(flagSucc);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	public String getAllIndexPath() {
		return allIndexPath;
	}

	public void setAllIndexPath(String allIndexPath) {
		this.allIndexPath = allIndexPath;
	}

}

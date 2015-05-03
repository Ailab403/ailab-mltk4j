package org.mltk.lucene.seg;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.lucene.analysis.Analyzer;

/**
 * 多线程分词，但是限定只能是单层文件夹中的所有文件，单个文件夹大规模文件数量的情况
 * 
 * @author superhy
 *
 */
public class TokenSegFolderFileMP {

	private Analyzer a;

	public TokenSegFolderFileMP(Analyzer a) {
		super();
		this.a = a;
	}

	public void execTokenSegThread(String oriFolderPath, String tagFolderPath) {

		// 预处理
		File oriFolder = new File(oriFolderPath);
		if (!oriFolder.exists()) {
			System.err.println("源文件夹不存在！");
			return;
		}
		File[] oriFolderFiles = oriFolder.listFiles();
		File tagFolder = new File(tagFolderPath);
		if (!tagFolder.exists()) {
			tagFolder.mkdirs();
		}

		// 建立线程池
		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Boolean>> setThreads = new HashSet<Future<Boolean>>();

		for (File oriFile : oriFolderFiles) {
			// 创建现成任务
			TokenSegFolderFileThread tokenSegFolderFileThread = new TokenSegFolderFileThread(
					oriFile, tagFolderPath, this.a);

			// 提交现成任务
			setThreads.add(exes.submit(tokenSegFolderFileThread));
		}

		// 执行多线程任务
		for (Future<Boolean> future : setThreads) {

			try {
				Boolean runFlag = future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Analyzer getA() {
		return a;
	}

	public void setA(Analyzer a) {
		this.a = a;
	}

}

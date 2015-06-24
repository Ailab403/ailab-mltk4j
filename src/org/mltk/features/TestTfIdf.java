package TIDF;

import java.io.*;
import java.util.*;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 分词-TFIDF-信息增益
 * @author LJ
 * 
 * @datetime 2015-6-15 
 */
public class TestTfIdf {
	public static final String stopWordTable = "C:/Users/zzw/Desktop/sc_ot-tingyongzhongwen_hc/stopWordTable.txt"; // 加载停用词库

	private static ArrayList<String> FileList = new ArrayList<String>(); // 文件列表

	// 递归读取该路径下文件返回文件列表
	public static List<String> readDirs(String filepath)
			throws FileNotFoundException, IOException {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("输入的[]");
				System.out.println("filepath:" + file.getAbsolutePath());
			} else {
				String[] flist = file.list();
				for (int i = 0; i < flist.length; i++) {
					File newfile = new File(filepath + "\\" + flist[i]);
					if (!newfile.isDirectory()) {
						FileList.add(newfile.getAbsolutePath());
					} else if (newfile.isDirectory()) {
						readDirs(filepath + "\\" + flist[i]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return FileList;
	}

	// 读入文件
	public static String readFile(String file) throws FileNotFoundException,
			IOException {
		StringBuffer strSb = new StringBuffer();
		InputStreamReader inStrR = new InputStreamReader(new FileInputStream(
				file), "gbk");
		BufferedReader br = new BufferedReader(inStrR);
		String line = br.readLine();
		while (line != null) {
			strSb.append(line).append("\r\n");
			line = br.readLine();
		}

		return strSb.toString();
	}

	// 分词处理
	public static ArrayList<String> cutWords(String file) throws IOException {

		ArrayList<String> fenci = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();
		String text = TestTfIdf.readFile(file);

		IKAnalyzer analyzer = new IKAnalyzer();
		fenci = analyzer.split(text); // 分词处理
		BufferedReader StopWordFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						stopWordTable))));
		// 用来存放停用词的集合
		Set<String> stopWordSet = new HashSet<String>();
		// 初如化停用词集
		String stopWord = null;
		for (; (stopWord = StopWordFileBr.readLine()) != null;) {
			stopWordSet.add(stopWord);
		}
		for (String word : fenci) {
			if (stopWordSet.contains(word)) {
				continue;
			}
			words.add(word);
		}
		System.out.println(words);
		return words;
	}

	// 统计一个文件中每个词出现的次数
	public static HashMap<String, Integer> normalTF(ArrayList<String> cutwords) {
		HashMap<String, Integer> resTF = new HashMap<String, Integer>();

		for (String word : cutwords) {
			if (resTF.get(word) == null) {
				resTF.put(word, 1);
				System.out.println(word);
			} else {
				resTF.put(word, resTF.get(word) + 1);
				System.out.println(word.toString());
			}
		}
		System.out.println(resTF);
		return resTF;
	}

	// 计算一个文件每个词tf值
	@SuppressWarnings("unchecked")
	public static HashMap<String, Float> tf(ArrayList<String> cutwords) {
		HashMap<String, Float> resTF = new HashMap<String, Float>();

		int wordLen = cutwords.size();
		HashMap<String, Integer> intTF = TestTfIdf.normalTF(cutwords);

		Iterator iter = intTF.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			resTF.put(entry.getKey().toString(), Float.parseFloat(entry
					.getValue().toString())
					/ wordLen);
			System.out.println(entry.getKey().toString() + " = "
					+ Float.parseFloat(entry.getValue().toString()) / wordLen);
		}
		return resTF;
	}

	// tf times for file 。。。。。。。
	public static HashMap<String, HashMap<String, Integer>> normalTFAllFiles(
			String dirc) throws IOException {
		FileList.clear();
		HashMap<String, HashMap<String, Integer>> allNormalTF = new HashMap<String, HashMap<String, Integer>>();

		List<String> filelist = TestTfIdf.readDirs(dirc);
		for (String file : filelist) {
			HashMap<String, Integer> dict = new HashMap<String, Integer>();
			ArrayList<String> cutwords = TestTfIdf.cutWords(file);
			dict = TestTfIdf.normalTF(cutwords);
			allNormalTF.put(file, dict);
		}
		return allNormalTF;
	}

	// 返回所有文件tf值
	public static HashMap<String, HashMap<String, Float>> tfAllFiles(String dirc)
			throws IOException {
		FileList.clear();
		HashMap<String, HashMap<String, Float>> allTF = new HashMap<String, HashMap<String, Float>>();
		List<String> filelist = TestTfIdf.readDirs(dirc);

		for (String file : filelist) {
			HashMap<String, Float> dict = new HashMap<String, Float>();
			ArrayList<String> cutwords = TestTfIdf.cutWords(file);
			dict = TestTfIdf.tf(cutwords);
			allTF.put(file, dict);
		}
		return allTF;
	}

	// 计算该目录下所有词的idf
	@SuppressWarnings("unchecked")
	public static HashMap<String, Float> idf(
			HashMap<String, HashMap<String, Float>> all_tf, String file)
			throws IOException {
		FileList.clear();
		HashMap<String, Float> resIdf = new HashMap<String, Float>();
		HashMap<String, Integer> dict = new HashMap<String, Integer>();
		int docNum = readDirs(file).size();
		for (int i = 0; i < docNum; i++) {
			HashMap<String, Float> temp = all_tf.get(FileList.get(i));
			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String word = entry.getKey().toString();
				if (dict.get(word) == null) {
					dict.put(word, 1);
				} else {
					dict.put(word, dict.get(word) + 1);
				}
			}
		}
		// 生成文件记录所有词和包含该词的文件数
		StringBuilder sb1 = new StringBuilder();
		Iterator iter1 = dict.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			if (entry.getKey().toString() != null) {
				sb1.append(entry.getKey().toString() + " "
						+ dict.get(entry.getKey()) + "\r\n");
			}
		}
		File filewriter = new File("E:/allCount.txt");
		FileWriter fw = new FileWriter(filewriter.getAbsoluteFile());
		BufferedWriter bb = new BufferedWriter(fw);
		bb.write(sb1.toString());
		bb.close();
		System.out.println(dict);
		// 计算idf
		System.out.println("IDF for every word is:");
		Iterator iter_dict = dict.entrySet().iterator();
		while (iter_dict.hasNext()) {
			Map.Entry entry = (Map.Entry) iter_dict.next();
			float value = (float) Math.log(docNum
					/ Float.parseFloat(entry.getValue().toString()));
			resIdf.put(entry.getKey().toString(), value);
			System.out.println(entry.getKey().toString() + " = " + value);
		}
		return resIdf;
	}

	// 返回该目录下所有词以及包含词的文件数
	@SuppressWarnings("unchecked")
	public static HashMap<String, Integer> idf_dict(
			HashMap<String, HashMap<String, Float>> all_tf, String file)
			throws IOException {
		FileList.clear();
		HashMap<String, Integer> dict = new HashMap<String, Integer>();
		List<String> filelist = readDirs(file);
		int docNum = filelist.size();

		for (int i = 0; i < docNum; i++) {
			HashMap<String, Float> temp = all_tf.get(filelist.get(i));
			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String word = entry.getKey().toString();
				if (dict.get(word) == null) {
					dict.put(word, 1);
				} else {
					dict.put(word, dict.get(word) + 1);
				}
			}
		}
		System.out.println(dict);
		return dict;
	}

	// 计算TFIDF值
	@SuppressWarnings("unchecked")
	public static void tf_idf(HashMap<String, HashMap<String, Float>> all_tf,
			HashMap<String, Float> idfs, String file) throws IOException {
		HashMap<String, HashMap<String, Float>> resTfIdf = new HashMap<String, HashMap<String, Float>>();
		FileList.clear();
		int docNum = readDirs(file).size();
		for (int i = 0; i < docNum; i++) {
			String filepath = FileList.get(i);
			HashMap<String, Float> tfidf = new HashMap<String, Float>();
			HashMap<String, Float> temp = all_tf.get(filepath);
			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String word = entry.getKey().toString();
				Float value = (float) Float.parseFloat(entry.getValue()
						.toString())
						* idfs.get(word);
				tfidf.put(word, value);
			}
			resTfIdf.put(filepath, tfidf);
		}
		System.out.println("TF-IDF for Every file is :");
		DisTfIdf(resTfIdf); // 显示TFIDF
	}

	// 返回计算的TFIDF值
	@SuppressWarnings("unchecked")
	public static HashMap<String, HashMap<String, Float>> tf_idf_return(
			HashMap<String, HashMap<String, Float>> all_tf,
			HashMap<String, Float> idfs, String file) throws IOException {
		FileList.clear();
		HashMap<String, HashMap<String, Float>> resTfIdf = new HashMap<String, HashMap<String, Float>>();
		int docNum = readDirs(file).size();
		for (int i = 0; i < docNum; i++) {
			@SuppressWarnings("unused")
			HashMap<String, Float> tfidf_reduce = new HashMap<String, Float>();
			String filepath = FileList.get(i);
			HashMap<String, Float> tfidf = new HashMap<String, Float>();
			HashMap<String, Float> temp = all_tf.get(filepath);
			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String word = entry.getKey().toString();
				Float value = (float) Float.parseFloat(entry.getValue()
						.toString())
						* idfs.get(word);
				tfidf.put(word, value);

			}
			resTfIdf.put(filepath, tfidf);
		}
		return resTfIdf;
	}

	// TFIDF显示输出 并建立文件存储该信息
	@SuppressWarnings("unchecked")
	public static void DisTfIdf(HashMap<String, HashMap<String, Float>> tfidf)
			throws IOException {
		StringBuilder stall = new StringBuilder();
		Iterator iter1 = tfidf.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entrys = (Map.Entry) iter1.next();
			System.out.println("FileName: " + entrys.getKey().toString());
			System.out.print("{");
			HashMap<String, Float> temp = (HashMap<String, Float>) entrys
					.getValue();
			Iterator iter2 = temp.entrySet().iterator();
			while (iter2.hasNext()) {
				Map.Entry entry = (Map.Entry) iter2.next();
				System.out.print(entry.getKey().toString() + " = "
						+ entry.getValue().toString() + ", ");
				stall.append(entrys.getKey().toString() + " "
						+ entry.getKey().toString() + " "
						+ entry.getValue().toString() + "\r\n");
			}
			System.out.println("}");
		}
		File filewriter = new File("E:/allTFIDF.txt");
		FileWriter fw = new FileWriter(filewriter.getAbsoluteFile());
		BufferedWriter bz = new BufferedWriter(fw);
		bz.write(stall.toString());
		bz.close();
	}

	// 单属性熵
	public static double Entropy(double[] p, double tot) {
		double entropy = 0.0;
		for (int i = 0; i < p.length; i++) {
			if (p[i] > 0.0) {
				entropy += -p[i] / tot * Math.log(p[i] / tot) / Math.log(2.0);
			}
		}
		return entropy;
	}

	// 信息增益特征降维
	@SuppressWarnings("unchecked")
	private static void Total(int N,
			HashMap<String, HashMap<String, Float>> result,
			HashMap<String, Integer> idfs_dict_neg,
			HashMap<String, Integer> idfs_dict_pos, String file)
			throws IOException {
		FileList.clear();
		double[] classCnt = new double[N]; // 类别数组
		double totalCnt = 0.0; // 总文件数
		for (int c = 0; c < N; c++) {
			classCnt[c] = 125; // 每个类别的文件数目
			totalCnt += classCnt[c];
		}
		int docNum = readDirs(file).size();
		int num = 0; // 词f的编号
		int numb = 0; // 词f的编号
		double totalEntroy = Entropy(classCnt, totalCnt); // 总的熵
		HashMap<String, Integer> count = new HashMap<String, Integer>();// 存储词及其编号
		HashMap<String, Integer> countG = new HashMap<String, Integer>();// 存储特征降维后word和其编号
		HashMap<String, Double> countG1 = new HashMap<String, Double>();// 存储特征降维后word和其信息增益
		HashMap<String, Double> infogains = new HashMap<String, Double>();// 存储词和该词的信息增益
		StringBuilder st = new StringBuilder();// 缓存文件名,词,信息增益,TFIDF
		StringBuilder ss = new StringBuilder();// 缓存未特征处理的类别,单词的编号,单词的TFIDF值
		StringBuilder sr = new StringBuilder();// 缓存经过特征处理后的类别,单词的编号,单词的TFIDF值
		for (int i = 0; i < docNum; i++) {
			String filepath = FileList.get(i);
			HashMap<String, Float> temp = result.get(filepath);
			Iterator iter = temp.entrySet().iterator();
			if (filepath.contains("dubo")) {
				ss.append(1 + "  "); // 将赌博类定义为类别1
			} else if (filepath.contains("fangdong")) {
				ss.append(2 + "  "); // 将反动类定义为类别2
			}
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String f = entry.getKey().toString();
				double[] featureCntWithF = new double[N]; // 包括词F的分布（类别1,2分别包含该词的文件数）
				double[] featureCntWithoutF = new double[N]; // 不包括词F的分布
				double totalCntWithF = 0.0; // 所有类别中包括词F的文件数
				double totalCntWithoutF = 0.0; // 所有类别中不包括词F的文件数
				for (int c = 0; c < N; c++) {
					Iterator iter_dict = null;
					switch (c) {
					case 0:
						iter_dict = idfs_dict_neg.entrySet().iterator();
						break;
					case 1:
						iter_dict = idfs_dict_pos.entrySet().iterator();
						break;
					}
					while (iter_dict.hasNext()) {
						Map.Entry entry_neg = (Map.Entry) iter_dict.next();
						if (f.equals(entry_neg.getKey().toString())) { // 该词在该类别中出现
							featureCntWithF[c] = Double.parseDouble(entry_neg
									.getValue().toString()); // 将该出现该词的文件数赋值给数组
							break;
						} else {
							featureCntWithF[c] = 0.0;
						}
					}
					featureCntWithoutF[c] = classCnt[c] - featureCntWithF[c]; // 不包括词F的文件数等于该类别总数减去包含该词的文件数
					totalCntWithF += featureCntWithF[c];
					totalCntWithoutF += featureCntWithoutF[c];
				}
				double entropyWithF = Entropy(featureCntWithF, totalCntWithF);
				double entropyWithoutF = Entropy(featureCntWithoutF,
						totalCntWithoutF);
				double wf = totalCntWithF / totalCnt;
				double infoGain = totalEntroy - wf * entropyWithF - (1.0 - wf) // 信息增益的公式
						* entropyWithoutF;
				infogains.put(f, infoGain);
				st.append(filepath + " " + f + " " + "信息增益" + "="
						+ infoGain // 缓存格式
						+ " " + "tfidf" + "=" + entry.getValue().toString()
						+ "\r\n");

				// }
				// 方式一：直接用阈值选取特征值可以省去下面再次遍历的过程
				// if(infogains.get(f)>0.004011587943125061){
				// 给词f编号
				if (count.get(f) == null) {
					num++;
					count.put(f, num);
				}
				ss.append(count.get(f) + ":" + entry.getValue() + " "); // 缓存格式
				// }
			}
			ss.append("\r\n");
		}
		File fileprepare = new File("E:/test.txt");
		FileWriter fz = new FileWriter(fileprepare.getAbsoluteFile());
		BufferedWriter bz = new BufferedWriter(fz);
		bz.write(ss.toString());
		bz.close();
		File filewriter = new File("E:/jieguo.txt");
		FileWriter fw = new FileWriter(filewriter.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(st.toString());
		bw.close();
		// 方式二：将信息增益从大到小排列,选取前特定数的词为特征词
		// 对信息增益排序（从大到小）
		ArrayList<Map.Entry<String, Double>> infoIds = new ArrayList<Map.Entry<String, Double>>(
				infogains.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				if (o2.getValue() - o1.getValue() > 0) {
					return 1; // 降序排列
				} else {
					return -1;
				}
			}
		});
		// 选取信息增益为前2000的词做特征词
		for (int c = 0; c < 2000; c++) {
			countG1.put(infoIds.get(c).getKey(), infoIds.get(c).getValue()); // 将处理后的数据存储到countG1中
		}
		// 再次遍历
		for (int i = 0; i < docNum; i++) {
			String filepath = FileList.get(i);
			HashMap<String, Float> temp = result.get(filepath);
			Iterator iter = temp.entrySet().iterator();
			if (filepath.contains("dubo")) {
				sr.append(1 + "  ");
			} else if (filepath.contains("fangdong")) {
				sr.append(2 + "  ");
			}
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				// for(Iterator<Feature>
				// i=index.featureIterator();i.hasNext();){
				String f = entry.getKey().toString();
				// 判断该词是特征降维后的那些词
				if (countG1.get(f) != null) {
					// 给该词编号
					if (countG.get(f) == null) {
						numb++;
						countG.put(f, numb);
					}
					sr.append(countG.get(f) + ":" + entry.getValue() + " ");
				}
			}
			sr.append("\r\n");
		}
		File fileprepare1 = new File("E:/testt.txt");
		FileWriter fr = new FileWriter(fileprepare1.getAbsoluteFile());
		BufferedWriter br = new BufferedWriter(fr);
		br.write(sr.toString());
		br.close();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String file = "C:/Users/zzw/Desktop/项目管理/语料/test"; // 总的数据路径
		String file1 = "C:/Users/zzw/Desktop/项目管理/语料/test/赌博"; // 类1数据路径
		String file2 = "C:/Users/zzw/Desktop/项目管理/语料/test/反动"; // 类2数据路径
		HashMap<String, HashMap<String, Float>> all_tf = tfAllFiles(file);
		HashMap<String, HashMap<String, Float>> all_tf_neg = tfAllFiles(file1); // file1文件的tf值和路径
		HashMap<String, HashMap<String, Float>> all_tf_pos = tfAllFiles(file2); // file2文件的tf值和路径
		System.out.println();
		HashMap<String, Integer> idfs_dict_neg = idf_dict(all_tf_neg, file1); // 返回file1下所有词以及包含词的文件数
		HashMap<String, Integer> idfs_dict_pos = idf_dict(all_tf_pos, file2); // 返回file2下所有词以及包含词的文件数
		HashMap<String, Float> idfs = idf(all_tf, file);
		System.out.println();
		tf_idf(all_tf, idfs, file);
		HashMap<String, HashMap<String, Float>> result = tf_idf_return(all_tf,
				idfs, file);
		int N = 2; // 输入类别数
		/*
		 * 信息增益公式 IG(T)=H(C)-H(C|T) H(C|T)=P(t)H(C|t)+P(t')H(C|t‘)
		 */
		Total(N, result, idfs_dict_neg, idfs_dict_pos, file); // 按信息增益进行特征降维

	}

}
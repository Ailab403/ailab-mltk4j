package org.mltk.mahout.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class ClusterPreUtil {

	public static void writePointsToFile(List<Vector> points, String fileName,
			FileSystem fs, Configuration conf) throws IOException {

		Path path = new Path(fileName);
		SequenceFile.Writer writer = new Writer(fs, conf, path,
				LongWritable.class, VectorWritable.class);
		long recNum = 0;

		VectorWritable vec = new VectorWritable();
		for (Vector point : points) {
			vec.set(point);
			writer.append(new LongWritable(recNum++), vec);
		}

		writer.close();
	}

	public static List<Vector> getPoints(Integer vecTypeNum, double[][] raw) {

		List<Vector> points = new ArrayList<Vector>();
		for (int i = 0; i < raw.length; i++) {
			double[] fr = raw[i];

			// 传入的第一个数字是向量空间的宽度
			Vector vec = DataVectorFactory.prodVector(fr.length, vecTypeNum);
			vec.assign(fr);
			points.add(vec);
		}

		return points;
	}
}

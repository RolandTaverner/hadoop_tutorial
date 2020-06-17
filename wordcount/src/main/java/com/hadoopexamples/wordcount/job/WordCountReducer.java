package com.hadoopexamples.wordcount.job;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int totalCount = 0;

		for (final IntWritable value : values) {
			totalCount += value.get();
		}

		m_totalCount.set(totalCount);
		context.write(key, m_totalCount);
	}

	private IntWritable m_totalCount = new IntWritable();
}

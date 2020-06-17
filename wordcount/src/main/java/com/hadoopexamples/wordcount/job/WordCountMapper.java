package com.hadoopexamples.wordcount.job;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

	@Override
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		final String line = value.toString();
		final String alphaNumericLine = line.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase();
		StringTokenizer tokenizer = new StringTokenizer(alphaNumericLine);

		while (tokenizer.hasMoreTokens()) {
			final String word = tokenizer.nextToken();

			m_word.set(word);
			m_count.set(1);
			context.write(m_word, m_count);
		}
	}

	private Text m_word = new Text();
	private IntWritable m_count = new IntWritable(0);
}

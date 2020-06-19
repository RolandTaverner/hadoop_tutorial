package com.hadoopexamples.wordcount.job;

import java.io.IOException;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import com.hadoopexamples.wordcount.App;

public class WordCountJob implements Configurable {

	public WordCountJob(Configuration conf) {
		setConf(conf);
	}

	public Job createJob() throws IOException {
		Job job = Job.getInstance(getConf(), "Sample WordCount job");

		job.setJarByClass(App.class);

		job.setMapperClass(WordCountMapper.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		TextInputFormat.setInputPaths(job, new Path(getConf().get("com.hadoopexamples.wordcount.in")));

		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		final Path outPath = (new Path(getConf().get("com.hadoopexamples.wordcount.out")));
		TextOutputFormat.setOutputPath(job, outPath);

		job.setNumReduceTasks(2);

		return job;
	}

	@Override
	public Configuration getConf() {
		return m_configuration;
	}

	@Override
	public void setConf(Configuration conf) {
		m_configuration = conf;
	}

	private Configuration m_configuration;
}

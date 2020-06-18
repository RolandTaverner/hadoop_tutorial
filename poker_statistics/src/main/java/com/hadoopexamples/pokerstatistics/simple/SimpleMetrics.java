package com.hadoopexamples.pokerstatistics.simple;

import java.io.IOException;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.hadoopexamples.pokerstatistics.App;
import com.hadoopexamples.pokerstatistics.taggedmetrics.common.IJobFactory;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricKey;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricValue;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.TextArrayWritable;

public class SimpleMetrics implements Configurable, IJobFactory {

	public SimpleMetrics(Configuration conf) {
		setConf(conf);
	}

	@Override
	public Job createJob() throws IOException {
		Job job = Job.getInstance(getConf(), "Simple metrics");

		job.setJarByClass(App.class);

		job.setMapperClass(Mapper.class);
		job.setMapOutputKeyClass(MRMetricKey.class);
		job.setMapOutputValueClass(MRMetricValue.class);
		job.setInputFormatClass(TextInputFormat.class);
		TextInputFormat.setInputPaths(job, new Path(getConf().get("com.hadoopexamples.pokerstatistics.in")));

		//job.setSortComparatorClass(KeyTagSortComparator.class);
		//job.setGroupingComparatorClass(KeyTagGroupingComparator.class);
		//job.setPartitionerClass(KeyTagPartitioner.class);

		job.setReducerClass(Reducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(TextArrayWritable.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		final Path outPath = (new Path(getConf().get("com.hadoopexamples.pokerstatistics.out"))).suffix("/simple");
		TextOutputFormat.setOutputPath(job, outPath);

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

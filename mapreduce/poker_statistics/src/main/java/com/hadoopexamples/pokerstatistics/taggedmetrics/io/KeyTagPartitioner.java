package com.hadoopexamples.pokerstatistics.taggedmetrics.io;

import org.apache.hadoop.mapreduce.Partitioner;

public class KeyTagPartitioner extends Partitioner<MRMetricKey, MRMetricValue> {

	@Override
	public int getPartition(MRMetricKey key, MRMetricValue value, int N)
	{
		final String realKey = key.getName();

		return java.lang.Math.abs(realKey.hashCode() % N);
	}
}

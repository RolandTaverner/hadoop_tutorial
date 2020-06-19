package com.hadoopexamples.pokerstatistics.taggedmetrics;

import java.io.IOException;

import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricValue;

public interface IMetricReducer {
	public void reduce(final String metricName, Iterable<MRMetricValue> values, IReducerSink sink)
			throws IOException, InterruptedException;
}

package com.hadoopexamples.pokerstatistics.taggedmetrics;

import java.io.IOException;
import java.util.SortedMap;

import com.hadoopexamples.pokerstatistics.metrics.mr.MetricValue;

public interface IReducerSink {
	public void write(final String metricName, final SortedMap<Integer, String> tags, final MetricValue value) throws IOException, InterruptedException;
}

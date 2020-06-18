package com.hadoopexamples.pokerstatistics.taggedmetrics;

public interface IMetric {
	public String name();

	public IMetricMapper getMapper();
	public IMetricReducer getReducer();
}

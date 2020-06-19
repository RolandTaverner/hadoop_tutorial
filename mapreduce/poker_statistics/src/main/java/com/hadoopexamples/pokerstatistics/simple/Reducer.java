package com.hadoopexamples.pokerstatistics.simple;

import java.util.HashMap;
import java.util.Map;

import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetric;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetricReducer;
import com.hadoopexamples.pokerstatistics.taggedmetrics.ReducerBase;

public class Reducer extends ReducerBase {
	public Reducer() {
		super(m_metricReducers);
	}

	private static final Map<String, IMetricReducer> m_metricReducers = new HashMap<String, IMetricReducer>();
	static {
		for (IMetric m : MetricsFactory.getMetrics()) {
			m_metricReducers.put(m.name(), m.getReducer());
		}
	}
}

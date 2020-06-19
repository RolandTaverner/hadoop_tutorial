package com.hadoopexamples.pokerstatistics.playeractivitydetails;

import java.util.ArrayList;
import java.util.Collection;

import com.hadoopexamples.pokerstatistics.playeractivitydetails.metrics.ActivityDetails;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetric;

public class MetricsFactory {
	public static Collection<IMetric> getMetrics() {
		return m_metrics;
	}
	
	private static final Collection<IMetric> m_metrics = new ArrayList<IMetric>();
	static {
		m_metrics.add(new ActivityDetails());
	}
}

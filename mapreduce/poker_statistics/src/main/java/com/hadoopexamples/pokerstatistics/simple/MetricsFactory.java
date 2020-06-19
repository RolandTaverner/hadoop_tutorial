package com.hadoopexamples.pokerstatistics.simple;

import java.util.ArrayList;
import java.util.Collection;

import com.hadoopexamples.pokerstatistics.simple.metrics.HandsDealt;
import com.hadoopexamples.pokerstatistics.simple.metrics.RGPlayers;
import com.hadoopexamples.pokerstatistics.simple.metrics.RakedHands;
import com.hadoopexamples.pokerstatistics.simple.metrics.TotalRGRake;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetric;

public class MetricsFactory {
	public static Collection<IMetric> getMetrics() {
		return m_metrics;
	}
	
	private static final Collection<IMetric> m_metrics = new ArrayList<IMetric>();
	static {
		m_metrics.add(new HandsDealt());
		m_metrics.add(new TotalRGRake());
		m_metrics.add(new RGPlayers());
		m_metrics.add(new RakedHands());
	}
	
}

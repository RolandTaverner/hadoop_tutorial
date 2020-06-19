package com.hadoopexamples.pokerstatistics.playeractivitydetails;

import java.util.ArrayList;
import java.util.Collection;

import com.hadoopexamples.pokerstatistics.playeractivitydetails.MetricsFactory;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetric;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetricMapper;
import com.hadoopexamples.pokerstatistics.taggedmetrics.MapperBase;

public class Mapper extends MapperBase {
	public Mapper() {
		super(m_metricMappers);
	}

	private static final Collection<IMetricMapper> m_metricMappers = new ArrayList<IMetricMapper>(MetricsFactory.getMetrics().size());
	static {
		for (IMetric m : MetricsFactory.getMetrics()) {
			m_metricMappers.add(m.getMapper());
		}
	}
}

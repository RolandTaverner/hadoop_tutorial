package com.hadoopexamples.pokerstatistics.taggedmetrics;

import java.io.IOException;
import java.util.SortedMap;

import org.apache.hadoop.mapreduce.Mapper;

import com.hadoopexamples.pokerstatistics.metrics.mr.MetricValue;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricKey;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricValue;

import org.apache.hadoop.io.Text;

public class MapperSink implements IMapperSink {

	public MapperSink(Mapper<Object, Text, MRMetricKey, MRMetricValue>.Context context) {
		m_context = context;
	}

	private final Mapper<Object, Text, MRMetricKey, MRMetricValue>.Context m_context;

	@Override
	public void write(final String metricName, SortedMap<Integer, String> tags, final MetricValue value) throws IOException, InterruptedException {
		final MRMetricKey mrKey = new MRMetricKey(metricName, tags);
		final MRMetricValue mrValue = new MRMetricValue(metricName, tags, value); 
		m_context.write(mrKey, mrValue);
	}

}

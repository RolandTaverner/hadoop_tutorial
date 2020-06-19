package com.hadoopexamples.pokerstatistics.taggedmetrics;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricKey;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricValue;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.TextArrayWritable;

public abstract class ReducerBase extends Reducer<MRMetricKey, MRMetricValue, NullWritable, TextArrayWritable> {

	public ReducerBase(Map<String, IMetricReducer> metricReducers) {
		for (Map.Entry<String, IMetricReducer> e : metricReducers.entrySet()) {
			m_metricReducers.put(e.getKey(), e.getValue());
		}
	}

	public void reduce(MRMetricKey key, Iterable<MRMetricValue> values, Context context)
			throws IOException, InterruptedException {
		final String metricName = key.getName();

		if (!m_metricReducers.containsKey(metricName)) {
			// TODO: log this
			return;
		}
		final IReducerSink sink = new ReducerSink(context);
		m_metricReducers.get(metricName).reduce(metricName, values, sink);
	}

	private final Map<String, IMetricReducer> m_metricReducers = new HashMap<String, IMetricReducer>();
}

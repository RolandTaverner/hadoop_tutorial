package com.hadoopexamples.pokerstatistics.taggedmetrics;

import java.io.IOException;
import java.util.SortedMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.hadoopexamples.pokerstatistics.metrics.mr.MetricValue;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricKey;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricValue;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.TextArrayWritable;

public class ReducerSink implements IReducerSink {

	public ReducerSink(Reducer<MRMetricKey, MRMetricValue, NullWritable, TextArrayWritable>.Context context) {
		m_context = context;
	}

	private final Reducer<MRMetricKey, MRMetricValue, NullWritable, TextArrayWritable>.Context m_context;

	@Override
	public void write(final String metricName, final SortedMap<Integer, String> tags, MetricValue value)
			throws IOException, InterruptedException {
		final Text[] strings = new Text[tags.size() + 2];
		strings[0] = new Text(metricName);
		int i = 1;
		for (final String tagValue : tags.values()) {
			strings[i++] = new Text(tagValue);
		}

		if (value.hasBoolValue()) {
			strings[i] = new Text(String.valueOf(value.getBoolValue() ? 1 : 0));
		} else if (value.hasIntValue()) {
			strings[i] = new Text(String.valueOf(value.getIntValue()));
		} else if (value.hasDoubleValue()) {
			strings[i] = new Text(String.valueOf(value.getDoubleValue()));
		} else {
			strings[i] = new Text("");
		}

		final TextArrayWritable out = new TextArrayWritable(strings);
		m_context.write(NullWritable.get(), out);
	}

}

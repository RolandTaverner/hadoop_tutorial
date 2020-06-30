package com.hadoopexamples.pokerstatistics.simple.metrics;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import com.hadoopexamples.pokerstatistics.metrics.mr.MetricValue;
import com.hadoopexamples.pokerstatistics.simple.Tag;
import com.hadoopexamples.pokerstatistics.simple.TagsMaker;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMapperSink;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetric;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetricMapper;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetricReducer;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IReducerSink;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricValue;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.TagsComparator;
import com.hadoopexamples.pokerstatistics.handshistory.Hand;

public class HandsDealt implements IMetric {

	@Override
	public IMetricMapper getMapper() {
		return m_mapper;
	}

	@Override
	public IMetricReducer getReducer() {
		return m_reducer;
	}

	@Override
	public String name() {
		return m_name;
	}

	private static final String m_name = "HandsDealt";
	private static final Mapper m_mapper = new Mapper();
	private static final Reducer m_reducer = new Reducer();

	public static class Mapper implements IMetricMapper {

		@Override
		public void map(final Hand hand, IMapperSink sink) throws IOException, InterruptedException {
			sink.write(m_name, TagsMaker.makeTags(hand), MetricValue.newBuilder().setIntValue(1).build());
		}

	}
	
	public static class Reducer implements IMetricReducer {
		@Override
		public void reduce(final String metricName, Iterable<MRMetricValue> values, IReducerSink sink)
				throws IOException, InterruptedException {

			SortedMap<Integer, String> currentTags = null;
			long count = 0;
			
			for (final MRMetricValue mrValue : values) {
				if (currentTags == null) {
					currentTags = new TreeMap<Integer, String>(mrValue.getTags());
					count = mrValue.getValue().getIntValue();
					continue;
				}
				
				if (TagsComparator.compare(currentTags, mrValue.getTags()) != 0) {
					// emit current value
					sink.write(metricName, currentTags, MetricValue.newBuilder().setIntValue(count).build());

					// start new group
					currentTags = new TreeMap<Integer, String>(mrValue.getTags());
					count = mrValue.getValue().getIntValue();
					continue;
				}
				count += mrValue.getValue().getIntValue();
			}
			
			if (currentTags != null) {
				// emit last value
				sink.write(metricName, currentTags, MetricValue.newBuilder().setIntValue(count).build());
			}
			
		}

	}

}

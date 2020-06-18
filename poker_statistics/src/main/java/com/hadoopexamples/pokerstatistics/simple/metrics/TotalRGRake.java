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

public class TotalRGRake implements IMetric {

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

	private static final String m_name = "TotalRGRake";
	private static final Mapper m_mapper = new Mapper();
	private static final Reducer m_reducer = new Reducer();

	public static class Mapper implements IMetricMapper {

		@Override
		public void map(final Hand hand, IMapperSink sink) throws IOException, InterruptedException {
			final double rake = hand.getSummary().getRake();
			if (rake != 0.0) {
				sink.write(m_name, TagsMaker.makeTags(hand), MetricValue.newBuilder().setDoubleValue(rake).build());
			}
		}

	}

	public static class Reducer implements IMetricReducer {

		@Override
		public void reduce(final String metricName, Iterable<MRMetricValue> values, IReducerSink sink)
				throws IOException, InterruptedException {
			SortedMap<Integer, String> currentTags = null;
			double rake = 0.0;

			for (final MRMetricValue mrValue : values) {
				if (currentTags == null) {
					currentTags = new TreeMap<Integer, String>(mrValue.getTags());
					rake = mrValue.getValue().getDoubleValue();
					continue;
				}

				if (TagsComparator.compare(currentTags, mrValue.getTags()) != 0) {
					// emit current value
					sink.write(metricName, currentTags, MetricValue.newBuilder().setDoubleValue(rake).build());

					// TEMP: fake PLO, MTT
					if (TagsMaker.getGameType(currentTags).equals("normal")) {
						SortedMap<Integer, String> ploTags = new TreeMap<Integer, String>(currentTags);
						ploTags.put(Tag.GAME_VARIANT.index(), "PLO");
						sink.write(metricName, ploTags, MetricValue.newBuilder().setIntValue((long)(rake*0.05)).build());	

						SortedMap<Integer, String> mttTags = new TreeMap<Integer, String>(currentTags);
						mttTags.put(Tag.GAME_VARIANT.index(), "MTT");
						sink.write(metricName, mttTags, MetricValue.newBuilder().setIntValue((long)(rake*0.03)).build());	
					}
					
					// start new group
					currentTags = new TreeMap<Integer, String>(mrValue.getTags());
					rake = mrValue.getValue().getDoubleValue();
					continue;
				}
				rake += mrValue.getValue().getDoubleValue();
			}

			if (currentTags != null) {
				// emit last value
				sink.write(metricName, currentTags, MetricValue.newBuilder().setDoubleValue(rake).build());
				
				// TEMP: fake PLO, MTT
				if (TagsMaker.getGameType(currentTags).equals("normal")) {
					SortedMap<Integer, String> ploTags = new TreeMap<Integer, String>(currentTags);
					ploTags.put(Tag.GAME_VARIANT.index(), "PLO");
					sink.write(metricName, ploTags, MetricValue.newBuilder().setIntValue((long)(rake*0.05)).build());	

					SortedMap<Integer, String> mttTags = new TreeMap<Integer, String>(currentTags);
					mttTags.put(Tag.GAME_VARIANT.index(), "MTT");
					sink.write(metricName, mttTags, MetricValue.newBuilder().setIntValue((long)(rake*0.03)).build());	
				}
			}
		}

	}

}

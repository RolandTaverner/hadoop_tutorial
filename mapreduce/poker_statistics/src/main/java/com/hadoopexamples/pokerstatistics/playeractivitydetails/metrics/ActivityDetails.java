package com.hadoopexamples.pokerstatistics.playeractivitydetails.metrics;

import java.io.IOException;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import com.hadoopexamples.pokerstatistics.metrics.mr.MetricValue;
import com.hadoopexamples.pokerstatistics.playeractivitydetails.Tag;
import com.hadoopexamples.pokerstatistics.playeractivitydetails.TagsMaker;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMapperSink;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetric;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetricMapper;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IMetricReducer;
import com.hadoopexamples.pokerstatistics.taggedmetrics.IReducerSink;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricValue;
import com.hadoopexamples.pokerstatistics.handshistory.Hand;
import com.hadoopexamples.pokerstatistics.handshistory.PlayerRake;

public class ActivityDetails implements IMetric {
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

	private static final String m_name = "PlayerActivityDetails";
	private static final Mapper m_mapper = new Mapper();
	private static final Reducer m_reducer = new Reducer();

	public static class Mapper implements IMetricMapper {

		@Override
		public void map(final Hand hand, IMapperSink sink) throws IOException, InterruptedException {
			for (final PlayerRake pr : hand.getSummary().getPlayerRakeList()) {
				if (pr.getAmount() != 0.0) {
					sink.write(m_name, TagsMaker.makeTags(hand, pr.getPlayerId()), m_emptyValue);
				}
			}
		}
	}

	public static class Reducer implements IMetricReducer {

		@Override
		public void reduce(final String metricName, Iterable<MRMetricValue> values, IReducerSink sink)
				throws IOException, InterruptedException {

			SortedMap<Integer, String> currentTags = null;
			long count = 0;

			for (final MRMetricValue mrValue : values) {
				currentTags = mrValue.getTags();
				++count;
			}

			if (count != 0) {
				// emit last value
				sink.write(metricName, currentTags, m_emptyValue);

				// TEMP: fake PLO, MTT
				if (TagsMaker.getGameType(currentTags).equals("normal")) {
					if (m_rnd.nextDouble() > 1.0 - 0.05) {
						SortedMap<Integer, String> ploTags = new TreeMap<Integer, String>(currentTags);
						ploTags.put(Tag.GAME_VARIANT.index(), "PLO");
						sink.write(metricName, ploTags, m_emptyValue);
					}
					if (m_rnd.nextDouble() > 1.0 - 0.03) {

						SortedMap<Integer, String> mttTags = new TreeMap<Integer, String>(currentTags);
						mttTags.put(Tag.GAME_VARIANT.index(), "MTT");
						sink.write(metricName, mttTags, m_emptyValue);
					}
				}
			}
		}

		private Random m_rnd = new Random();
	}

	private static final MetricValue m_emptyValue = MetricValue.newBuilder().build();
}

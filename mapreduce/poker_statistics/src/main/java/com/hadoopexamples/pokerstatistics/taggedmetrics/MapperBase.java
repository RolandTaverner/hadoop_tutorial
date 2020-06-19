package com.hadoopexamples.pokerstatistics.taggedmetrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collection;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricKey;
import com.hadoopexamples.pokerstatistics.taggedmetrics.io.MRMetricValue;
import com.hadoopexamples.pokerstatistics.handshistory.Hand;

public abstract class MapperBase extends Mapper<Object, Text, MRMetricKey, MRMetricValue> {

	public MapperBase(Collection<IMetricMapper> metricMappers) {
		m_metricMappers.addAll(metricMappers);
	}

	@Override
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		Decoder decoder = Base64.getDecoder();
		final Hand hand = Hand.parseFrom(decoder.decode(value.toString()));
		final IMapperSink sink = new MapperSink(context);

		for (IMetricMapper metricMapper : m_metricMappers) {
			metricMapper.map(hand, sink);
		}
	}

	private final Collection<IMetricMapper> m_metricMappers = new ArrayList<IMetricMapper>();
}

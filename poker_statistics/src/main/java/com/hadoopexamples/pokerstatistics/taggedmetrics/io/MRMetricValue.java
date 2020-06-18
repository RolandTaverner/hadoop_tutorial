package com.hadoopexamples.pokerstatistics.taggedmetrics.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.io.Writable;

import com.hadoopexamples.pokerstatistics.metrics.mr.MetricTagsValue;
import com.hadoopexamples.pokerstatistics.metrics.mr.MetricValue;
import com.hadoopexamples.pokerstatistics.metrics.mr.Tag;

public class MRMetricValue implements Writable {

	public MRMetricValue() {
	}

	public MRMetricValue(String name, Map<Integer, String> tags, MetricValue value) {
		m_name = name;
		for (final Map.Entry<Integer, String> entry : tags.entrySet()) {
			m_tags.put(entry.getKey(), entry.getValue());
		}
		m_value = MetricValue.newBuilder(value).build();
	}

	public String getName() {
		return m_name;
	}

	public void setName(String m_name) {
		this.m_name = m_name;
	}

	public SortedMap<Integer, String> getTags() {
		return m_tags;
	}

	public MetricValue getValue() {
		return m_value;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		int len = in.readInt();
		final byte[] buf = new byte[len];
		in.readFully(buf);
		final MetricTagsValue v = MetricTagsValue.parseFrom(buf);
		setName(v.getName());
		m_value = MetricValue.newBuilder(v.getValue()).build();

		m_tags.clear();
		for (final Tag t : v.getTagsList()) {
			m_tags.put(t.getKey(), t.getValue());
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		Collection<Tag> tags = new ArrayList<Tag>(m_tags.size());
		for (final Map.Entry<Integer, String> entry : m_tags.entrySet()) {
			tags.add(Tag.newBuilder().setKey(entry.getKey()).setValue(entry.getValue()).build());
		}
		final MetricTagsValue v = MetricTagsValue.newBuilder().setName(getName()).setValue(m_value).addAllTags(tags)
				.build();
		final byte[] bytes = v.toByteArray();

		out.writeInt(bytes.length);
		out.write(bytes);
	}

	private String m_name;
	private SortedMap<Integer, String> m_tags = new TreeMap<Integer, String>();
	private MetricValue m_value = null;
}

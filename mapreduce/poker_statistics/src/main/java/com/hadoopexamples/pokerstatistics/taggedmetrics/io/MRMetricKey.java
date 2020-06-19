package com.hadoopexamples.pokerstatistics.taggedmetrics.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.io.WritableComparable;

public class MRMetricKey implements WritableComparable<MRMetricKey> {

	public MRMetricKey() {
	}

	public MRMetricKey(String name, Map<Integer, String> tags) {
		m_name = name;
		for (final Map.Entry<Integer, String> entry : tags.entrySet()) {
			m_tags.put(entry.getKey(), entry.getValue());
		}
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

	private String m_name;
	private SortedMap<Integer, String> m_tags = new TreeMap<Integer, String>();

	@Override
	public void readFields(DataInput in) throws IOException {
		setName(in.readUTF());

		m_tags.clear();
		final int len = in.readInt();
		for (int i = 0; i < len; ++i) {
			final int key = in.readInt();
			final String val = in.readUTF();
			m_tags.put(key, val);
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(getName());
		out.writeInt(m_tags.size());
		for (final Map.Entry<Integer, String> entry : m_tags.entrySet()) {
			out.writeInt(entry.getKey().intValue());
			out.writeUTF(entry.getValue());
		}
	}

	@Override
	public int compareTo(MRMetricKey rhs) {
		final int nameRes = m_name.compareTo(rhs.m_name);
		if (nameRes != 0) {
			return nameRes;
		}

		return TagsComparator.compare(m_tags, rhs.m_tags);
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) {
	        return true;
	    }
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof MRMetricKey)) {
	    	return false;
	    }

		MRMetricKey rhs = (MRMetricKey) o;
		return compareTo(rhs) == 0;
	}
	
	@Override
	public int hashCode() {
		return m_name.hashCode() * 163 + m_tags.hashCode();
	}
	
}

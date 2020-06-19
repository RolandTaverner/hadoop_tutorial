package com.hadoopexamples.pokerstatistics.taggedmetrics.io;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class KeyTagGroupingComparator extends WritableComparator {
	public KeyTagGroupingComparator() {
		super(MRMetricKey.class, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable lhs, WritableComparable rhs) {
		final MRMetricKey lhsKey = (MRMetricKey) lhs;
		final MRMetricKey rhsKey = (MRMetricKey) rhs;

		return lhsKey.getName().compareTo(rhsKey.getName());
	}
}

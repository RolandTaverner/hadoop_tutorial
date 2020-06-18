package com.hadoopexamples.pokerstatistics.taggedmetrics.io;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class KeyTagSortComparator extends WritableComparator {

	public KeyTagSortComparator() {
		super(MRMetricKey.class, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable lhs, WritableComparable rhs) {
		final MRMetricKey lhsKey = (MRMetricKey) lhs;
		final MRMetricKey rhsKey = (MRMetricKey) rhs;

		return lhsKey.compareTo(rhsKey);
	}
}

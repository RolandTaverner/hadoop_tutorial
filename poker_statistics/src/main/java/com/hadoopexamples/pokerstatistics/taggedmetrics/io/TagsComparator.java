package com.hadoopexamples.pokerstatistics.taggedmetrics.io;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class TagsComparator {
	public static int compare(final SortedMap<Integer, String> lhs, final SortedMap<Integer, String> rhs) {
		final Set<Map.Entry<Integer, String>> lhsEntries = lhs.entrySet();
		final Set<Map.Entry<Integer, String>> rhsEntries = rhs.entrySet();

		Iterator<Map.Entry<Integer, String>> lhsIt = lhsEntries.iterator();
		Iterator<Map.Entry<Integer, String>> rhsIt = rhsEntries.iterator();

		while (lhsIt.hasNext() || rhsIt.hasNext()) {
			if (!lhsIt.hasNext() && rhsIt.hasNext()) {
				return -1;
			} else if (lhsIt.hasNext() && !rhsIt.hasNext()) {
				return 1;
			}

			final Map.Entry<Integer, String> lhsEntry = lhsIt.next();
			final Map.Entry<Integer, String> rhsEntry = rhsIt.next();

			final int keyRes = lhsEntry.getKey().compareTo(rhsEntry.getKey());
			if (keyRes != 0) {
				return keyRes;
			}

			final int valueRes = lhsEntry.getValue().compareTo(rhsEntry.getValue());
			if (valueRes != 0) {
				return valueRes;
			}
		}

		return 0;
	}
}

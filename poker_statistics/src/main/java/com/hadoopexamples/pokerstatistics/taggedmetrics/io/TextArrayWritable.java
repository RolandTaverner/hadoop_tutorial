package com.hadoopexamples.pokerstatistics.taggedmetrics.io;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class TextArrayWritable extends ArrayWritable {
	public TextArrayWritable() {
		super(Text.class);
	}

	public TextArrayWritable(Text[] strings) {
		super(Text.class, strings);
	}

	@Override
	public String toString() {
		Writable[] array = this.get();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			s.append(array[i] + "\t");
		}

		return s.toString();
	}
}
package com.hadoopexamples.pokerstatistics.taggedmetrics;

import java.io.IOException;

import com.hadoopexamples.pokerstatistics.handshistory.Hand;

public interface IMetricMapper {
	public void map(final Hand hand, IMapperSink sink) throws IOException, InterruptedException;
}

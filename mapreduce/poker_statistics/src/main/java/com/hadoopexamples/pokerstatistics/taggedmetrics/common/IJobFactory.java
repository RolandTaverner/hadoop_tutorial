package com.hadoopexamples.pokerstatistics.taggedmetrics.common;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;

public interface IJobFactory
{
	public Job createJob() throws IOException;
}
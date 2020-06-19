package com.hadoopexamples.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.hadoopexamples.wordcount.job.WordCountJob;

public class App extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new App(), args);
		System.exit(res);
	}

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			printUsage("Wrong number of arguments: " + args.length);
			System.exit(-1);
		}
		getConf().set("com.hadoopexamples.wordcount.in", args[0]);
		getConf().set("com.hadoopexamples.wordcount.out", args[1]);

		WordCountJob wordCountJob = new WordCountJob(getConf());
		Job job = wordCountJob.createJob();
		if (!job.waitForCompletion(true)) {
			return 1;
		}

		return 0;
	}

	private static void printUsage(final String errorMessage) {
		if (errorMessage != null && !errorMessage.isEmpty()) {
			System.err.println("ERROR: " + errorMessage);
		}
		System.err.println("Required args: <in_path> <out_path>");
		ToolRunner.printGenericCommandUsage(System.err);
	}
}
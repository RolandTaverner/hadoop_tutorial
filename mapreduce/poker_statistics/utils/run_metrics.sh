#!/bin/bash

HADOOP_ROOT=$1
INDIR=$2
OUTDIR=$3

for dir in $($HADOOP_ROOT/bin/hadoop fs -ls $INDIR | sed "s/^.*hdfs:/hdfs:/g" | grep 'hdfs:'); do
  echo "Processing $dir"
  date=$(echo $dir | grep -oE '[0-9][0-9][0-9][0-9]\.[0-9][0-9]\.[0-9][0-9]$')
  out=$OUTDIR/$date
  $HADOOP_ROOT/bin/hdfs dfs -rm -r $out
  $HADOOP_ROOT/bin/hadoop jar ../target/hhmetrics-0.0.1.jar $dir $out
done


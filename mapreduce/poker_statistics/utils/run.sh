#!/bin/bash

HADOOP_HOME=$1
INDIR=$2
OUTDIR=$3

DIR="$(dirname "${BASH_SOURCE[0]}")"
DIR="$(realpath "${DIR}")" 

$HADOOP_HOME/bin/hdfs dfs -rm -r $OUTDIR
$HADOOP_HOME/bin/hadoop jar $DIR/../target/pokerstatistics-0.0.1.jar $INDIR $OUTDIR

tmp_dir=$(mktemp -d "${TMPDIR:-/tmp/}$(basename $0).XXXXXXXXXXXX")
$HADOOP_HOME/bin/hdfs dfs -copyToLocal $OUTDIR/* $tmp_dir

echo "Results downloaded to $tmp_dir" 
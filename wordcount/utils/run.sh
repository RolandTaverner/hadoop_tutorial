#!/bin/bash

HADOOP_DIR=$1
INDIR=$2
OUTDIR=$3

DIR="$(dirname "${BASH_SOURCE[0]}")"
DIR="$(realpath "${DIR}")" 

$HADOOP_DIR/bin/hdfs dfs -rm -r $OUTDIR
$HADOOP_DIR/bin/hadoop jar $DIR/../target/wordcount-0.0.1.jar $INDIR $OUTDIR

tmp_dir=$(mktemp -d "${TMPDIR:-/tmp/}$(basename $0).XXXXXXXXXXXX")
$HADOOP_DIR/bin/hdfs dfs -copyToLocal $OUTDIR/* $tmp_dir

echo "Results downloaded to $tmp_dir" 
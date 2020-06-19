#!/bin/bash

HADOOP_ROOT=$1
INDIR=$2
OUTDIR=$3

rm -rf $OUTDIR/tmp
mkdir -p $OUTDIR/tmp

tmp_dir=$OUTDIR/tmp

$HADOOP_ROOT/bin/hdfs dfs -copyToLocal $INDIR/* $tmp_dir

mkdir -p $OUTDIR/metrics
mkdir -p $OUTDIR/playeractivitydetails

cur_date=$(date '+%Y.%m.%d')

for date_dir in $tmp_dir/*; do
  datepart=$(echo "$(basename -- $date_dir)" | cut -d'-' -f 1)
  #echo $datepart
  
  cat ${date_dir}/simple/part-r-* > "${OUTDIR}/metrics/${datepart}_0001_${cur_date}.tsv"
  
  cat ${date_dir}/playeractivitydetails/part-r-* > "${OUTDIR}/playeractivitydetails/${datepart}_0001_${cur_date}.tsv"
  
done

rm -rf $tmp_dir
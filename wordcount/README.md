# wordcount sample app

## Upload sample data

```bash
export HADOOP_DIR=...

export DFS_DIR=/user/$USER/wordcount/in
$HADOOP_DIR/bin/hdfs dfs -mkdir /user/$USER/wordcount 
$HADOOP_DIR/bin/hdfs dfs -mkdir $DFS_DIR
$HADOOP_DIR/bin/hdfs dfs -copyFromLocal ./utils/testdata.txt $DFS_DIR
```

## Run
```bash
mvn package
./utils/run.sh $HADOOP_DIR /user/$USER/wordcount/in /user/$USER/wordcount/out

```

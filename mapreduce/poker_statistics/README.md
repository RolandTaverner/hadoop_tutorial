# PokerStatistics sample app

## Upload sample data

```bash
export HADOOP_HOME=...

export DFS_DIR=/user/$USER/pokerstatistics/in
$HADOOP_HOME/bin/hdfs dfs -mkdir -p $DFS_DIR
$HADOOP_HOME/bin/hdfs dfs -copyFromLocal ./utils/testdata1.txt $DFS_DIR/
$HADOOP_HOME/bin/hdfs dfs -copyFromLocal ./utils/testdata2.txt $DFS_DIR/
```

## Run
```bash
mvn package
./utils/run.sh $HADOOP_HOME /user/$USER/pokerstatistics/in /user/$USER/pokerstatistics/out

```

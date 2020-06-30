# wordcount sample app

## Upload sample data

```bash
export HADOOP_HOME=...

export DFS_DIR=/user/$USER/wordcount/in
$HADOOP_HOME/bin/hdfs dfs -mkdir -p $DFS_DIR
$HADOOP_HOME/bin/hdfs dfs -copyFromLocal ./utils/testdata.txt $DFS_DIR/
```

## Run
```bash
mvn package
./utils/run.sh $HADOOP_HOME /user/$USER/wordcount/in /user/$USER/wordcount/out

```

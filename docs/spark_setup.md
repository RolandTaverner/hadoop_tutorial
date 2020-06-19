# Download Spark

```bash
export WORK_DIR=/work

cd $WORK_DIR
wget http://mirror.linux-ia64.org/apache/spark/spark-2.4.6/spark-2.4.6-bin-without-hadoop.tgz
tar -xzvf spark-2.4.6-bin-without-hadoop.tgz
```

# Setup environment variables

```bash
export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
export SPARK_HOME=$WORK_DIR/spark-2.4.6-bin-without-hadoop
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native:$LD_LIBRARY_PATH
export PATH=$PATH:$SPARK_HOME/bin
export SPARK_DIST_CLASSPATH=$($HADOOP_HOME/bin/hadoop classpath)
```

# Configure

```bash
echo "spark.master yarn"          >> $SPARK_HOME/conf/spark-defaults.conf
echo "spark.driver.memory 512m"   >> $SPARK_HOME/conf/spark-defaults.conf
echo "spark.yarn.am.memory 512m"  >> $SPARK_HOME/conf/spark-defaults.conf
echo "spark.executor.memory 512m" >> $SPARK_HOME/conf/spark-defaults.conf
```

# Start/Stop

In order to use Spark with Yarn we need Yarn itself.

## Start

```bash
$HADOOP_HOME/sbin/start-yarn.sh
```

## Stop

```bash
$HADOOP_HOME/sbin/stop-yarn.sh
```


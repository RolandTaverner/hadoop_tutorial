# Установка

Документация: https://hadoop.apache.org/docs/r2.8.5/hadoop-project-dist/hadoop-common/SingleCluster.html#Pseudo-Distributed_Operation

Поставить необходимый софт:

```bash
sudo apt-get install openjdk-8-jdk
sudo apt-get install ssh
sudo apt-get install rsync
```

Скачать http://mirror.linux-ia64.org/apache/hadoop/common/hadoop-2.8.5/hadoop-2.8.5.tar.gz  
Распаковать куда-то, например, `/work/hadoop-2.8.5`.  

```bash
export WORK_DIR=/work
export HADOOP_HOME=$WORK_DIR/hadoop-2.8.5
```

Создать каталоги для данных

```bash
mkdir -p $WORK_DIR/hadoop-data/hdfs/namenode
mkdir -p $WORK_DIR/hadoop-data/hdfs/datanode
mkdir -p $WORK_DIR/hadoop-data/tmp
```

Отредактировать $HADOOP_HOME/etc/hadoop/core-site.xml

```xml
<configuration>

  <property>
    <name>fs.defaultFS</name>
    <value>hdfs://localhost:9000</value>
  </property>

  <property>
    <name>hadoop.tmp.dir</name>
    <value>/work/hadoop-data/tmp</value>
  </property>

</configuration>
```

Отредактировать $HADOOP_HOME/etc/hadoop/hdfs-site.xml

```xml
<configuration>

    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>

    <property>
        <name>dfs.namenode.name.dir</name>
        <value>file:/work/hadoop-data/hdfs/namenode</value>
    </property>

    <property>
        <name>dfs.datanode.data.dir</name>
        <value>file:/work/hadoop-data/hdfs/datanode</value>
    </property>

</configuration>
```

Отредактировать $HADOOP_HOME/etc/hadoop/hadoop-env.sh

```bash
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```

Однократно выполнить

```bash
$HADOOP_HOME/bin/hdfs namenode -format
```

Запустить Hadoop

```bash
$HADOOP_HOME/sbin/start-dfs.sh
```

Остановить Hadoop (перед выключением или перезагрузкой, иначе может повредиться HDFS и надо будет сделать форматирование заново)

```bash
$HADOOP_HOME/sbin/stop-dfs.sh
```
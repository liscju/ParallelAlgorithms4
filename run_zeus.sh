#!/bin/env bash
#PBS -l nodes=1:ppn=12
module load plgrid/apps/spark

start-multinode-spark-cluster.sh

$SPARK_HOME/bin/spark-submit --class SparkConnectedComponents --master local[*] /people/plgliscju/ParallelAlgorithms4/target/scala-2.10/connectedcomponents_2.10-1.0.jar /people/plgliscju/ParallelAlgorithms4/graphs/g10.txt > /people/plgliscju/ParallelAlgorithms4/output/g10.out
$SPARK_HOME/bin/spark-submit --class SparkConnectedComponents --master local[*] /people/plgliscju/ParallelAlgorithms4/target/scala-2.10/connectedcomponents_2.10-1.0.jar /people/plgliscju/ParallelAlgorithms4/graphs/g100.txt > /people/plgliscju/ParallelAlgorithms4/output/g100.out
$SPARK_HOME/bin/spark-submit --class SparkConnectedComponents --master local[*] /people/plgliscju/ParallelAlgorithms4/target/scala-2.10/connectedcomponents_2.10-1.0.jar /people/plgliscju/ParallelAlgorithms4/graphs/g1000.txt > /people/plgliscju/ParallelAlgorithms4/output/g1000.out
$SPARK_HOME/bin/spark-submit --class SparkConnectedComponents --master local[*] /people/plgliscju/ParallelAlgorithms4/target/scala-2.10/connectedcomponents_2.10-1.0.jar /people/plgliscju/ParallelAlgorithms4/graphs/g10000.txt > /people/plgliscju/ParallelAlgorithms4/output/g10000.out
$SPARK_HOME/bin/spark-submit --class SparkConnectedComponents --master local[*] /people/plgliscju/ParallelAlgorithms4/target/scala-2.10/connectedcomponents_2.10-1.0.jar /people/plgliscju/ParallelAlgorithms4/graphs/g100000.txt > /people/plgliscju/ParallelAlgorithms4/output/g100000.out


stop-multinode-spark-cluster.sh

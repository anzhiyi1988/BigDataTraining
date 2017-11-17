#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import os
import time

INTPUT_PATH = "hdfs://master:9000/flume/record/"
OUTPUT_PATH = "hdfs://master:9000/etl/record/"
LOAD_CMD = "java -cp /home/hadoop/etl/etl.jar orz.an.log.etl.load2hive.LoadDataToHive %s  %s  %s"
HADOOP_CMD = "hadoop jar /home/hadoop/hadoop-2.7.3/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar -D mapred.reduce.tasks=0 -D mapred.map.tasks=1  -input %s -output %s -mapper /home/hadoop/etl/etl.py -file /home/hadoop/etl/etl.py"


def getCurrentYmdHM():
    time_struct = time.localtime(time.time() - 60 * 10)

    H = time.strftime('%H', time_struct)
    M = int(time.strftime('%M', time_struct))
    M = "%02d" % ((M / 10) * 10)
    Ymd = time.strftime('%Y-%m-%d', time_struct)

    return Ymd + "/" + H + M


def startETL():
    in_day = sys.argv[1]
    in_time = sys.argv[2]
    print in_day + "/" + in_time

    subPath = in_day + "/" + in_time
    input = INTPUT_PATH + subPath
    output = OUTPUT_PATH + subPath

    hadoop_cmd = HADOOP_CMD % (input, output)
    print hadoop_cmd
    os.system(hadoop_cmd)
    print 'loading data into Hive'
    load_cmd = LOAD_CMD % (output, subPath.split("/")[0], subPath.split("/")[1])
    os.system(load_cmd)


if __name__ == '__main__':
    startETL()

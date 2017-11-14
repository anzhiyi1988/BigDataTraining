#!/bin/bash

nohup hive --service hiveserver2 >> /home/hadoop/hive-2.1.1/logs/hive.log 2>&1 &

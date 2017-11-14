#!/bin/bash

nohup hive --service metastore >> /home/hadoop/hive-2.1.1/logs/metastore.log 2>&1 &

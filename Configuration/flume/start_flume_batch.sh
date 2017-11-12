#!/bin/bash

flume-ng agent --conf /home/hadoop/flume-1.7.0/conf --conf-file /home/hadoop/flume-1.7.0/conf/LogAnalysis.properties --name logAgent -Dflume.root.logger=DEBUG,console -Dflume.monitoring.type=http -Dflume.monitoring.port=34545


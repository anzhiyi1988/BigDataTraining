#!/bin/bash

presto --server slave:8080 --catalog hive --schema default

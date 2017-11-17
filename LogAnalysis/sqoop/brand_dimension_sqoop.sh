#!/bin/bash

sqoop import --connect jdbc:mysql://mysql:3306/log --username root --password 123456 --table brand_dimension --driver com.mysql.jdbc.Driver --m 10 --target-dir /warehouse/brand_dimension

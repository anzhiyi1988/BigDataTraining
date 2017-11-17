create external table if not exists user_dimension (
    uid       string,
    name      string,
    gender    string,
    birth     date,
    province  string
)
row format delimited fields terminated by ','
location 'hdfs://master:9000/warehouse/user_dimension'
;

create external table if not exists brand_dimension (
    bid       string,
    category  string,
    brand     string
)
row format delimited fields terminated by ','
location 'hdfs://master:9000/warehouse/brand_dimension'
;


create table if not exists record (
    rid             string,     -- record id
    uid             string,     -- user id
    bid             string,     -- brand id
    trancation_date timestamp,  --
    price           int,
    source_province string,
    target_province string,
    site            string,
    express_number  string,
    express_company string
)
partitioned by (
    partition_date  string,
    hour_minute     int
)
row format delimited fields terminated by ','
;


-- ----------------------------------------------
-- hive 每个年龄的花费总额
-- ----------------------------------------------
select
cast(DATEDIFF(CURRENT_DATE, birth)/365 as int) as age,
sum(price) as totalPrice
from record
join user_dimension
on record.uid=user_dimension.uid
group by cast(DATEDIFF(CURRENT_DATE, birth)/365 as int)
order by totalPrice desc;

-- ----------------------------------------------
-- presto 每个年龄的花费总额
-- ----------------------------------------------
select
cast((year(CURRENT_DATE)-year(birth)) as integer) as age,
sum(price) as totalPrice
from record
join user_dimension
on record.uid=user_dimension.uid
group by cast((year(CURRENT_DATE)-year(birth)) as integer)
order by totalPrice desc



-- ----------------------------------------------
-- hive/presto 每个商品的销售总额
-- ----------------------------------------------
select
brand,
sum(price) as totalPrice
from record
join brand_dimension
on record.bid=brand_dimension.bid
group by brand_dimension.brand
order by totalPrice desc;


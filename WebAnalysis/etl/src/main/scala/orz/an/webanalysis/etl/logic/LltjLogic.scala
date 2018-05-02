package orz.an.webanalysis.etl.logic

import orz.an.webanalysis.etl.cache.Cache
import orz.an.webanalysis.etl.entity.Dim
import orz.an.webanalysis.etl.util.{DataClear, SparkUtil}
import orz.an.webanalysis.etl.dao.Dao

/**
  * 流量统计计算
  * 计算每天页面浏览量，访问者数，访问IP数，时长
  */
object LltjLogic {

    def runAnalysis(): Unit = {


        // 获得SparkContext
        val sc = SparkUtil.getSparkContext(this.getClass)

        //读取日志
        assert(Cache.day != null, "should set day before run")
        val logFile = "file:///data/logs/aura" + Cache.day.replace("-", "") + ".log"


        val records = sc.textFile(logFile)


        val logs = DataClear.convertAndFilterLog(records).cache()

        //构造dim数据，每一条数据指定pv uv ip 等
        val dims = logs.map(
            log => {
                val dim: Dim = new Dim(pv = 1, uv = 1, ip = 1)
                dim.Ts = log.Ts
                dim.uvs += log.Uuid
                dim.ips += log.Ip
                dim
            }
        ).cache()

        // 开始聚合，把数据叠加，构造uuid set 和 ip set， 最后uv 和ip 都是set的size，去重的，pv直接加和就可以了
        val reduce = dims.reduce((m, n) => {
            m.pv += n.pv
            m.uvs ++= n.uvs
            m.ips ++= n.ips
            m.uv = m.uvs.size
            m.ip = m.ips.size
            m
        })

        // 取出每个用户的访问时间
        val times = logs.map(
            log => {
                (log.Uuid, log.Ts)
            }
        ).cache()


        // 1. 分组得到每个用户的访问时间数组
        // 2. 遍历每个用户的访问时间数组，如果前后两次访问的时间差达到半小时（3600秒）就认为访问中断只加10秒，否则就加上两次访问的时间差
        // 3. 把每个所有用户的访问时间求和
        val totalTime = times.groupByKey().map(
            m => {
                val list: List[Long] = m._2.toList.sorted
                var time: Long = 10L
                if (list.size > 1) {
                    var cachets: Long = 0
                    list.foreach(ts => {
                        if (cachets != 0) {
                            val sub: Long = ts - cachets
                            if (sub >= 1800) {
                                time += 10
                            } else {
                                time += sub
                            }
                        }
                        cachets = ts
                    })
                }
                time
            }
        ).reduce(_ + _)

        sc.stop()
        reduce.time = totalTime
        reduce.day = Cache.day

        Dao.saveLltj(reduce)
    }


    def main(args: Array[String]): Unit = {
        val dayStr = if (args.length > 0) args(0) else "2016-12-01"
        Cache.setDay(dayStr)
        runAnalysis()
    }
}

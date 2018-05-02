package orz.an.test

import org.apache.spark.rdd.RDD

object MyTest {

    def testSome(map: RDD[(String, String)]): Unit = {

        map.groupByKey().map(x => (x._1, x._2.size, x._2.toSet.size))

        map.groupByKey().mapValues(x => (x.size, x.toSet.size))

    }


}

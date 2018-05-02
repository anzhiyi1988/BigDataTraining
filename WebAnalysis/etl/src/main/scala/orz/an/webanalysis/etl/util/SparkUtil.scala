package orz.an.webanalysis.etl.util

import org.apache.spark.{SparkConf, SparkContext}

object SparkUtil {

    def getSparkConf(cls: Class[_]): SparkConf = {
        new SparkConf()
            .setAppName(cls.getSimpleName)
            .setIfMissing("spark.master", "local[*]")
    }


    /**
      * 获得SparkContext
      *
      * @param cls
      * @return
      */
    def getSparkContext(cls: Class[_]): SparkContext = {
        new SparkContext(getSparkConf(cls))
    }

}

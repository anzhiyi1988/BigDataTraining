package orz.an.webanalysis.etl.util

import com.alibaba.fastjson.JSON
import org.apache.spark.rdd.RDD
import orz.an.webanalysis.etl.entity.Log

object DataClear {


    /**
      * 标题清理过滤
      *
      * @param title title
      * @return
      */
    def clearTitleAll(title: String): String = {
        var result: String = clearTitle(title, "-")
        result = clearTitle(result, "_")
        result
    }

    def clearTitle(title: String, symbol: String): String = {
        if (title == null) {
            return ""
        }
        val index: Int = title.lastIndexOf(symbol)
        if (index < 5) {
            title
        } else {
            clearTitle(title.substring(0, index), symbol)
        }
    }

    /**
      * 转换并过滤每行日志<br>
      * 把每行的string json串转换为Log对象，并过滤掉null行，返回新RDD
      *
      * @param lines 日志记录RDD，每行是string json串
      * @return
      */
    def convertAndFilterLog(lines: RDD[String]): RDD[Log] = {

        lines.map(
            line => {
                val log: Log = JSON.parseObject(line, classOf[Log])
                if (log.isLegal) {
                    log
                } else {
                    null
                }
            }
        ).filter(_ != null)
    }

}

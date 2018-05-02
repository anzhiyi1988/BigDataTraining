package orz.an.webanalysis.etl.dao

import java.sql.Connection

import orz.an.webanalysis.etl.db.DB
import orz.an.webanalysis.etl.entity.Dim

object Dao {

    /**
      * 保存流量统计数据
      *
      * @param dim 某一天的流量统计
      * @return
      */

    def saveLltj(dim: Dim): Int = {
        val sql: String = "insert into sparkcore_dimension_data(dimeid,`day`,pv,uv,ip,time) values (#{dimeId},#{day},#{pv},#{uv},#{ip},#{time}) on duplicate key update pv = values(pv),uv = values(uv),ip = values(ip),time = values(time)"
        saveObject(sql, dim)
    }



    /**
      * 保存数据
      * @param sql sql
      * @param entity 实体
      */
    private def saveObject(sql: String, entity: Object): Int = {
        val conn: Connection = DB.getConn
        try {
            BasicDao.saveObject(sql, entity, conn)
        } finally {
            DB.close(conn)
        }
    }

}
